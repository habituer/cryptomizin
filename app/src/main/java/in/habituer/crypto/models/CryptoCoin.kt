package `in`.habituer.crypto.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by januprasad on 18,February,2019
 */

@Parcelize
data class CryptoCoin(
        @SerializedName("24h_volume_inr") val volume_inr_24hr: String?,
        @SerializedName("24h_volume_usd") val volume_usd_24h: String?,
        val available_supply: String?,
        val id: String?,
        val last_updated: String?,
        val market_cap_inr: String?,
        val market_cap_usd: String?,
        val name: String?,
        val percent_change_1h: String?,
        val percent_change_24h: String?,
        val percent_change_7d: String?,
        val price_btc: String?,
        val price_inr: String?,
        val price_usd: String?,
        val rank: String?,
        val symbol: String?,
        val total_supply: String?
) : Parcelable