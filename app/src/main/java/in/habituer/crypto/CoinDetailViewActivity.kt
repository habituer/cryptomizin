package `in`.habituer.crypto

import `in`.habituer.crypto.extension.formatINR
import `in`.habituer.crypto.extension.formatINRUnit
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
        c.price_inr.let { it ->
            if (it != null) {
                price_inr_detail.text = it.formatINR()
            }
        }


        volume_24_value.text = c.volume_inr_24hr!!.formatINRUnit()
        market_cap_value.text = c.market_cap_inr!!.formatINRUnit()
        supply_value.text = c.available_supply!!.formatINRUnit()


        ImageLoader.loadImage(c.symbol!!.toLowerCase(), coin_icon_detail)
        setTypeface()
        setColor(c)
    }

    private fun setColor(c: CryptoCoin) {
        price_24hour_detail.setTextColor(if (c.percent_change_24h!!.contains("-")) RED else GREEN)
    }

    private fun setTypeface() {
        val t = getBarlow()
        coin_full_name_detail.typeface = t
        coin_symbol_detail.typeface = t
        price_24hour_detail.typeface = t
        price_inr_detail.typeface = t


        volume_24_text.typeface = t
        market_cap_text.typeface = t
        supply_text.typeface = t

        volume_24_value.typeface = t
        market_cap_value.typeface = t
        supply_value.typeface = t


    }
}
