package com.example.unolingo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.model.ForumSummaryEntity
import com.example.unolingo.utils.Utils

class ForumAdapter() : RecyclerView.Adapter<ForumAdapter.ViewHolder>() {
    private val TAG = "ForumAdapter"
    private val list = Utils.forumSummaryList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forum_item, parent, false)
        Log.d(TAG, "onCreateViewHolder: creating viewholder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: binding view")
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        lateinit var name: TextView
        lateinit var title: TextView
        lateinit var date: TextView

        fun bind(info: ForumSummaryEntity){
            Log.d("ViewHolder", "bind: binding data")
            name = itemView.findViewById(R.id.forum_item_name)
            title = itemView.findViewById(R.id.forum_item_title)
            date = itemView.findViewById(R.id.forum_item_update_date)

            name.text = info.name
            title.text = info.title
            date.text = info.date

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, "This feature is not yet implemented", Toast.LENGTH_SHORT).show()
            }
        }
    }
}