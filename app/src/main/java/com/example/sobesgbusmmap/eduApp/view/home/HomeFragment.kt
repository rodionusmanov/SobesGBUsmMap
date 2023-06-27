package com.example.sobesgbusmmap.eduApp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.databinding.EduHomeTabFragmentBinding
import com.example.sobesgbusmmap.eduApp.domain.Homework
import com.example.sobesgbusmmap.eduApp.domain.Lesson
import com.example.sobesgbusmmap.eduApp.utils.LESSONS_FRIDAY
import com.example.sobesgbusmmap.eduApp.utils.LESSONS_MONDAY
import com.example.sobesgbusmmap.eduApp.utils.LESSONS_THURSDAY
import com.example.sobesgbusmmap.eduApp.utils.LESSONS_TUESDAY
import com.example.sobesgbusmmap.eduApp.utils.LESSONS_WEDNESDAY
import java.time.DayOfWeek
import java.time.LocalDate

class HomeFragment : Fragment() {

    private val lessonListExample: List<Lesson> = listOf(
        Lesson(
            "History",
            "8:00 - 8:45",
            true,
            "Ms. Hist"
        ),
        Lesson(
            "Math",
            "9:00 - 9:45",
            false,
            "Mr. Math"
        )
    )

    private val homeworkListExample: List<Homework> = listOf(
        Homework(
            "Math",
            2,
            "3 calculations"
        ),
        Homework(
            "History",
            4,
            "Troy"
        )
    )

    private var _binding: EduHomeTabFragmentBinding? = null
    private val binding: EduHomeTabFragmentBinding
        get() {
            return _binding!!
        }

    private lateinit var homeClassesAdapter: HomeClassesAdapter
    private lateinit var homeworkAdapter: HomeworkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = EduHomeTabFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.classesRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        binding.homeworkRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        initClassesList()
        initHomeworkList()
    }


    private fun initClassesList() {
        homeClassesAdapter = HomeClassesAdapter(getTodayClassesList())
        binding.classesRecyclerView.adapter = homeClassesAdapter
    }

    private fun getTodayClassesList(): List<Lesson> = when (LocalDate.now().dayOfWeek) {
        DayOfWeek.MONDAY -> LESSONS_MONDAY
        DayOfWeek.TUESDAY -> LESSONS_TUESDAY
        DayOfWeek.WEDNESDAY -> LESSONS_WEDNESDAY
        DayOfWeek.THURSDAY -> LESSONS_THURSDAY
        DayOfWeek.FRIDAY -> LESSONS_FRIDAY
        DayOfWeek.SATURDAY -> emptyList()
        DayOfWeek.SUNDAY -> emptyList()
        else -> emptyList()
    }

private fun initHomeworkList() {
    homeworkAdapter = HomeworkAdapter(homeworkListExample)
    binding.homeworkRecyclerView.adapter = homeworkAdapter
}
}