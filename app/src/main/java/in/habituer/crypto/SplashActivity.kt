package `in`.habituer.crypto

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import `in`.habituer.crypto.extension.getBarlow
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by januprasad on 18,February,2019
 */
class SplashActivity : AppCompatActivity() {

    private var mHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initTypeface()
        mHandler = Handler(Looper.getMainLooper())


        mHandler!!.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 500)
    }

    private fun initTypeface() {
        val typeFace = getBarlow()
        title_app.typeface = typeFace
    }
}
