package com.evgeniy.restapp.RecyclerView

interface SearchableItem {
    fun contains(string: String): Boolean
}