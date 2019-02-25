package `in`.habituer.crypto

import `in`.habituer.crypto.extension.appendStart
import `in`.habituer.crypto.models.CryptoCoin
import `in`.habituer.crypto.utils.ColorConstant.GREEN
import `in`.habituer.crypto.utils.ColorConstant.RED
import `in`.habituer.crypto.utils.ImageLoader
import `in`.januprasad.currencyops.*;
import `in`.januprasad.currencyops.BigDecimalUtils.createCurrency
import `in`.januprasad.fontmaniautil.FontManager
import `in`.januprasad.fontmaniautil.FontType
import `in`.januprasad.fontmaniautil.setFont
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
        price_24hour_detail.text = if (c.percent_change_24h!!.contains("-")) c.percent_change_24h else c.percent_change_24h.appendStart("+")

        c.price_inr.let { it ->
            if (it != null) {
                price_inr_detail.text = createCurrency(it).toIndianRupee()
            }
        }

        coin_rank_detail.text = c.rank!!.appendStart("# ")

        volume_24_value.text = createCurrency(c.volume_inr_24hr!!).toIndianRupeeWithUnit()
        market_cap_value.text = createCurrency(c.market_cap_inr!!).toIndianRupeeWithUnit()
        supply_value.text = createCurrency(c.available_supply!!).toIndianRupeeWithUnit()

        ImageLoader.loadImage(c.symbol!!.toLowerCase(), coin_icon_detail)
        setTypeface()
        setColor(c)
    }

    private fun setColor(c: CryptoCoin) {
        price_24hour_detail.setTextColor(if (c.percent_change_24h!!.contains("-")) RED else GREEN)
    }

    private fun setTypeface() {
        setFont(FontManager.BARLOW.withType(FontType.BOLD),
                coin_full_name_detail,
                coin_symbol_detail,
                price_24hour_detail,
                price_inr_detail,
                volume_24_text,
                market_cap_text,
                supply_text,
                volume_24_value,
                market_cap_value,
                supply_value)
    }
}
