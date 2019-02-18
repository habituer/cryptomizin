package `in`.habituer.crypto.extension

import java.text.DecimalFormat

/**
 * Created by januprasad on 18,February,2019
 */
fun String.formatTwoDigit() = DecimalFormat("##.##").format(this.toDouble())
