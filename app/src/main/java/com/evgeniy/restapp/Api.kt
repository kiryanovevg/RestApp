package com.evgeniy.restapp

import com.evgeniy.restapp.Models.ConversationResponse
import com.evgeniy.restapp.Models.ResponseWrapper
import com.evgeniy.restapp.Models.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("messages.getConversations")
    fun getConversations(@Query("count") count: Int,
                         @Query("offset") offset: Int): Single<ResponseWrapper<ConversationResponse>>

    @GET("users.get?fields=photo_200")
    fun getUserFullNames(@Query("user_ids") ids: String): Single<ResponseWrapper<List<User>>>
}