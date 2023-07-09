package com.example.detail_screen.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.calendar_screen.presentation.viewmodel.NoteViewModel
import com.example.detail_screen.presentation.theme.Black
import com.example.detail_screen.presentation.theme.DarkPink
import com.example.detail_screen.presentation.theme.DirtyPink
import com.example.detail_screen.presentation.theme.White
import kotlinx.coroutines.delay
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun DetailScreen(
    viewModel: NoteViewModel,
    router: CalendarRouter,
    noteBundle: Bundle
) {

    var str1 = rememberSaveable { mutableStateOf("sss") }
    var str2 = rememberSaveable { mutableStateOf("fff") }
    var isClickedStart = rememberSaveable { mutableStateOf(false) }
    var isClickedFinish = rememberSaveable { mutableStateOf(false) }

    val snackbarVisibleState = rememberSaveable { mutableStateOf(false) }

    val noteName = rememberSaveable { mutableStateOf(noteBundle.getString("noteName") ?: "") }

    val noteDescription =
        rememberSaveable { mutableStateOf(noteBundle.getString("noteDescription") ?: "") }

    val timeStart = rememberSaveable { mutableStateOf(noteBundle.getIntArray("timeStart")) }

    val timeFinish = rememberSaveable { mutableStateOf(noteBundle?.getIntArray("timeFinish")) }

    val id = rememberSaveable { mutableStateOf(noteBundle.getInt("id")) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = noteName.value,
            onValueChange = { noteName.value = it },
            label = { Text("Название") },
            modifier = Modifier.clickable {
                isClickedStart.value = false
                isClickedFinish.value = false
            }
        )

        TextField(
            value = noteDescription.value,
            onValueChange = { noteDescription.value = it },
            label = { Text("Описание") },
            modifier = Modifier.clickable {
                isClickedStart.value = false
                isClickedFinish.value = false
            }
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DirtyPink,
                contentColor = White
            ),
            onClick = {
                isClickedFinish.value = false
                isClickedStart.value = true
            }
        ) {
            Text("Дата и время начала")
        }
        if (isClickedStart.value) {
            WheelDateTimePicker(
                startDateTime = ((getChosenDate(timeStart.value))),
                minDateTime = LocalDateTime.now()
            )
            { snappedDateTime ->
                val list = addDateTime(snappedDateTime)
                timeStart.value = list
                str1.value = snappedDateTime.toString()
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DirtyPink,
                contentColor = White
            ),
            onClick = {
                isClickedStart.value = false
                isClickedFinish.value = true
            }
        ) {
            Text("Дата и время окончания")
        }
        if (isClickedFinish.value) {
            WheelDateTimePicker(
                startDateTime = ((getChosenDate(timeFinish.value))),
                minDateTime = LocalDateTime.now()
            )
            { snappedDateTime ->
                val list = addDateTime(snappedDateTime)
                timeFinish.value = list
                str2.value = snappedDateTime.toString()
            }
        }

        Button(colors = ButtonDefaults.buttonColors(
            backgroundColor = DirtyPink,
            contentColor = White
        ),
            onClick = {
                if (timeStart.value == null || timeFinish.value == null || noteName.value == "" || noteDescription.value == "") {
                    snackbarVisibleState.value = true
                } else {
                    if (noteBundle.isEmpty) {
                        Log.e(
                            "DetailScreen add",
                            "${noteName.value} ${noteDescription.value} ${timeStart.value} ${timeFinish.value}"
                        )
                        viewModel.addNote(
                            convertToTimestamp(timeStart.value)!!,
                            convertToTimestamp(timeFinish.value)!!,
                            noteName.value,
                            noteDescription.value
                        )
                    } else {
                        viewModel.updateNote(
                            id.value, convertToTimestamp(timeStart.value)!!,
                            convertToTimestamp(timeFinish.value)!!,
                            noteName.value,
                            noteDescription.value
                        )
                    }
                    router.openCalendarFragment()
                }
            }) {
            Text(if (noteBundle.isEmpty) "Добавить" else "Сохранить изменения")
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DirtyPink,
                contentColor = White
            ),
            onClick = {

                isClickedStart.value = false
                isClickedFinish.value = false
                router.openCalendarFragment()
            }) {
            Text("Отменить")
        }
        if(!noteBundle.isEmpty) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DarkPink,
                    contentColor = White
                ),
                onClick = {
                    isClickedStart.value = false
                    isClickedFinish.value = false
                    viewModel.deleteNote(id.value)
                    router.openCalendarFragment()
                }) {
                Text("Удалить")
            }
        }
    }

    if (snackbarVisibleState.value) {
        LaunchedEffect(true) {
            delay(800)
            snackbarVisibleState.value = false  // Close Snackbar after 100 milliseconds
        }
        Snackbar(
            backgroundColor = White,
            contentColor = Black,
            action = {
            },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .wrapContentSize()
        ) {
            Text(text = "Заполните все поля перед сохранением")
        }
    }

}

fun convertToTimestamp(list: IntArray?): Timestamp? {
    if (list != null) {
        val dateTime = LocalDateTime.of(list[0], list[1], list[2], list[3], list[4], list[5])
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formattedDateTime = dateTime.format(formatter)
        return Timestamp.valueOf(formattedDateTime)
    }
    return null
}

fun getChosenDate(list: IntArray?): LocalDateTime {
    if (list == null) return LocalDateTime.now()
    return LocalDateTime.of(list[0], list[1], list[2], list[3], list[4], list[5])
}

fun addDateTime(dateTime: LocalDateTime): IntArray {
    val list = ArrayList<Int>()
    list.add(dateTime.year)
    list.add(dateTime.monthValue)
    list.add(dateTime.dayOfMonth)
    list.add(dateTime.hour)
    list.add(dateTime.minute)
    list.add(0)
    return list.toIntArray()
}


