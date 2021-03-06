package com.kotlintest.app.view.activity



import androidx.databinding.ViewDataBinding
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SplashActivity : BaseActivity<ActivitySplashBinding>() {


    protected fun callIntent(){
        setIntent(MainActivity::class.java,3)
    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initView(mViewDataBinding: ViewDataBinding?) {
        disposable.add(Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {  t->
                run {
                    Timber.d(t)
                }
            }
            .subscribe { aLong ->
                callIntent()
            })
    }

}
