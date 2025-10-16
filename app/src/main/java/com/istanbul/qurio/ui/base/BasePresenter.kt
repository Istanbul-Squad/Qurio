package com.istanbul.qurio.ui.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

abstract class BasePresenter<V : BaseView> {
    private val job = SupervisorJob()
    val coroutineScope = CoroutineScope(Dispatchers.Main + job)

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
        onSuccess: suspend (T) -> Unit = {},
        onError: suspend (Throwable) -> Unit = {},
        onStart: suspend () -> Unit = {},
        onFinally: suspend () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): Job {

        return CoroutineScope(dispatcher + job).launch {
            try {
                onStart()
                val result = execute()
                onSuccess(result)
            } catch (e: Throwable) {
                onError(e)
            } finally {
                onFinally()
            }
        }
    }
}