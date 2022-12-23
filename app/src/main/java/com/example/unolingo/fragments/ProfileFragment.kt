package com.example.unolingo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.unolingo.R
import com.example.unolingo.utils.Utils

class ProfileFragment : Fragment() {

    private lateinit var name: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        name = view.findViewById(R.id.profile_name)
        name.text = Utils.userName
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
            }
    }
}