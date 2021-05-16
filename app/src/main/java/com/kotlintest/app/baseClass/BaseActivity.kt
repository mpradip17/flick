package com.kotlintest.app.baseClass.BaseActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.kotlintest.app.utility.CommonFunction
import com.kotlintest.app.utility.SharedHelper
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var disposable: CompositeDisposable
    lateinit var activity: Activity
    lateinit var fragmentManager: FragmentManager
    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun initView(mViewDataBinding: ViewDataBinding?)

    val commonFunction : CommonFunction by inject()
    val sharedHelper : SharedHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = CompositeDisposable()
        activity = this
        fragmentManager = supportFragmentManager
        bindContentView(layoutId())

    }
    private fun bindContentView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner=this
        initView(binding)
    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable.isDisposed) {
            disposable.clear()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(message: String, view: View) {
        //or use (activity.window.decorView.rootView) to set a global view for snackBar
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


    fun setIntent(cObjection: Class<*>, isFrom: Int) {
        startActivity(Intent(this, cObjection))
        when (isFrom) {
            1 -> {
                //just no need to finish
            }
            2 ->
                //just finishing the single activity
                finish()
            3 ->
                //finishing all previous activity
                finishAffinity()
        }
    }




}