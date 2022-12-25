package com.example.unolingo.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.activity.QuestionsActivity
import com.example.unolingo.model.Lesson
import com.example.unolingo.model.LessonProgress

class InnerLessonAdapter(val lesson: Lesson, val progress: ArrayList<LessonProgress>) : RecyclerView.Adapter<InnerLessonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inner_lesson_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lesson.lessons[position],
            progress.first { it.lessonID == lesson.lessons[position] })
    }

    override fun getItemCount() = lesson.lessons.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var textView: TextView
        private lateinit var accessImage: ImageView
        private lateinit var progressBar: ProgressBar
        private lateinit var progressText: TextView

        fun bind(courseName: String, progress: LessonProgress){
            textView = itemView.findViewById(R.id.inner_lesson_item_name)
            textView.text = courseName

            progressBar = itemView.findViewById(R.id.inner_lesson_progress_bar)
            progressText = itemView.findViewById(R.id.inner_lesson_progress_text)
            progressText.text = progress.score.toString()
            progressBar.progress = progress.score

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, QuestionsActivity::class.java)
                intent.putExtra("LESSON", textView.text.toString())
                it.context.startActivity(intent)
            }
            accessImage = itemView.findViewById(R.id.inner_lesson_locked_image)
            if(progress.isCompleted){
                // TODO(change background to green)
                accessImage.visibility = View.GONE
            }
            else if (progress.isAccessible){
                accessImage.visibility = View.VISIBLE
                accessImage.setImageResource(R.drawable.ic_unlocked)
            }
            else{
                accessImage.visibility = View.VISIBLE
                accessImage.setImageResource(R.drawable.ic_locked)
            }
        }
    }
}