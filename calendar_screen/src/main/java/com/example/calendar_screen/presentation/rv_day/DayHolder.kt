package com.example.calendar_screen.presentation.rv_day

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_screen.databinding.ItemHourBinding
import com.example.calendar_screen.presentation.rv_note.NoteAdapter
import com.example.database.NoteEntity

class DayHolder(
    private val binding: ItemHourBinding
): RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: DayEntity, adapter: NoteAdapter){
        with(binding){
            time.text = item.time
            rvTasks.visibility = View.VISIBLE
            rvTasks.adapter = adapter
            Log.e("DayHolder", adapter.itemCount.toString())
        }
    }

    fun onBind(item: DayEntity){
        with(binding){
            time.text = item.time
            rvTasks.visibility = View.GONE
        }
    }
}

