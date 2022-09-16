package com.example.objectbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class NotesAdapter : BaseAdapter() {
    private var dataset: List<User>

    private class NoteViewHolder(itemView: View?) {
        lateinit var text: TextView
        lateinit var comment: TextView

        init {
            if (itemView != null) {
                text=itemView.findViewById(R.id.textViewNoteText)
                comment = itemView.findViewById(R.id.textViewNoteComment)
            }

        }
    }

    fun setNotes(notes: List<User>) {
        dataset = notes
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView: View? = convertView
        val holder: NoteViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
            holder = NoteViewHolder(convertView)
            convertView.setTag(holder)
        } else {
            holder = convertView.getTag() as NoteViewHolder
        }
        val note: User = getItem(position)
        holder.text.text = note.text
        holder.comment.text = note.comment
        return convertView
    }

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): User {
        return dataset[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    init {
        dataset = ArrayList()
    }
}