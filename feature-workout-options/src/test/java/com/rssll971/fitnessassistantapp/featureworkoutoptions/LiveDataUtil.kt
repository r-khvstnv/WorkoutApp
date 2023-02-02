/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwait(): T{
    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T>{
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@getOrAwait.removeObserver(this)

        }

    }

    this.observeForever(observer)

    try {
        if (!latch.await(2, TimeUnit.SECONDS)){
            throw TimeoutException("Livedata Never gets its value")
        }
    } finally {
        this.removeObserver(observer)
    }

    return data as T
}