package `in`.habituer.crypto.extension

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by januprasad on 18,February,2019
 */
const val format = "#,###.0000"

fun String.formatTwoDigit() = DecimalFormat(format).format(this.toDouble())!!

fun String.formatINRUnit(): String? {
    var local = Locale("en", "IN")
    var format: NumberFormat = NumberFormat.getCurrencyInstance(local)
    return format.format(String.format("%.2f", this.toDouble()).toFloat())
}
fun String.formatINR(): String? {
    return DecimalFormat(format).format(this.toDouble())
}
