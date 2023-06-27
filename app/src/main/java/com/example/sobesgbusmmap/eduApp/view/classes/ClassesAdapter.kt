package com.example.sobesgbusmmap.eduApp.view.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.EduClassesFragmentItemBinding
import com.example.sobesgbusmmap.databinding.EduHomeClassesItemBinding
import com.example.sobesgbusmmap.eduApp.domain.Lesson

class ClassesAdapter(lessons: List<Lesson>) :
    RecyclerView.Adapter<ClassesAdapter.ClassesViewHolder>() {

    private val lessonList = lessons
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassesViewHolder {

        val binding =
            EduClassesFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ClassesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ClassesViewHolder, position: Int) {
        holder.bind(lessonList[position])
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    inner class ClassesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(lesson: Lesson) {
            EduClassesFragmentItemBinding.bind(itemView).apply {
                tvLessonName.text = lesson.lessonName
                tvLessonInterval.text = lesson.teacher
                ivLessonLogo.setImageResource(R.drawable.history)
                if (lesson.isSkype) {
                    skypeSection.visibility = View.VISIBLE
                } else {
                    skypeSection.visibility = View.INVISIBLE
                }
            }
        }
    }
}