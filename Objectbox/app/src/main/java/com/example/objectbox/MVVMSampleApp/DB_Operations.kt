package com.example.objectbox.MVVMSampleApp

import android.util.Log
import com.example.objectbox.ObjectBox
import com.example.objectbox.User
import com.example.objectbox.User_
import io.objectbox.Box
import io.objectbox.query.Query
import java.util.*

class DB_Operations {

    private var notesBox: Box<User> = ObjectBox.store.boxFor(User::class.java)
    var notesQuery: Query<User> = notesBox.query().order(User_.date).build()


    fun getAllNotes(): List<User> {
        return notesQuery.find()
    }

    fun add(noteText:String, comment:String)
    {
        val note = User()

        note.text=noteText
        note.comment=comment
        note.date= Date()
        notesBox.put(note)

        Log.i("Op","Added")
    }

    fun remove(user: User)
    {
        notesBox.remove(user)

        Log.i("Op","Removed")
    }

}