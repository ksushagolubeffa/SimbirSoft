package com.example.calendar_screen.presentation.rv_note

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_screen.databinding.ItemTaskBinding
import com.example.database.NoteEntity

class NoteAdapter(private val listModel: List<NoteEntity>?,
                 private val onItemClick:(Int) -> (Unit)) : RecyclerView.Adapter<NoteHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder = NoteHolder(
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick = onItemClick
    )

    override fun getItemCount(): Int {
        if (listModel!=null) return listModel.size
        return 0
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        if(listModel!=null) {
            Log.e("NoteAdapter", listModel.toString())
            holder.onBind(listModel[position])
        }
    }
}