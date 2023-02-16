package com.example.mydemoapplication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mydemoapplication.R
import com.example.mydemoapplication.rx.RxViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

class ScrollingActivity : AppCompatActivity() {


    private var rxViewModel: RxViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        rxViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory()).get(
            RxViewModel::class.java
        )
        setSupportActionBar(findViewById(R.id.toolbar))


        init()
    }

    private fun init() {
        rxViewModel?.observerPublishSubject()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Long) {
                    findViewById<TextView>(R.id.subject_message).text =
                        findViewById<TextView>(R.id.subject_message).text.toString() + "->" + t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    findViewById<TextView>(R.id.subject_message).text =
                        findViewById<TextView>(R.id.subject_message).text.toString() + "->END"
                }


            })
    }

    fun publish_subject(view: View) {
        rxViewModel?.observerPublishSubject()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Long) {
                    findViewById<TextView>(R.id.publish_subject).text =
                        findViewById<TextView>(R.id.publish_subject).text.toString() + "->" + t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    findViewById<TextView>(R.id.publish_subject).text =
                        findViewById<TextView>(R.id.publish_subject).text.toString() + "->END"
                }


            })

//        rxViewModel?.observerPublishSubject()
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(object : Subject<Long>() {
//                override fun subscribeActual(observer: Observer<in Long>) {
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onError(e: Throwable) {
//                }
//
//                override fun onComplete() {
//                }
//
//                override fun hasObservers(): Boolean {
//                    return false
//                }
//
//                override fun hasThrowable(): Boolean {
//                    return false
//                }
//
//                override fun hasComplete(): Boolean {
//                    return false
//                }
//
//                override fun getThrowable(): Throwable? {
//                    return null
//                }
//
//                override fun onNext(t: Long) {
//                    findViewById<TextView>(R.id.publish_subject).text =
//                        findViewById<TextView>(R.id.publish_subject).text.toString() + "->" + t
//                }
//
//
//            })
    }

    fun async_subject(view: View) {
        rxViewModel?.observerAsyncSubject()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Long) {
                    findViewById<TextView>(R.id.async_subject).text =
                        findViewById<TextView>(R.id.async_subject).text.toString() + "->" + t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    findViewById<TextView>(R.id.async_subject).text =
                        findViewById<TextView>(R.id.async_subject).text.toString() + "->END"
                }


            })
    }

    fun behavior_subject(view: View) {
        rxViewModel?.observerBehaviorSubject()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Long) {
                    findViewById<TextView>(R.id.behavior_subject).text =
                        findViewById<TextView>(R.id.behavior_subject).text.toString() + "->" + t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    findViewById<TextView>(R.id.behavior_subject).text =
                        findViewById<TextView>(R.id.behavior_subject).text.toString() + "->END"
                }


            })
    }

    fun replay_subject(view: View) {
        rxViewModel?.observerReplaySubject()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Long) {
                    findViewById<TextView>(R.id.replay_subject).text =
                        findViewById<TextView>(R.id.replay_subject).text.toString() + "->" + t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {
                    findViewById<TextView>(R.id.replay_subject).text =
                        findViewById<TextView>(R.id.replay_subject).text.toString() + "->END"
                }


            })
    }
}