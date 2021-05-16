package com.kotlintest.app.utility

import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlintest.app.model.ImageSearchModel
import com.kotlintest.app.utility.RecycleView.GridItemSpacingDecoration
import com.kotlintest.app.view.adapter.ImageAdapter
import java.util.*

object CustomBinding {

    private val gridSpacing = dp2px(2)
    fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    @BindingAdapter("ImageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, data: ImageSearchModel.Photos.Photo) {
        val url = "http://farm${data.farm}.static.flickr.com/${data.server}/${data.id}_${data.secret}.jpg"
        Glide.with(imageView.context).load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }

    @BindingAdapter("load_allLead")
    @JvmStatic
    fun loadUsers(recyclerView: RecyclerView, adapter: Any?) {
        when (adapter) {


            is ImageAdapter -> {
                (Objects.requireNonNull<RecyclerView.ItemAnimator>(recyclerView.getItemAnimator()) as SimpleItemAnimator).supportsChangeAnimations =
                    false
                recyclerView.addItemDecoration(GridItemSpacingDecoration(2, gridSpacing, false))
                recyclerView.adapter = adapter as RecyclerView.Adapter<*>?
            }


        }
    }

}