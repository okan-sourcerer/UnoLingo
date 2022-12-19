package com.example.unolingo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.model.Lesson

class InnerLessonAdapter(val lesson: Lesson) : RecyclerView.Adapter<InnerLessonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inner_lesson_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lesson.lessons[position])
    }

    override fun getItemCount() = lesson.lessons.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var textView: TextView

        fun bind(courseName: String){
            textView = itemView.findViewById(R.id.inner_lesson_item_name)
            textView.text = courseName
        }

    }
}