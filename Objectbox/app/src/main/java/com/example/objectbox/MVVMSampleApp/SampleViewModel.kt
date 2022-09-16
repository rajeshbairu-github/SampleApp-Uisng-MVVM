package com.example.objectbox.MVVMSampleApp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.objectbox.User

class SampleViewModel: ViewModel() {

    private var mutableToDoItems: MutableLiveData<List<User>>? = null
    private val dbOperations: DB_Operations = DB_Operations()

    val repository: Repository = Repository()


    fun getAllNotes(): LiveData<List<User>>? {

        return repository.getAllNotes()
    }

    fun addNotes(noteText:String, comment:String)
    {
        repository.add(noteText, comment)
    }


    fun remove(user: User)
    {
        repository.remove(user)
    }
}