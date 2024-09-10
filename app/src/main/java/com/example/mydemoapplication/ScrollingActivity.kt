package com.example.mydemoapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mydemoapplication.R
import com.example.mydemoapplication.flow.TestFlow
import com.example.mydemoapplication.notification.NotifyManager
import com.example.mydemoapplication.pinchzoomlayout.PinchZoomActivity
import com.example.mydemoapplication.rx.RxViewModel
import com.example.mydemoapplication.thread.TestThread
import com.example.mydemoapplication.ui.ConnectingView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ScrollingActivity : AppCompatActivity() {


    private var rxViewModel: RxViewModel? = null
    private var flowCallback:(()->Unit) = {
        println("collect:I am got it!!!")
    }

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
//        rxViewModel?.observerPublishSubject()
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(object : Observer<Long> {
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: Long) {
//                    findViewById<TextView>(R.id.subject_message).text =
//                        findViewById<TextView>(R.id.subject_message).text.toString() + "->" + t
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//
//                override fun onComplete() {
//                    findViewById<TextView>(R.id.subject_message).text =
//                        findViewById<TextView>(R.id.subject_message).text.toString() + "->END"
//                }
//
//
//            })

//        TestFlow.flowTest(flowCallback)

//        val test = TestThread()
//        val test2 = TestThread()
////        val threadA = Thread { test.runTest("threadA test") }
////
////        val threadB = Thread { test.runTest1("threadB test2") }
//
////        threadA.start()
////        threadB.start()
//
//        test.runTest("threadA test")
//        test2.runTest("threadB test")

        findViewById<ConnectingView>(R.id.view_connect)
            .setTimeTextSize(120f)
            .setTimeUnitTextSize(74f)
            .startAnimation(200_000)
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
            ?.doOnNext {
                Log.d("jc_test", "thread=${Thread.currentThread().name}")
            }
            ?.subscribeOn(Schedulers.computation())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnNext {
                Log.d("jc_test", "thread2=${Thread.currentThread().name}")
            }
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

        rxViewModel?.testFromIterable()
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(
//                {
//                    System.out.println("rxViewModel?.retry() int=$it")
//                }, {
//
//                    System.out.println("rxViewModel?.retry() error=${it.message}")
//                })
    }

    fun create_notify(view: View) {
        NotifyManager.create(this)

    }

    fun clickPinchZoom(view: View) {
        startActivity(Intent(this,PinchZoomActivity::class.java))
    }
}