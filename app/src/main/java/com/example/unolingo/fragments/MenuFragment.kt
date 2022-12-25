package com.example.unolingo.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unolingo.R
import com.example.unolingo.adapter.LessonAdapter
import com.example.unolingo.utils.LessonConnectionHandler
import com.example.unolingo.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var nameTextView: TextView
    private lateinit var menuAdapter: LessonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(activity?.intent?.getBooleanExtra("NEW_REGISTRATION", false) == true){
            LessonConnectionHandler.insertInitialLessonProgress()
        }
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerView = view.findViewById(R.id.menu_recycler)
        menuAdapter = LessonAdapter()
        recyclerView.adapter = menuAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        nameTextView = view.findViewById(R.id.menu_linear_text_name)

        FirebaseAuth.getInstance().uid?.let { it ->
            FirebaseFirestore.getInstance().collection("users").document(
                it
            ).get().addOnSuccessListener {
                Log.d(TAG, "onCreate: userName is received from server: ${it.get("name")}")
                nameTextView.text = it.get("name") as CharSequence?
                Utils.userName = nameTextView.text.toString()
            }
        }

        return view
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: restored view")
        super.onViewStateRestored(savedInstanceState)
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            MenuFragment().apply {
                arguments = Bundle().apply {
                }
            }

        private const val TAG = "MenuFragment"
    }
}