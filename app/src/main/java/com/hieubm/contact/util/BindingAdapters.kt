package com.hieubm.contact.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.request.CachePolicy
import com.hieubm.contact.R
import kotlinx.coroutines.Dispatchers

object BindingAdapters {

    @BindingAdapter("visible")
    @JvmStatic
    fun View.setVisible(isVisible: Boolean?) {
        this.isVisible = isVisible ?: false
    }

    @BindingAdapter("gone")
    @JvmStatic
    fun View.setGone(isGone: Boolean?) {
        this.isGone = isGone ?: false
    }

    @BindingAdapter("url")
    @JvmStatic
    fun ImageView.setURL(url: String?) {
        url?.let {
            load(url) {
                dispatcher(Dispatchers.IO)
                diskCachePolicy(CachePolicy.ENABLED)
                placeholder(R.drawable.rect_radius_gray)
            }
        }
    }
}