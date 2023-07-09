package com.example.calendar_screen.presentation.rv_day

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import kotlinx.coroutines.*
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_screen.databinding.ItemHourBinding
import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.calendar_screen.presentation.rv_note.NoteAdapter
import com.example.calendar_screen.presentation.viewmodel.NoteViewModel
import com.example.database.NoteEntity

class DayAdapter(
    private var listModel: List<DayEntity>,
    private var viewModel: NoteViewModel,
    private var viewLifecycleOwner: LifecycleOwner,
    private var router: CalendarRouter,
    private var elementsWithNotes: List<Int>
) : ListAdapter<DayEntity, DayHolder>(DayEntityDiffCallback())  {

    var noteInfo: NoteEntity? = null

    init {
        setHasStableIds(true) // Add this line to enable stable IDs
    }

    override fun getItemId(position: Int): Long {
        return listModel[position].timeStart.toLong() // Return a unique identifier for each item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder = DayHolder(
        binding = ItemHourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return listModel.size
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        if (listModel[position].list != null && elementsWithNotes.contains(listModel[position].timeStart)) {
            Log.e("DayAdapter", listModel[position].timeStart.toString())
            Log.e("DayAdapter", listModel[position].list.toString())
            viewLifecycleOwner.lifecycleScope.launch {
                delay(300L)
                val adapter = NoteAdapter(listModel[position].list) { item ->
                    onItemClick(item)
                }
                holder.onBind(listModel[position], adapter)
            }
        } else {
            Log.e("DayAdapter", listModel[position].timeStart.toString())
            holder.onBind(listModel[position])
        }
    }

    private fun onItemClick(id: Int) {
        initObservers()
        val dataBundle = Bundle()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNoteById(id)
            delay(300L)
            dataBundle.putString("noteDescription", noteInfo?.description)
            dataBundle.putString("noteName", noteInfo?.name)
            dataBundle.putInt("id", id)
            dataBundle.putIntArray("timeStart", noteInfo?.dateStart?.toIntArray())
            dataBundle.putIntArray("timeFinish", noteInfo?.dateFinish?.toIntArray())
            router.openDetailFragment(dataBundle)
        }
    }

    private fun initObservers() {
        viewModel.note.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { item ->
                    noteInfo = item
                },
                onFailure = {}
            )
        }
    }
}

//
//Log.e("DayAdapter", listModel[position].timeStart.toString())
//Log.e("DayAdapter", listModel[position].list.toString())
//Log.e("Adapter from db", noteInfo.toString())
//Log.e("Bundle adapter", (dataBundle.isEmpty).toString())
