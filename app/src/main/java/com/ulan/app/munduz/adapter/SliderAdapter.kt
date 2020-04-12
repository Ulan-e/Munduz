package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.SliderImage

class SliderAdapter: PagerAdapter {

    private var mContext: Context
    private var mImages : MutableList<SliderImage>

    constructor(context: Context, images: MutableList<SliderImage>) : super() {
        this.mContext = context
        this.mImages = images
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return mImages.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater= mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_slider, null)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val sliderImage = mImages.get(position)
        Picasso.get().load(sliderImage.image).into(imageView)

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }
}