package `in`.habituer.crypto

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import `in`.habituer.crypto.`interface`.ILoadMore
import `in`.habituer.crypto.adapters.CoinAdapter
import `in`.habituer.crypto.extension.Logger
import `in`.habituer.crypto.models.CryptoCoin
import `in`.habituer.crypto.utils.Constants.API
import `in`.habituer.crypto.utils.Constants.ENDPOINT
import `in`.habituer.crypto.utils.Utils
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import java.io.IOException

/**
 * Created by januprasad on 18,February,2019
 */
class MainActivity : AppCompatActivity(), ILoadMore {

    internal var items: MutableList<CryptoCoin> = ArrayList()
    internal lateinit var adapter: CoinAdapter
    private lateinit var client: OkHttpClient
    private lateinit var request: Request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swife_to_refresh.post { loadFist10Coin() }

        swife_to_refresh.setOnRefreshListener {
            items.clear()
            loadFist10Coin()
            setUpAdapter()
        }

        coin_recycler_view.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        setUpAdapter()
    }

    private fun setUpAdapter() {
        adapter = CoinAdapter(coin_recycler_view, this@MainActivity, items)
        coin_recycler_view.adapter = adapter
        adapter.setLoadMore(this)
        adapter.onItemClick = { cryptoCoin ->
            // do something with your item
            startActivity(intentFor<CoinDetailViewActivity>("cryptoCoin" to cryptoCoin).singleTop())
        }
    }

    override fun onLoadMore() {
        if (items.size <= Utils.MAX_COIN_LOAD)
            loadNext10Coin(items.size)
        else
            Toast.makeText(this@MainActivity, "Data max is" + Utils.MAX_COIN_LOAD, Toast.LENGTH_LONG).show()
    }

    private fun loadFist10Coin() {
        client = OkHttpClient()
        request = Request.Builder()
                .url(String.format("$API$ENDPOINT&limit=10&start=0"))
                .build()

        client.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d("ERROR: ", e.toString())
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        val body = response.body()!!.string()
                        val gson = Gson()
                        items = gson.fromJson(body, object : TypeToken<List<CryptoCoin>>() {}.type)
                        runOnUiThread {
                            adapter.updateData(items)
                        }
                    }
                })
    }

    private fun loadNext10Coin(index: Int) {
        client = OkHttpClient()
        request = Request.Builder()
                .url(String.format("$API$ENDPOINT&start=%d&limit=10", index))
                .build()

        swife_to_refresh.isRefreshing = true
        client.newCall(request)
                .enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d("ERROR: ", e.toString())
                    }

                    override fun onResponse(call: Call?, response: Response) {
                        val body = response.body()!!.string()
                        val gson = Gson()
                        val newItems = gson.fromJson<List<CryptoCoin>>(body, object : TypeToken<List<CryptoCoin>>() {}.type)
                        runOnUiThread {
                            items.addAll(newItems)
                            adapter.setLoaded()
                            adapter.updateData(items)
                            swife_to_refresh.isRefreshing = false
                        }
                    }
                })
    }

}


