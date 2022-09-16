package com.example.objectbox

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.objectbox.NotesAdapter
import com.example.objectbox.ObjectBox
import com.example.objectbox.R
import com.example.objectbox.User
import io.objectbox.Box
import io.objectbox.query.Query
import java.text.DateFormat
import java.util.*


open class MainActivity : AppCompatActivity() {

    lateinit var editText:EditText
    lateinit var addNoteButton:Button
    lateinit var notesAdapter: NotesAdapter

    lateinit var notesBox:Box<User>

    lateinit var notesQuery:Query<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setUpViews()

        notesBox= ObjectBox.store.boxFor(User::class.java)

        notesQuery=notesBox.query().order(User_.text).build()


    }

    private fun updateNotes() {
        val notes: List<User> = notesQuery.find()
        notesAdapter.setNotes(notes)
    }

    private fun setUpViews() {

        val listView: ListView = findViewById(R.id.listViewNotes)
        listView.onItemClickListener = noteClickListener

        notesAdapter = NotesAdapter()
        listView.adapter = notesAdapter
        addNoteButton = findViewById<View>(R.id.buttonAdd) as Button
        addNoteButton.setEnabled(false)
        editText = findViewById<View>(R.id.editTextNote) as EditText

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId === EditorInfo.IME_ACTION_DONE) {
                addNote()
                return@setOnEditorActionListener true
            }
            false
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enable = s.isNotEmpty()
                addNoteButton.isEnabled = enable
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
    }

    fun onAddButtonClick(view: View?) {
        addNote()
    }

    private fun addNote() {
        val noteText = editText.text.toString()
        editText.setText("")
        val df: DateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)
        val comment = "Added on " + df.format(Date())

        val note = User()
        note.text=noteText
        note.comment=comment
        note.date=Date()
        notesBox.put(note)
        Log.d("App.TAG" ,"Inserted new note, ID: " + note.id)
        updateNotes()
    }

    var noteClickListener =
        OnItemClickListener { parent, view, position, id ->
            val note: User = notesAdapter.getItem(position)
            notesBox.remove(note)
            Log.d("App.TAG", "Deleted note, ID: " + note.id)
            updateNotes()
        }


}