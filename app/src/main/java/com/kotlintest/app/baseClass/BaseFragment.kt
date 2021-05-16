package com.kotlintest.app.baseClass

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.utility.SharedHelper
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kotlintest.app.utility.GlideApp
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    lateinit var disposable: CompositeDisposable
    lateinit var activity: Activity
    var fragmentManagers: FragmentManager? = null
    lateinit var views:View

    val commonFunction : CommonFunction by inject()
    val sharedHelper : SharedHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = CompositeDisposable()
        activity = getActivity()!!
        fragmentManagers = fragmentManager!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (disposable.isDisposed) {
            disposable.clear()
        }
    }

    fun glideApp(imagePath: String, Images: ImageView) {
        GlideApp.with(activity)
            .load(imagePath)
            .into(Images)
    }

    fun showSnackBar(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
            .show()
    }

    private fun showNetworkSnackBar(message: String, view: View?) {
        val snackBar: Snackbar?
        snackBar = Snackbar.make(view!!, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        snackBar.setAction("Dismiss", View.OnClickListener {
            snackBar.dismiss()
        })
        snackBar.show()
    }


}
