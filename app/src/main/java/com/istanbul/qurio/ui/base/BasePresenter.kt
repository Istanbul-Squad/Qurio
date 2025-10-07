package com.istanbul.qurio.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

abstract class BasePresenter<V : BaseView> {
    private val job = SupervisorJob()
    protected val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    protected var view: V? = null
        private set

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        coroutineScope.coroutineContext.cancelChildren()
        view = null
    }

    open fun onDestroy() {
        job.cancel()
    }
}