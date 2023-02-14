package com.example.mydemoapplication.rx

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mydemoapplication.data.LoginRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.operators.observable.ObservableCount
import io.reactivex.rxjava3.subjects.*
import java.util.concurrent.TimeUnit

/**
 * Package: com.example.mydemoapplication.rx
 * Date:    2023/2/14
 * Desc:    com.example.mydemoapplication.rx
 *
 * @author liujicheng
 */
class RxViewModel() : ViewModel() {

    val TAG = "RxViewModel"

    private var dispose: Disposable? = null
    private var asyncSubject = AsyncSubject.create<Int>()
    private var behaviorSubject = BehaviorSubject.create<Int>()
    private var completableSubject = CompletableSubject.create()
    private var maybeSubject = MaybeSubject.create<Int>()
    private var publishSubject = PublishSubject.create<Int>()
    private var replaySubject = ReplaySubject.create<Int>()

    //    private var serializedSubject = SerializedSubject()
    private var singleSubject = SingleSubject.create<Int>()
    private var unicastSubject = UnicastSubject.create<Int>()

    init {
        dispose = Observable.interval(1, TimeUnit.SECONDS)
            .doOnNext {
                Log.d(TAG, "interval -> $it")
            }
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        dispose?.dispose()
    }


}