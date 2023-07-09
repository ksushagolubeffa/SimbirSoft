package com.example.calendar_screen.presentation.rv_note

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_screen.databinding.ItemHourBinding
import com.example.calendar_screen.databinding.ItemTaskBinding
import com.example.database.NoteEntity

class NoteHolder(
    private val binding: ItemTaskBinding,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun onBind(item: NoteEntity) {
        with(binding) {
            root.setOnClickListener {
                onItemClick(item.id)
            }
            name.text = item.name
            time.text =
                "${item.dateStart[3]}:${item.dateStart[4]} - ${item.dateFinish[3]}:${item.dateFinish[4]}"
        }
    }
}
