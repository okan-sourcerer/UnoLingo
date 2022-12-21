package com.example.unolingo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.model.Lesson
import com.example.unolingo.utils.Animations
import com.example.unolingo.utils.LessonConnectionHandler

class LessonAdapter: RecyclerView.Adapter<LessonAdapter.ViewHolder>() {
    var list = LessonConnectionHandler.lessonList

    init{
        LessonConnectionHandler.retrieveLessons(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        Log.d(TAG, "onCreateViewHolder: creating viewholder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var text: TextView
        lateinit var expandIcon: ImageView
        lateinit var collapsableRecyclerView: RecyclerView
        lateinit var cardView: CardView
        private var isExpanded = false
        fun bind(lesson: Lesson){
            text = itemView.findViewById(R.id.lesson_item_name)
            expandIcon = itemView.findViewById(R.id.lesson_item_expand)
            collapsableRecyclerView = itemView.findViewById(R.id.lesson_item_inner_layout)
            cardView = itemView.findViewById(R.id.lesson_layout)

            text.text = lesson.name

            collapsableRecyclerView.adapter = InnerLessonAdapter(lesson)
            collapsableRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            cardView.setOnClickListener {
                if (isExpanded){
                    performCollapse()
                } else{
                    performExpand()
                }

                Log.d(TAG, "bind: isExpanded : $isExpanded")
                isExpanded = !isExpanded
            }
        }

        private fun performExpand(){
            Animations.expand(collapsableRecyclerView)
            expandIcon.animate().rotation(180f)
        }

        private fun performCollapse(){
            Animations.collapse(collapsableRecyclerView)
            expandIcon.animate().rotation(0f)
        }
    }

    companion object {
        private const val TAG = "LessonAdapter"
    }
}