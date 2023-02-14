package com.example.mydemoapplication.rx

import androidx.lifecycle.ViewModel
import com.example.mydemoapplication.data.LoginRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.internal.operators.observable.ObservableCount
import java.util.concurrent.TimeUnit

/**
 * Package: com.example.mydemoapplication.rx
 * Date:    2023/2/14
 * Desc:    com.example.mydemoapplication.rx
 *
 * @author liujicheng
 */
class RxViewModel() : ViewModel() {


    init {

        Observable.interval(1, TimeUnit.SECONDS)
            .doOnNext {

            }
            .subscribe()
     }

}