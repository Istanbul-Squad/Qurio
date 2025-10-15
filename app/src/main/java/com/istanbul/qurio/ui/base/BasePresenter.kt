package com.istanbul.qurio.ui.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

abstract class BasePresenter<V : BaseView> {
    private val job = SupervisorJob()
    protected val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    protected var view: V? = null
        private set

    protected fun attachView(view: V) {
        this.view = view
    }

    protected fun detachView() {
        coroutineScope.coroutineContext.cancelChildren()
        view = null
    }

    open fun onDestroy() {
        job.cancel()
    }

    protected fun <T> tryToExecute(
        execute: suspend () -> T,
        onSuccess: ((T) -> Unit) = {},
        onError: (Throwable) -> Unit = {},
        onStart: suspend () -> Unit = {},
        onFinally: () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): Job {
        val handler = CoroutineExceptionHandler { _, throwable ->
            onError(
                throwable
            )
        }
        return CoroutineScope(dispatcher + job + handler).launch {
            onStart()
            runCatching { execute() }
                .onSuccess(onSuccess)
                .onFailure(onError)
            onFinally()
        }
    }
}