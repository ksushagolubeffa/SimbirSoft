package com.example.calendar_screen.presentation.rv_day

import androidx.recyclerview.widget.DiffUtil

class DayEntityDiffCallback : DiffUtil.ItemCallback<DayEntity>() {
    override fun areItemsTheSame(oldItem: DayEntity, newItem: DayEntity): Boolean {
        return oldItem.timeStart == newItem.timeStart
    }

    override fun areContentsTheSame(oldItem: DayEntity, newItem: DayEntity): Boolean {
        return oldItem.time == newItem.time
    }
}