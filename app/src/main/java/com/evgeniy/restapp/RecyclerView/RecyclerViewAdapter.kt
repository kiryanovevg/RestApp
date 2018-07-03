package com.evgeniy.restapp.RecyclerView

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.evgeniy.restapp.BR
import java.util.ArrayList

class RecyclerViewAdapter<T, P>(val layout: Int, val presenter: P) :
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private lateinit var fullList: List<T>
    private lateinit var filteredList: List<T>

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val binding = DataBindingUtil.bind<ViewDataBinding>(view)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: T = filteredList[position]
        holder.binding?.setVariable(BR.item, item)
        holder.binding?.setVariable(BR.presenter, presenter)
        holder.binding?.executePendingBindings()
    }

    override fun getItemCount() = filteredList.size

    fun setList(itemList: List<T>) {
        fullList = itemList
        filteredList = itemList
        notifyDataSetChanged()
    }

    fun filter(string: String) {
        filteredList = if (string.isEmpty()) fullList
        else fullList.filter { (it as SearchableItem).contains(string) }
        notifyDataSetChanged()
    }
}