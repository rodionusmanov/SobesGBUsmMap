package com.example.sobesgbusmmap.eduApp.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.EduHomeClassesItemBinding
import com.example.sobesgbusmmap.eduApp.domain.Lesson

class HomeClassesAdapter(lessons: List<Lesson>) :
    RecyclerView.Adapter<HomeClassesAdapter.HomeClassesViewHolder>() {

    private val lessonList = lessons
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeClassesViewHolder {

        val binding =
            EduHomeClassesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeClassesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeClassesViewHolder, position: Int) {
        holder.bind(lessonList[position])
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    inner class HomeClassesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(lesson: Lesson) {
            EduHomeClassesItemBinding.bind(itemView).apply {
                tvLessonName.text = lesson.lessonName
                tvLessonInterval.text = lesson.lessonTimeInterval
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