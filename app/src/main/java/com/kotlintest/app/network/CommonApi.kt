package com.kotlintest.app.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.kotlintest.app.utility.SharedHelper
import com.kotlintest.app.utility.rx.SchedulersFacade
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class  CommonApi constructor(
    var application: Application,
    val sharedHelper: SharedHelper,
    val api: ApiInterface,
    val schedulersFacade: SchedulersFacade
) {
    fun getImageApi(
        data : HashMap<String,Any>,
        response: MutableLiveData<Response>,
        disable: CompositeDisposable
    ) {


        disable.add(api.getImageSearch(data)
            .doOnSubscribe({ response.postValue(Response.loading()); })
            .compose(schedulersFacade.applyAsync())
            .doFinally { response.value = Response.dismiss() }
            .subscribe({
                response.value = Response.success(it)
            }, {
                response.value = Response.error(it)
                response.value = Response.dismiss()

            })
        )
    }


}