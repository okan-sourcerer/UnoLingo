package com.example.unolingo.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.activity.MainActivity
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private lateinit var textView: TextView
        private lateinit var accessImage: ImageView
        private lateinit var progressBar: ProgressBar
        private lateinit var progressText: TextView

        fun bind(courseName: String, progress: LessonProgress){
            Log.d("InnerLessonAdapter", "bind: $progress")
            textView = itemView.findViewById(R.id.inner_lesson_item_name)
            textView.text = courseName

            accessImage = itemView.findViewById(R.id.inner_lesson_locked_image)

            progressBar = itemView.findViewById(R.id.inner_lesson_progress_bar)
            progressText = itemView.findViewById(R.id.inner_lesson_progress_text)
            progressText.text = progress.score.toString()
            progressBar.progress = progress.score

            Log.d("InnerLessonAdapter", "bind: ${progress.accessible}, ${progress.completed}")
            if(progress.completed){
                Log.d("InnerLessonAdapter", "bind: should be completed")
                accessImage.visibility = View.GONE
            }
            else if (progress.accessible){
                Log.d("InnerLessonAdapter", "bind: should be accessible")
                accessImage.visibility = View.VISIBLE
                accessImage.setImageResource(R.drawable.ic_unlocked)
            }
            else{
                Log.d("InnerLessonAdapter", "bind: should be locked")
                accessImage.visibility = View.VISIBLE
                accessImage.setImageResource(R.drawable.ic_locked)
                itemView.isEnabled = false
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, QuestionsActivity::class.java)
                intent.putExtra("LESSON", textView.text.toString())
                (itemView.context as MainActivity).questionScoreResult.launch(intent)
                Log.d("InnerLessonAdapter", "bind: updating viewholder pos $adapterPosition")
                notifyItemChanged(adapterPosition)
            }
            accessImage = itemView.findViewById(R.id.inner_lesson_locked_image)

        }
    }
}