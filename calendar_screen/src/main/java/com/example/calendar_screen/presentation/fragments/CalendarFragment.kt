package com.example.calendar_screen.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.calendar_screen.R
import com.example.calendar_screen.databinding.FragmentMonthCalendarBinding
import com.example.calendar_screen.domain.AddNoteUseCase
import com.example.calendar_screen.domain.DeleteNoteUseCase
import com.example.calendar_screen.domain.NoteUseCase
import com.example.calendar_screen.domain.NotesByDayUseCase
import com.example.calendar_screen.domain.NotesByMonthUseCase
import com.example.calendar_screen.domain.UpdateNoteUseCase
import com.example.calendar_screen.presentation.di.CalendarComponentProvider
import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.calendar_screen.presentation.rv_day.DayAdapter
import com.example.calendar_screen.presentation.rv_day.DayEntity
import com.example.calendar_screen.presentation.viewmodel.NoteViewModel
import com.example.database.Note
import com.example.database.NoteEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.sql.Timestamp
import javax.inject.Inject

class CalendarFragment : Fragment(R.layout.fragment_month_calendar) {

    private var wasRead: Int = 0

    private var binding: FragmentMonthCalendarBinding? = null

    private var listOfNotes: List<NoteEntity>? = null

    private var listOfTime: ArrayList<DayEntity> = TimeList.listOfTime

    @Inject
    lateinit var router: CalendarRouter

    @Inject
    lateinit var notesByDayUseCase: NotesByDayUseCase

    @Inject
    lateinit var noteUseCase: NoteUseCase

    @Inject
    lateinit var addNoteUseCase: AddNoteUseCase

    @Inject
    lateinit var updateNoteUseCase: UpdateNoteUseCase

