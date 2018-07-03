package com.evgeniy.restapp.Data

import android.content.Context
import com.evgeniy.restapp.Api
import com.evgeniy.restapp.Models.ConversationItem
import com.evgeniy.restapp.Models.ConversationResponse
import com.evgeniy.restapp.Models.ResponseWrapper
import com.evgeniy.restapp.Models.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


class Repository(private val api: Api, private val context: Context) {

    fun getConversations(count: Int, offset: Int): Single<List<ConversationItem>> = api.getConversations(count, offset)
            .flatMap { wrapper: ResponseWrapper<ConversationResponse> ->
                val comma = ","
                val builder = StringBuilder()
                val filtered = wrapper.response.items
                        .filter { it.lastMessage.peerId in 1..1999999999 }

                filtered.forEach{ builder.append(it.lastMessage.peerId).append(comma) }
                builder.deleteCharAt(builder.length - 1)

                Single.zip(
                        Single.just(filtered),
                        api.getUserFullNames(builder.toString()),
                        BiFunction<List<ConversationItem>, ResponseWrapper<List<User>>, List<ConversationItem>>{ t1, t2 ->
                            t1.zip(t2.response) { a, b ->
                                a.user = b
                                a
                            }
                        }
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getString(resId: Int): String = context.getString(resId)
}
