package com.geejoe.debouncer

import rx.Observer
import rx.Scheduler
import rx.Subscription
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by lizhiyue on 2020-03-19.
 */
class Debouncer<T> private constructor(callable: (T) -> Unit,
                                       timeout: Long, unit: TimeUnit,
                                       callableSchedulers: Scheduler)
  : Observer<T>, Subscription {

  private var mInnerSubscription: Subscription
  private val mSubject by lazy {
    PublishSubject.create<T>()
  }

  init {
    mInnerSubscription = mSubject.asObservable()
        .debounce(timeout, unit)
        .observeOn(callableSchedulers)
        .subscribe({ value ->
          callable.invoke(value)
        }, {
          //no-op
        })
  }

  override fun onError(e: Throwable?) {
    mSubject.onError(e)
  }

  override fun onNext(t: T) {
    mSubject.onNext(t)
  }

  override fun onCompleted() {
    mSubject.onCompleted()
  }

  override fun isUnsubscribed(): Boolean {
    return mInnerSubscription.isUnsubscribed
  }

  override fun unsubscribe() {
    return mInnerSubscription.unsubscribe()
  }

  companion object {
    fun <T> create(callable: (T) -> Unit,
                   timeout: Long, unit: TimeUnit,
                   callableSchedulers: Scheduler): Debouncer<T> {
      return Debouncer(callable, timeout, unit, callableSchedulers)
    }
  }
}