    @Inject
    lateinit var deleteNoteUseCase: DeleteNoteUseCase

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModel.provideFactory(
            notesByDayUseCase,
            noteUseCase,
            addNoteUseCase,
            updateNoteUseCase,
            deleteNoteUseCase
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        var calendarComponent = (requireActivity().application as CalendarComponentProvider)
            .provideCalendarComponent()
        calendarComponent.injectCalendarFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initObservers()
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        wasRead = pref.getInt("wasRead", 0)
        Log.e("pref in onCreateView", wasRead.toString())
        if (wasRead == 0) {
            readJsonFile()
        }
        pref.edit().putInt("wasRead", 1).apply()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMonthCalendarBinding.bind(view)
        Log.e("saved instance state", wasRead.toString())
        Log.e("list of time", listOfTime.toString())
        binding?.calendar?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            onDateClick(year, month + 1, dayOfMonth)
        }
        binding?.btnAdd?.setOnClickListener {
            val bundle = Bundle()
            router.openDetailFragment(bundle)
            //navigation to detail screen WE ARE PASSING BUNDLE (here it is empty)
        }
    }

    private fun onDateClick(year: Int, month: Int, dayOfMonth: Int) {
        rvCreator(year, month, dayOfMonth)
    }

    private fun rvCreator(year: Int, month: Int, dayOfMonth: Int) {
        Log.e("day in fragment", "${dayOfMonth} ${month} ${year}")
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getNotesByDay(dayOfMonth, month, year)
            delay(200L)
            val elementsWithNotes = setListElements(year, month, dayOfMonth)
            Log.e("listOfTime", listOfTime.toString())
            binding?.hoursRecyclerView?.adapter =
                DayAdapter(listOfTime, viewModel, viewLifecycleOwner, router, elementsWithNotes)
            binding?.hoursRecyclerView?.visibility = View.VISIBLE
        }
    }

    private fun setListElements(year: Int, month: Int, dayOfMonth: Int): List<Int> {
        val elementsWithNotes = ArrayList<Int>()
        if (listOfNotes != null) {
            Log.e("all time with list of notes", listOfTime.toString())
            listOfTime.forEach {
                it.list = null
            }
            Log.e("all time with list of notes", listOfTime.toString())
            listOfNotes?.forEach { note ->
                listOfTime.forEach {
                    if (note.dateFinish[2] == note.dateStart[2] && (note.dateFinish[3] - note.dateStart[3]) < 2) {
                        if (it.timeStart == note.dateStart[3] || (it.timeStart == note.dateFinish[3] && note.dateFinish[4] != 0)) {
                            elementsWithNotes.add(it.timeStart)
                            if (it.list != null) {
                                it.list!!.add(note)
                            } else {
                                val noteList: List<NoteEntity> = listOf(note)
                                val arrayList: ArrayList<NoteEntity> = ArrayList(noteList)
                                it.list = arrayList
                            }
                        }
                    } else if (note.dateFinish[2] == note.dateStart[2] && (note.dateFinish[3] - note.dateStart[3]) >= 2) {
                        val helper = ArrayList<Int>()
                        for (number in note.dateStart[3] until note.dateFinish[3]) {
                            helper.add(number)
                        }
                        if (helper.contains(it.timeStart) || (it.timeStart == note.dateFinish[3] && note.dateFinish[4] != 0)) {
                            elementsWithNotes.add(it.timeStart)
                            if (it.list != null) {
                                it.list!!.add(note)
                            } else {
                                val noteList: List<NoteEntity> = listOf(note)
                                val arrayList: ArrayList<NoteEntity> = ArrayList(noteList)
                                it.list = arrayList
                            }
                        }
                    } else {
                        if (note.dateStart[0] == year && note.dateStart[1] == month && note.dateStart[2] == dayOfMonth) {
                            val helper = ArrayList<Int>()
                            for (number in note.dateStart[3] until 24) {
                                helper.add(number)
                            }
                            if (helper.contains(it.timeStart)) {
                                elementsWithNotes.add(it.timeStart)
                                if (it.list != null) {
                                    it.list!!.add(note)
                                } else {
                                    val noteList: List<NoteEntity> = listOf(note)
                                    val arrayList: ArrayList<NoteEntity> = ArrayList(noteList)
                                    it.list = arrayList
                                }
                            }
                        } else if (note.dateFinish[0] == year && note.dateFinish[1] == month && note.dateFinish[2] == dayOfMonth) {
                            val helper = ArrayList<Int>()
                            for (number in 0 until note.dateFinish[3]) {
                                helper.add(number)
                            }
                            if (helper.contains(it.timeStart) || (it.timeStart == note.dateFinish[3] && note.dateFinish[4] != 0)) {
                                elementsWithNotes.add(it.timeStart)
                                if (it.list != null) {
                                    it.list!!.add(note)
                                } else {
                                    val noteList: List<NoteEntity> = listOf(note)
                                    val arrayList: ArrayList<NoteEntity> = ArrayList(noteList)
                                    it.list = arrayList
                                }
                            }
                        } else {
                            elementsWithNotes.add(it.timeStart)
                            if (it.list != null) {
                                it.list!!.add(note)
                            } else {
                                val noteList: List<NoteEntity> = listOf(note)
                                val arrayList: ArrayList<NoteEntity> = ArrayList(noteList)
                                it.list = arrayList
                            }
                        }
                    }
                }
            }
        }
        Log.e("all time with list of notes", listOfTime.toString())
        return elementsWithNotes
    }

    private fun initObservers() {
        viewModel.notes.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { list ->
                    listOfNotes = list
                },
                onFailure = {}
            )
        }
    }

    private fun readJsonFile() {
        try {
            val inputStream = resources.openRawResource(R.raw.note)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = buffer.toString(Charsets.UTF_8)
            val jsonObject = JSONObject(jsonString)

            val id = jsonObject.getInt("id")
            val dateStart = Timestamp(jsonObject.getLong("date_start"))
            val dateFinish = Timestamp(jsonObject.getLong("date_finish"))
            val name = jsonObject.getString("name")
            val description = jsonObject.getString("description")
            viewModel.addNote(dateStart, dateFinish, name, description)

        } catch (e: Exception) {
            Log.e("Calendar Error", e.toString())
            e.printStackTrace()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
