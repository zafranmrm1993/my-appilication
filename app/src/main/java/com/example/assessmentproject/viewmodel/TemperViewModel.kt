package com.example.assessmentproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assessmentproject.model.TemperListModel
import com.example.assessmentproject.servicelayer.RetroService
import com.example.assessmentproject.servicelayer.RetrofitInstance
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TemperViewModel : ViewModel() {
    private lateinit var temperList: MutableLiveData<TemperListModel>

    init {
        temperList = MutableLiveData()
    }

    fun getTemperList(): MutableLiveData<TemperListModel>{
        return temperList
    }

    fun makeApiCall(query: String) {

        val retroInstance  = RetrofitInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getTemperListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }


    private fun getBookListObserverRx(): Observer<TemperListModel> {
        return object : Observer<TemperListModel> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                temperList.postValue(null)
            }

            override fun onNext(t: TemperListModel) {
                temperList.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }

}