package com.example.objectbox.MVVMSampleApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.objectbox.*
import com.example.objectbox.databinding.ActivityMvvmBinding
import io.objectbox.Box
import io.objectbox.query.Query
import java.text.DateFormat
import java.util.*

class MVVM : AppCompatActivity() {

    lateinit var activityMvvmBinding:ActivityMvvmBinding
    lateinit var model: SampleViewModel


    lateinit var notesAdapter: NotesAdapter

    lateinit var notesBox: Box<User>
    lateinit var notesQuery: Query<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMvvmBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)

        notesBox= ObjectBox.store.boxFor(User::class.java)
        notesQuery=notesBox.query().order(User_.text).build()


        setUpViews()

       model= ViewModelProvider(this)[SampleViewModel::class.java]

        val list: LiveData<List<User>>? =model.getAllNotes()

        list?.observe(this){
            updateNotes()
        }

        activityMvvmBinding.buttonAdd.setOnClickListener {
            add()
        }

    }

    private fun setUpViews() {

        val listView: ListView = findViewById(R.id.listViewNotes)
        listView.onItemClickListener = noteClickListener

        notesAdapter = NotesAdapter()
        listView.adapter = notesAdapter
        updateNotes()

        activityMvvmBinding.buttonAdd.isEnabled=true

        activityMvvmBinding.editTextNote.setOnEditorActionListener { _, actionId, _ ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
              add()
                return@setOnEditorActionListener true
            }
            false
        }
        activityMvvmBinding.editTextNote.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enable = s.isNotEmpty()
                activityMvvmBinding.buttonAdd.isEnabled = enable
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
    }


    var noteClickListener =
        AdapterView.OnItemClickListener { parent, view, position, id ->
            val user: User = notesAdapter.getItem(position)
            model.remove(user)
            updateNotes()
        }

    fun add()
    {
        var text: String =activityMvvmBinding.editTextNote.text.toString()
        activityMvvmBinding.editTextNote.text.clear()

        val df: DateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
        val comment = "Added on " + df.format(Date())

       // Toast.makeText(this,"Add",Toast.LENGTH_SHORT).show()

        model.addNotes(text,comment)

        updateNotes()
    }

    private fun updateNotes() {
      //  Toast.makeText(this,"Update",Toast.LENGTH_SHORT).show()
        val notes: List<User> = notesQuery.find()
        notesAdapter.setNotes(notes)
    }
}