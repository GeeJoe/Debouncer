package com.geejoe.debouncer

import android.view.View
import com.jakewharton.rxbinding.view.RxView
import java.util.concurrent.TimeUnit

/**
 * Created by lizhiyue on 2020-03-20.
 */
fun View.throttleClick(onClick: (View) -> Unit) {
  val subscription = RxView.clicks(this)
      .throttleFirst(1, TimeUnit.SECONDS)
      .subscribe({
        onClick.invoke(this)
      }, {
        //no-op
      })
  this.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
    override fun onViewDetachedFromWindow(v: View) {
      v.removeOnAttachStateChangeListener(this)
      subscription.unsubscribe()
    }

    override fun onViewAttachedToWindow(v: View) {
    }
  })
}