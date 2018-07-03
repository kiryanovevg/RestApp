package com.evgeniy.restapp.Models

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.evgeniy.restapp.R
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
@BindingAdapter("setDate")
fun setDate(textView: TextView, unix: Int) {
    val unixDate = Date(unix * 1000L)
    val localDate = Date()

    val sdf = SimpleDateFormat()
    sdf.applyPattern("dd-MM-yyyy")

    val formatUnixDate = sdf.format(unixDate)
//    sdf.timeZone = TimeZone.getTimeZone("GMT")
    val formatLocalDate =  sdf.format(localDate)

    if (formatUnixDate == formatLocalDate) {
        sdf.applyPattern("HH:mm")
    }

    sdf.timeZone = TimeZone.getDefault()
    textView.text = sdf.format(unixDate)
}

@BindingAdapter("setImageFromUrl")
fun setImageFromUrl(imageView: ImageView, url: String?) {

    Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_none_image))
            .into(imageView)
}