package com.kotlintest.app.view.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.washeruser.repository.Status
import com.kotlintest.app.R
import com.kotlintest.app.baseClass.BaseActivity.BaseActivity
import com.kotlintest.app.databinding.ActivityMainBinding
import com.kotlintest.app.model.ImageSearchModel
import com.kotlintest.app.network.Response
import com.kotlintest.app.view.adapter.ImageAdapter
import com.kotlintest.app.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun layoutId(): Int = R.layout.activity_main

    private val homeViewModel by viewModel<HomeViewModel>()

    private var dataList : ArrayList<ImageSearchModel.Photos.Photo> = ArrayList()

    private var loading = false
    private  var adapter : ImageAdapter ?=null
    override fun initView(mViewDataBinding: ViewDataBinding?) {
        binding.viewModel = homeViewModel
        adapter = ImageAdapter(dataList)
        binding.adapter = adapter

        homeViewModel.response().observe(this, Observer {
            processResponse(it)
        })

        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    val layoutManager = binding.recycleView.layoutManager as GridLayoutManager
                   val visibleItemCount = layoutManager.getChildCount()
                    val  totalItemCount = layoutManager.getItemCount()
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            homeViewModel.page++
                            homeViewModel.apiCall(homeViewModel.page,homeViewModel.textSearch)
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        })

    }

    private fun processResponse(response: Response){
        when(response.status){
            Status.SUCCESS -> {
                when (response.data) {
                    is ImageSearchModel ->{
                       /* if(homeViewModel.page==1){
                            dataList.clear()
                        }*/
                        if(response.data.photos.photo.isNotEmpty()){
                            loading=true;
                            dataList.addAll(response.data.photos.photo)
                            adapter?.notifyItemInserted(dataList.size-1)
                        }
                    }

                }
            }
            Status.LOADING -> {
                if(homeViewModel.page!=1 && binding.loadingProgressXml.visibility == View.GONE){

                    binding.loadingProgressXml.visibility = View.VISIBLE
                }


            }
            Status.DISMISS -> {
                if(binding.loadingProgressXml.visibility==View.VISIBLE)
                  binding.loadingProgressXml.visibility = View.GONE

            }
            Status.ERROR->{
                dataList.clear()
            }


        }

    }
}
