package com.evgeniy.restapp.UI.ConversationScreen

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.evgeniy.restapp.Models.ConversationItem

class ConversationContract {

    @StateStrategyType(AddToEndSingleStrategy::class)
    interface View : MvpView {

        fun setLoadingProgressVisibility(visibility: Boolean)

        fun setItemList(itemList: ArrayList<ConversationItem>)

        fun setLoadingItemsVisibility(visibility: Boolean)

        @StateStrategyType(SkipStrategy::class)
        fun notifyAdapter()

        @StateStrategyType(SkipStrategy::class)
        fun showMessage(msg: String)

        fun setRefreshVisibility(visibility: Boolean)

        fun filterRecyclerView(toString: String)
    }

    interface Presenter {
        fun loadingData()
        fun refreshData()
        fun onSearchTextChanged(toString: String)
    }
}