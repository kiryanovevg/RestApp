package com.evgeniy.restapp.UI.ConversationScreen

import com.arellomobile.mvp.InjectViewState
import com.evgeniy.restapp.BaseApplication
import com.evgeniy.restapp.Data.Repository
import com.evgeniy.restapp.Models.ConversationItem
import com.evgeniy.restapp.R
import com.evgeniy.restapp.UI.BasePresenter
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class ConversationPresenter : BasePresenter<ConversationContract.View>(), ConversationContract.Presenter {

    init {
        BaseApplication.component.inject(this)
    }

    @Inject
    lateinit var repository: Repository

    private val count = 20
    private var offset = 0
    private val itemList = ArrayList<ConversationItem>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setItemList(itemList)
        viewState.setLoadingProgressVisibility(true)
        loadingData()
    }

    override fun refreshData() {
        offset = 0
        viewState.setRefreshVisibility(true)
        loadingData()
    }

    override fun loadingData() {
        repository.getConversations(count, offset)
                .subscribe(object : SingleObserver<List<ConversationItem>> {
                    override fun onSubscribe(d: Disposable) {
                        unsubscribeOnDestroy(d)

                        if (offset != 0)
                            viewState.setLoadingItemsVisibility(true)
                    }

                    override fun onSuccess(result: List<ConversationItem>) {
                        if (offset == 0 && !itemList.isEmpty()) {
                            viewState.setItemList(itemList)
                            itemList.clear()
                        }
                        itemList.addAll(result)

                        viewState.notifyAdapter()
                        offset += count

                        viewState.setLoadingProgressVisibility(false)
                        viewState.setRefreshVisibility(false)
                        if (offset != 0) viewState.setLoadingItemsVisibility(false)
                    }

                    override fun onError(e: Throwable) {
                        viewState.setLoadingProgressVisibility(false)
                        viewState.setRefreshVisibility(false)
                        if (offset != 0) viewState.setLoadingItemsVisibility(false)
                        viewState.showMessage(repository.getString(R.string.error))
                    }
                })
    }

    override fun onSearchTextChanged(toString: String) {
        viewState.filterRecyclerView(toString)
    }
}
