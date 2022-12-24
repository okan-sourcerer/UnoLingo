package com.example.unolingo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.activity.QuestionsActivity

class QuestionSortAdapter(private val words: ArrayList<String>, private val answer: String, private val context: Context) : RecyclerView.Adapter<QuestionSortAdapter.ViewHolder>(){

    private var stats: LatestStats = LatestStats(0, null, 0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_sort_item, parent, false)

        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(stats, words[position])
    }

    override fun getItemCount() = words.size

    private fun checkAnswer(){

    }

    inner class ViewHolder(itemView: View, private val context: Context): RecyclerView.ViewHolder(itemView){

        private lateinit var button: Button
        fun bind(stats: LatestStats, word: String){

            button = itemView.findViewById(R.id.question_text_button)
            button.text = word
            button.setOnClickListener {
                if (stats.counter == 0){
                    stats.prevButton = button
                    stats.counter++
                    stats.lastIndex = adapterPosition
                }
                else{
                    stats.counter = 0
                    val tempWord: String = button.text.toString()
                    button.text = stats.prevButton?.text
                    stats.prevButton?.text = tempWord

                    words[adapterPosition] = button.text.toString()
                    words[stats.lastIndex] = tempWord
                    if (answer == words.joinToString(" ")){
                        Log.d("QuestionSortAdapter", "bind: doğru cevaba ulaşıldı")
                        if (context is QuestionsActivity){
                            context.nextAnswer()
                        }
                        ((QuestionsActivity) )
                    }
                }
            }
        }
    }

    data class LatestStats(var counter: Int, var prevButton: Button?, var lastIndex: Int)

}