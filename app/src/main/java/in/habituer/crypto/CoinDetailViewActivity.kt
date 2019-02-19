package `in`.habituer.crypto

import `in`.habituer.crypto.extension.getBarlow
import `in`.habituer.crypto.models.CryptoCoin
import `in`.habituer.crypto.utils.ColorConstant.GREEN
import `in`.habituer.crypto.utils.ColorConstant.RED
import `in`.habituer.crypto.utils.ImageLoader
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_coin_detail_view.*

class CoinDetailViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail_view)
        val cryptoCoin = intent.getParcelableExtra<CryptoCoin>("cryptoCoin")
        setUI(cryptoCoin)

    }

    private fun setUI(c: CryptoCoin) {
        coin_full_name_detail.text = c.name
        price_24hour_detail.text = c.percent_change_24h
        price_inr_detail.text = c.price_inr
        ImageLoader.loadImage(c.symbol!!.toLowerCase(), coin_icon_detail)
        setTypeface()
        setColor(c)
    }

    private fun setColor(c: CryptoCoin) {
        price_24hour_detail.setTextColor(if (c.percent_change_24h!!.contains("-")) RED else GREEN)
    }

    private fun setTypeface() {
        coin_full_name_detail.typeface = getBarlow()
        coin_symbol_detail.typeface = getBarlow()
        price_24hour_detail.typeface = getBarlow()
        price_inr_detail.typeface = getBarlow()
    }
}
