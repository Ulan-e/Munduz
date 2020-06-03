package ulanapp.munduz.helpers

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ulanapp.munduz.R

fun setSmallImage(urlImage: String, viewImage : ImageView){
    Picasso.get()
        .load(urlImage)
        .error(R.drawable.ic_error_image_black_24dp)
        .fit()
        .into(viewImage)
}