package `in`.habituer.crypto

import `in`.habituer.crypto.utils.Constants
import `in`.januprasad.bdlibs.BigDecimalUtils
import org.junit.Test

import org.junit.Assert.*
import java.text.DecimalFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val a:Double = BigDecimalUtils.createCurrency("10").toDouble()

        val df = DecimalFormat("###.##")
        System.out.println(df.format(a))
//        val x = String.format("${Constants.API}${Constants.ENDPOINT}&limit=10&start=0")
//        print(x)
        print(a)
    }
}
