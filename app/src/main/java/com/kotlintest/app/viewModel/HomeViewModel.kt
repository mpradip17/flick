package com.kotlintest.app.viewModel

import androidx.lifecycle.MutableLiveData
import com.kotlintest.app.baseClass.BaseViewModel
import com.kotlintest.app.network.CommonApi
import com.kotlintest.app.network.Response

class HomeViewModel(var commonApi: CommonApi)  : BaseViewModel() {

    var page =1
    var textSearch = ""

    fun response(): MutableLiveData<Response> {
        return response
    }
    fun onUsernameTextChanged(loginParamModel: Any) {
        page = 1
        when(loginParamModel){
            is String ->{
                if(loginParamModel.isNotEmpty()){
                    apiCall(page,loginParamModel)
                }
            }
        }

    }

    fun apiCall( page : Int ,textSearch : String){
        val data = HashMap<String,Any>()
        data.put("method","flickr.photos.search")
        data.put("api_key","3e7cc266ae2b0e0d78e279ce8e361736")
        data.put("format","json")
        data.put("nojsoncallback","1")
        data.put("safe_search","1")
        data.put("text",textSearch)
        data.put("page",page)
        commonApi.getImageApi(data,response,disable)


    }
}