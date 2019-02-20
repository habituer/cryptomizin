package `in`.habituer.crypto.adapters

import `in`.habituer.crypto.R
import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.habituer.crypto.`interface`.ILoadMore
import `in`.habituer.crypto.extension.appendStart
import `in`.habituer.crypto.utils.ColorConstant.GREEN
import `in`.habituer.crypto.utils.ColorConstant.RED
import `in`.habituer.crypto.extension.getBarlow
import `in`.habituer.crypto.models.CryptoCoin
import `in`.habituer.crypto.utils.ImageLoader.loadImage
import `in`.habituer.crypto.utils.Logger.logv
import `in`.januprasad.bdlibs.BigDecimalUtils.createCurrency
import `in`.januprasad.bdlibs.toIndianRupee
import `in`.januprasad.bdlibs.toIndianRupeeWithUnit
import android.annotation.SuppressLint
import com.balysv.materialripple.MaterialRippleLayout
import kotlinx.android.synthetic.main.item_coin.view.*

/**
 * Created by januprasad on 18,February,2019
 */


class CoinAdapter(recyclerView: RecyclerView, internal var activity: Activity, var items: List<CryptoCoin>) :
        RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {


    internal var loadMore: ILoadMore? = null
    var isLoading: Boolean? = false
    var visibleThreshold = 5
    var lastVisibleItem: Int = 0
    var totalItemCount: Int = 0
    var onItemClick: ((CryptoCoin) -> Unit)? = null

    init {
        val linearLayout = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayout.itemCount
                lastVisibleItem = linearLayout.findLastVisibleItemPosition()
                if (isLoading == false && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    loadMore!!.onLoadMore()
                    isLoading = true
                }
            }
        })
    }

    fun setLoadMore(loadMore: ILoadMore) {
        this.loadMore = loadMore
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_coin, parent, false)
        return CoinViewHolder(MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                .rippleAlpha(0.2f)
                .rippleColor(-0xa7a7a8)
                .rippleHover(true)
                .create())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coinModel = items.get(position)
        val item = holder as CoinViewHolder
        val typeFace = activity.getBarlow()
        item.coinSymbol.text = coinModel.symbol
        item.coinSymbol.typeface = typeFace
        coinModel.price_inr.let { it ->
            if (it != null) {
                item.coinPrice.text = createCurrency(it).toIndianRupeeWithUnit()
            }
        }

        item.coinPrice.typeface = typeFace
        item.coinTwentyHourChange.text = coinModel.percent_change_24h + "%"

        item.coinTwentyHourChange.text = if (coinModel.percent_change_24h!!.contains("-")) coinModel.percent_change_24h else coinModel.percent_change_24h.appendStart("+")

        item.coinTwentyHourChange.typeface = typeFace


        loadImage(coinModel.symbol!!.toLowerCase(), item.coinIcon)

        item.coinTwentyHourChange.setTextColor(if (coinModel.percent_change_24h!!.contains("-")) RED else GREEN)
    }

    fun setLoaded() {
        isLoading = false
    }

    fun updateData(coinModels: List<CryptoCoin>) {
        this.items = coinModels
        notifyDataSetChanged()
    }

    inner class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var coinIcon = itemView.coin_icon
        var coinSymbol = itemView.coin_symbol
        var coinPrice = itemView.price_inr
        var coinTwentyHourChange = itemView.price_24hour

        init {
            itemView.setOnClickListener {
                if (adapterPosition >= 0)
                    onItemClick?.invoke(items[adapterPosition])
            }
        }

    }

}
