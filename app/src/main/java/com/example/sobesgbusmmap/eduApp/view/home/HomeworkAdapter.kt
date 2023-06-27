package com.example.sobesgbusmmap.eduApp.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sobesgbusmmap.R
import com.example.sobesgbusmmap.databinding.EduHomeworkItemBinding
import com.example.sobesgbusmmap.eduApp.domain.Homework
import com.example.sobesgbusmmap.eduApp.domain.Lesson

class HomeworkAdapter (homework: List<Homework>) :
    RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>() {

    private val homeworkList = homework
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkViewHolder {

        val binding =
            EduHomeworkItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeworkViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        holder.bind(homeworkList[position])
    }

    override fun getItemCount(): Int {
        return homeworkList.size
    }

    inner class HomeworkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(homework: Homework) {
            EduHomeworkItemBinding.bind(itemView).apply {
                tvHomeworkName.text = homework.homeworkName
                tvHomeworkDaysLeft.text = "${homework.homeworkDaysLeft} days left"
                tvHomeworkDescription.text = homework.homeworkDescription
                ivLessonLogo.setImageResource(R.drawable.history)
            }
        }
    }
}