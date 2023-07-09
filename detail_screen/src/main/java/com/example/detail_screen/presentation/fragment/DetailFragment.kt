package com.example.detail_screen.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.calendar_screen.domain.AddNoteUseCase
import com.example.calendar_screen.domain.DeleteNoteUseCase
import com.example.calendar_screen.domain.NoteUseCase
import com.example.calendar_screen.domain.NotesByDayUseCase
import com.example.calendar_screen.domain.UpdateNoteUseCase
import com.example.calendar_screen.presentation.di.CalendarComponentProvider
import com.example.calendar_screen.presentation.router.CalendarRouter
import com.example.calendar_screen.presentation.viewmodel.NoteViewModel
import com.example.detail_screen.presentation.di.DetailComponentProvider
import javax.inject.Inject

class DetailFragment: Fragment() {

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModel.provideFactory(
            notesByDayUseCase,
            noteUseCase,
            addNoteUseCase,
            updateNoteUseCase,
            deleteNoteUseCase
        )
    }

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

    @Inject
    lateinit var router: CalendarRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            arguments?.let{
                setContent {
                    DetailScreen(viewModel, router, it)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        var detailComponent = (requireActivity().application as DetailComponentProvider)
            .provideDetailComponent()
        detailComponent.injectDetailFragment(this)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeTestingTheme {
//        DetailScreen()
//    }
//}