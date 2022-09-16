package com.example.objectbox

import android.content.Context
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}