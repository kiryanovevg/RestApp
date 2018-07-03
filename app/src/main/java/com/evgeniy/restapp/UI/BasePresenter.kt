package com.evgeniy.restapp.UI

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    private val mCompositeDisposable = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()
    }
}