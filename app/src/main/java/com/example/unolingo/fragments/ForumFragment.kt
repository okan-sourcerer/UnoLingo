package com.example.unolingo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForumFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ForumFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}