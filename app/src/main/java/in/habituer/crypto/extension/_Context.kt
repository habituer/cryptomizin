package `in`.habituer.crypto.extension


import `in`.habituer.crypto.R
import android.app.Application
import android.content.Context
import android.support.v4.content.res.ResourcesCompat

/**
 * Created by januprasad on 18,February,2019
 */
val Context.app: Application
    get() = applicationContext as Application

fun Context.getBarlow() = ResourcesCompat.getFont(app, R.font.barlowbold)!!
