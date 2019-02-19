package `in`.habituer.crypto.utils

import `in`.habituer.crypto.R
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by januprasad on 19,February,2019
 */
object ImageLoader {
    fun loadImage(url:String, view: ImageView) {
        Picasso.get().load(StringBuilder(Utils.imageUrl)
                .append(url)
                .append(".png")
                .toString())
                .placeholder(R.drawable.ic_trending_up_black_24dp)
                .into(view)
    }
}