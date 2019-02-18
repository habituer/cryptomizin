package `in`.habituer.crypto.adapters

import `in`.habituer.crypto.R
import android.app.Activity
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.habituer.crypto.`interface`.ILoadMore
import `in`.habituer.crypto.extension.formatTwoDigit
import `in`.habituer.crypto.extension.getBarlow
import `in`.habituer.crypto.extension.getNotoSans
import `in`.habituer.crypto.models.Coins
import `in`.habituer.crypto.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin.view.*

/**
 * Created by januprasad on 18,February,2019
 */

class CoinViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    var coinIcon = itemView.coin_icon
    var coinSymbol = itemView.coin_symbol
    var coinPrice = itemView.price_usd
    var coinTwentyHourChange = itemView.price_24hour
}

class CoinAdapter(recyclerView: RecyclerView, internal var activity: Activity, var items:List<Coins>): RecyclerView.Adapter<CoinViewHolder>() {

    internal var loadMore:ILoadMore?=null
    var isLoading:Boolean?=false
    var visibleThreshold=5
    var lastVisibleItem:Int=0
    var totalItemCount:Int=0

    init {
        val linearLayout = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayout.itemCount
                lastVisibleItem = linearLayout.findLastVisibleItemPosition()
                if (isLoading == false && totalItemCount <= lastVisibleItem+visibleThreshold){
                    loadMore!!.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    fun setLoadMore(loadMore: ILoadMore){
        this.loadMore = loadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_coin, parent, false)
        return CoinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder?, position: Int) {
        val coinModel = items.get(position)
        val item = holder as CoinViewHolder
        val typeFace = activity.getBarlow()
        item.coinSymbol.text = coinModel.symbol
        item.coinSymbol.typeface = typeFace
        item.coinPrice.text = "$"+ coinModel.price_usd!!.formatTwoDigit()
        item.coinPrice.typeface = typeFace
        item.coinTwentyHourChange.text = coinModel.percent_change_24h + "%"
        item.coinTwentyHourChange.typeface = typeFace

        Picasso.with(activity.baseContext)
                .load(StringBuilder(Utils.imageUrl)
                .append(coinModel.symbol!!.toLowerCase())
                .append(".png")
                .toString())
                .into(item.coinIcon)

        item.coinTwentyHourChange.setTextColor(if (coinModel.percent_change_24h!!.contains("-"))
            Color.parseColor("#FF0000")
        else
            Color.parseColor("#32CD32")
        )

    }

    fun setLoaded(){
        isLoading = false
    }

    fun updateData(coinModels: List<Coins>){
        this.items = coinModels
        notifyDataSetChanged()
    }
}
