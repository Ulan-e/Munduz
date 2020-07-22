package ulanapp.munduz.helpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import ulanapp.munduz.R

fun setSmallImage(context: Context, urlImage: String, viewImage: ImageView) {
    Glide
        .with(context)
        .load(urlImage)
        .error(R.drawable.ic_error_image_black_24dp)
        .fitCenter()
        .into(viewImage)
}
