package com.example.unolingo.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.adapter.ForumAdapter
import com.example.unolingo.utils.Utils

class ForumFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val TAG: String = "ForumFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.d(TAG, "onCreate: ")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forum, container, false)
        recyclerView = view.findViewById(R.id.forum_recycler)
        Log.d(TAG, "onCreateView: creating adapter for recyclerview")
        recyclerView.adapter = ForumAdapter()
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_forum, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add_discussion -> {
                Toast.makeText(activity?.applicationContext, "This feature is not yet implemented.", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ForumFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}