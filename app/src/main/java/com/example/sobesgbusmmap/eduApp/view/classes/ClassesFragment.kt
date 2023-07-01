package com.example.sobesgbusmmap.eduApp.view.classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sobesgbusmmap.databinding.EduClassesFragmentBinding
import com.example.sobesgbusmmap.eduApp.domain.Lesson

class ClassesFragment : Fragment() {

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

    private var _binding: EduClassesFragmentBinding? = null
    private val binding: EduClassesFragmentBinding
        get() {
            return _binding!!
        }

    private lateinit var classesAdapter: ClassesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = EduClassesFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.classesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        initClassesList()
    }

    private fun initClassesList() {
        classesAdapter = ClassesAdapter(lessonListExample)
        binding.classesRecyclerView.adapter = classesAdapter
    }
}