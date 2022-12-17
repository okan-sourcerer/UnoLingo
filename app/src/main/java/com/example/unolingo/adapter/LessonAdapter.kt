package com.example.unolingo.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.model.Lesson
import com.example.unolingo.utils.Utils

class LessonAdapter: RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    val list = Utils.lessonList
    private val TAG = "LessonAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        Log.d(TAG, "onCreateViewHolder: creating viewholder")
        return LessonAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var text: TextView
        lateinit var expandIcon: ImageView
        lateinit var collapsableLayout: ConstraintLayout
        private var isExpanded = false
        fun bind(lesson: Lesson){
            text = itemView.findViewById(R.id.lesson_item_name)
            expandIcon = itemView.findViewById(R.id.lesson_item_expand)
            collapsableLayout = itemView.findViewById(R.id.lesson_item_inner_layout)

            text.text = lesson.courseName

            expandIcon.setOnClickListener {
                if (collapsableLayout.visibility == View.VISIBLE){
                    collapsableLayout.visibility = View.GONE
                } else{
                    collapsableLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    private class LessonWrapper(lessonName: String) {
        var lessonName: String
        var isExpanded: Boolean

        init {
            this.lessonName = lessonName
            isExpanded = false
        }
    }
}