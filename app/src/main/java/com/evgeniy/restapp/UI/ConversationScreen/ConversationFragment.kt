package com.evgeniy.restapp.UI.ConversationScreen

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.evgeniy.restapp.Models.ConversationItem
import com.evgeniy.restapp.R
import com.evgeniy.restapp.RecyclerView.EndlessRecyclerOnScrollListener
import com.evgeniy.restapp.RecyclerView.RecyclerViewAdapter
import com.evgeniy.restapp.databinding.ConversationFragmentBinding


class ConversationFragment : MvpAppCompatFragment(), ConversationContract.View {

    private lateinit var adapter: RecyclerViewAdapter<ConversationItem, ConversationPresenter>

    @InjectPresenter
    lateinit var presenter: ConversationPresenter

    private lateinit var binding: ConversationFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.conversation_fragment,
                container,
                false
        )

        adapter = RecyclerViewAdapter(R.layout.conversation_item, presenter)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                if (binding.etSearch.text.toString().isEmpty()) presenter.loadingData()
            }
        })

        binding.swipeRefresh.setOnRefreshListener { presenter.refreshData() }
        binding.swipeRefresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent
        )

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, p1: Int, p2: Int, p3: Int) {
                presenter.onSearchTextChanged(cs.toString())
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    override fun setItemList(itemList: ArrayList<ConversationItem>) {
        adapter.setList(itemList)
    }

    override fun notifyAdapter() {
        adapter.notifyDataSetChanged()
    }

    override fun filterRecyclerView(toString: String) {
        adapter.filter(toString)
    }

    override fun setLoadingItemsVisibility(visibility: Boolean) {
        binding.itemProgress.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun setLoadingProgressVisibility(visibility: Boolean) {
        binding.progress.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    override fun setRefreshVisibility(visibility: Boolean) {
        binding.swipeRefresh.isRefreshing = visibility
        binding.etSearch.text.clear()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}