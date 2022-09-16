package com.example.objectbox.MVVMSampleApp

import androidx.lifecycle.MutableLiveData
import com.example.objectbox.User

class Repository {

    private var mutableToDoItems: MutableLiveData<List<User>>? = null
    val dbOperations: DB_Operations = DB_Operations()


    fun getAllNotes(): MutableLiveData<List<User>>?
    {

        val temp:List<User> =dbOperations.getAllNotes()
        mutableToDoItems?.value=temp
        return mutableToDoItems
    }

    fun remove(user: User)
    {
        dbOperations.remove(user)
    }

    fun add(noteText:String, comment:String)
    {
        dbOperations.add(noteText,comment)
    }

}