package com.evgeniy.restapp.Models

import com.evgeniy.restapp.RecyclerView.SearchableItem

data class ResponseWrapper<out T>(val response: T)
data class ConversationResponse(val count: Int, val unreadCount: Int, val items: List<ConversationItem>)
data class Message(val date: Int,
                   val fromId: Int,
                   val id: Int,
                   val peerId: Int,
                   val text: String)
data class User(val id: Int,
                val firstName: String,
                val lastName: String,
                val photo_200: String)

data class ConversationItem(val lastMessage: Message, var user: User) : SearchableItem {
    override fun contains(string: String): Boolean {
        return user.firstName.toLowerCase().contains(string) || user.lastName.toLowerCase().contains(string)
    }
}

