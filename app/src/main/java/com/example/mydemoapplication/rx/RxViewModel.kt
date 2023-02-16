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
    private var asyncSubject = AsyncSubject.create<Long>()
    private var behaviorSubject = BehaviorSubject.create<Long>()
    private var completableSubject = CompletableSubject.create()
    private var publishSubject = PublishSubject.create<Long>()
    private var replaySubject = ReplaySubject.create<Long>()

    private var maybeSubject = MaybeSubject.create<Long>()
    private var singleSubject = SingleSubject.create<Long>()

    private var unicastSubject = UnicastSubject.create<Long>()

    init {
        dispose = Observable.interval(1, TimeUnit.SECONDS)
            .doOnNext {
                Log.d(TAG, "Longerval -> $it")
                publishSubject.onNext(it)
                asyncSubject.onNext(it)
                behaviorSubject.onNext(it)
                replaySubject.onNext(it)

                if (it == 20L) {
                    asyncSubject.onComplete()
                    maybeSubject.onSuccess(it)
                    singleSubject.onSuccess(it)
                    completableSubject.onComplete()
                    dispose?.dispose()
                }
            }
            .subscribe()
    }

    override fun onCleared() {
        super.onCleared()
        dispose?.dispose()
    }

    fun observerAsyncSubject(): Observable<Long> {
        return asyncSubject
    }

    fun observerPublishSubject(): Observable<Long> {
        return publishSubject
    }

    fun observerBehaviorSubject(): Observable<Long> {
        return behaviorSubject
    }

    fun observerReplaySubject(): Observable<Long> {
        return replaySubject
    }
}