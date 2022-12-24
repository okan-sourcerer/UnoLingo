package com.example.unolingo.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.unolingo.R
import com.example.unolingo.adapter.QuestionSortAdapter
import com.example.unolingo.model.Question
import com.example.unolingo.utils.AutoFitGridLayoutManager
import com.example.unolingo.utils.Utils
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

class QuestionsActivity : AppCompatActivity() {

    private lateinit var playSound: LinearLayout
    private lateinit var questionImage: ImageView
    private lateinit var questionText: TextView
    private lateinit var questionText2: TextView
    private lateinit var questionOptions: LinearLayout
    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button

    private lateinit var questionSortRecycler: RecyclerView
    private lateinit var submit: Button
    private lateinit var answerInput: EditText

    private lateinit var nextQuestionLayout: LinearLayout
    private lateinit var nextQuestionButton: Button

    private var questionList = ArrayList<Question>()

    private var questionIndex: Int = 0

    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_loading)
        spinner = findViewById(R.id.question_loading)

        val lessonName = intent.getStringExtra("LESSON")
        if (lessonName != null){
            FirebaseFirestore.getInstance().collection("lessons").whereEqualTo("lessonID", lessonName)
                .get().addOnSuccessListener {

                    for (item in it.documents.iterator()){
                        val question = item.toObject(Question::class.java)
                        Log.d(TAG, "onCreate: adding questions ${question?.questionNum}" )
                        if (question != null) {
                            questionList.add(question)
                        }
                    }
                    questionList.sortBy { question: Question -> question.questionNum }

                    Utils.storeImages(questionList, this)
                    Log.d(TAG, "onCreate: retrieved questions. size: ${questionList.size}")
                    handleUI(0)
                }
                .addOnFailureListener {
                    Log.d(TAG, "onCreate: failed to retrieve questions")
                    Toast.makeText(this, "Failed to retrieve questions from server.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun handleUI(questionIndex: Int){
        if (questionIndex == questionList.size){
            finish()
            return
        }
        this.questionIndex = questionIndex
        val question = questionList[questionIndex]

        Log.d(TAG, "handleUI: handling ui for question: ${questionIndex + 1}, type ${question.type}, answer: ${question.answer}")
        when(question.type){
            1 -> {
                forBlankQuestions(question, question.type, questionIndex)
            }
            2 -> {
                forOptionQuestions(question, question.type, questionIndex)
            }
            3 -> {
                forOptionQuestions(question, question.type, questionIndex)
            }
            4 -> {
                forBlankQuestions(question, question.type, questionIndex)
            }
            5 -> {
                forSortQuestions(question, questionIndex)
            }
            6 -> {
                forOptionQuestions(question, question.type, questionIndex)
            }
            7 -> {
                forQuestionsWithImageAnswers(question, questionIndex)
            }
            8 -> {
                forOptionQuestions(question, question.type, questionIndex)
            }
        }
    }

    private fun forBlankQuestions(question: Question, type: Int, index: Int){
        setContentView(R.layout.activity_question_fill_blanks)
        questionText = findViewById(R.id.question_blank_question)
        questionText2 = findViewById(R.id.question_blank_question2)
        submit = findViewById(R.id.question_blanks_submit)
        answerInput = findViewById(R.id.question_blanks_input)
        questionImage = findViewById(R.id.question_blank_image)

        if (type == 4){
            questionImage.visibility = View.VISIBLE
            Glide
                .with(this)
                .load(File(question.filePaths[0]))
                .into(questionImage)

        }
        nextQuestionLayout = findViewById(R.id.question_next_layout)
        nextQuestionButton = findViewById(R.id.question_next)

        questionText.text = question.question
        questionText2.visibility = View.VISIBLE
        if (type == 1){
            questionText2.text = question.typeSpecificQuestion
        }

        submit.setOnClickListener {
            if(answerInput.text.toString().lowercase() == question.answer.lowercase()){
                nextQuestionLayout.visibility = View.VISIBLE
                return@setOnClickListener
            }
        }
        nextQuestionButton.setOnClickListener {
            nextAnswer(index)
            return@setOnClickListener
        }
    }

    private fun forOptionQuestions(question: Question, type: Int, index: Int){
        setContentView(R.layout.activity_questions_layout_options)
        questionText = findViewById(R.id.question_question)
//        questionText2 = findViewById(R.id.question_blank_question2)

        questionOptions = findViewById(R.id.question_questions_layout)
        nextQuestionLayout = findViewById(R.id.question_next_layout)
        nextQuestionButton = findViewById(R.id.question_next)
        nextQuestionButton.setOnClickListener {
            nextAnswer(index)
            return@setOnClickListener
        }
        questionText.text = question.question
        if (type == 8){
            questionOptions.visibility = View.GONE
            questionImage = findViewById(R.id.question_image)
            questionImage.visibility = View.VISIBLE
            nextQuestionLayout.visibility = View.VISIBLE
            Glide
                .with(this)
                .load(File(question.filePaths[0]))
                .into(questionImage)

//            questionImage.setImageBitmap(Utils.loadImageFromInternal(question.filePaths[0]))
        } else{
            if (type == 3){
                playSound = findViewById(R.id.question_play_sound)
                playSound.visibility = View.VISIBLE

                playSound.setOnClickListener {
                    question.typeSpecificQuestion // url
//                    TODO("Load audio from firebase storage and start/stop when clicked")
                }
            }
            else if (type == 6){
                Log.d(TAG, "forOptionQuestions: this question has image ${question.question}")
                questionImage = findViewById(R.id.question_image)
                questionImage.visibility = View.VISIBLE
                Glide
                    .with(this)
                    .load(File(question.filePaths[0]))
                    .into(questionImage)
//                questionImage.setImageBitmap(Utils.loadImageFromInternal(question.filePaths[0]))
            }

            option1 = findViewById(R.id.question_option1)
            option2 = findViewById(R.id.question_option2)
            option3 = findViewById(R.id.question_option3)
            option4 = findViewById(R.id.question_option4)

            option1.text = question.option1
            option2.text = question.option2
            option3.text = question.option3
            option4.text = question.option4

            option1.setOnClickListener {
                if (checkOptionAnswer(question, "option1")){
                    nextQuestionLayout.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                else{
//                    TODO("wrong answer + 1")
                }
                it.isEnabled = false
            }
            option2.setOnClickListener {
                if(checkOptionAnswer(question, "option2")){
                    nextQuestionLayout.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                else{
//                    TODO("wrong answer + 1")
                }
                it.isEnabled = false
            }
            option3.setOnClickListener {
                if(checkOptionAnswer(question, "option3")){
                    nextQuestionLayout.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                else{
//                    TODO("wrong answer + 1")
                }
                it.isEnabled = false
            }
            option4.setOnClickListener {
                if(checkOptionAnswer(question, "option4")){
                    nextQuestionLayout.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                else{
//                    TODO("wrong answer + 1")
                }
                it.isEnabled = false
            }
        }
    }

    private fun checkOptionAnswer(question: Question, option: String): Boolean{
        return question.answer == option
    }

    private fun forQuestionsWithImageAnswers(question: Question, index: Int){
        setContentView(R.layout.activity_question_image_answers)
        questionText = findViewById(R.id.question_question)
        nextQuestionLayout = findViewById(R.id.question_next_layout)
        nextQuestionButton = findViewById(R.id.question_next)
        val imageOption1: ImageButton = findViewById(R.id.question_option1)
        val imageOption2: ImageButton = findViewById(R.id.question_option2)
        val imageOption3: ImageButton = findViewById(R.id.question_option3)
        val imageOption4: ImageButton = findViewById(R.id.question_option4)
        questionText.text = question.question

        Glide
            .with(this)
            .load(File(question.filePaths[0]))
            .into(imageOption1)
        Glide
            .with(this)
            .load(File(question.filePaths[1]))
            .into(imageOption2)
        Glide
            .with(this)
            .load(File(question.filePaths[2]))
            .into(imageOption3)
        Glide
            .with(this)
            .load(File(question.filePaths[3]))
            .into(imageOption4)
//        imageOption1.setImageBitmap(Utils.loadImageFromInternal(question.filePaths[0]))
//        imageOption2.setImageBitmap(Utils.loadImageFromInternal(question.filePaths[1]))
//        imageOption3.setImageBitmap(Utils.loadImageFromInternal(question.filePaths[2]))
//        imageOption4.setImageBitmap(Utils.loadImageFromInternal(question.filePaths[3]))

        imageOption1.setOnClickListener {
            if (checkOptionAnswer(question, "option1")){
                nextQuestionLayout.visibility = View.VISIBLE
                return@setOnClickListener
            }
            else{
//                    TODO("wrong answer + 1")
            }
            it.isEnabled = false
        }
        imageOption2.setOnClickListener {
            if(checkOptionAnswer(question, "option2")){
                nextQuestionLayout.visibility = View.VISIBLE
                return@setOnClickListener
            }
            else{
//                    TODO("wrong answer + 1")
            }
            it.isEnabled = false
        }
        imageOption3.setOnClickListener {
            if(checkOptionAnswer(question, "option3")){
                nextQuestionLayout.visibility = View.VISIBLE
                return@setOnClickListener
            }
            else{
//                    TODO("wrong answer + 1")
            }
            it.isEnabled = false
        }
        imageOption4.setOnClickListener {
            if(checkOptionAnswer(question, "option4")){
                nextQuestionLayout.visibility = View.VISIBLE
                return@setOnClickListener
            }
            else{
//                    TODO("wrong answer + 1")
            }
            it.isEnabled = false
        }

        nextQuestionButton.setOnClickListener {
            handleUI(index + 1)
            return@setOnClickListener
        }
    }

    private fun forSortQuestions(question: Question, index: Int){
        setContentView(R.layout.activity_question_sort_question)
        questionText = findViewById(R.id.question_sort_question_text)

        nextQuestionLayout = findViewById(R.id.question_next_layout)
        nextQuestionButton = findViewById(R.id.question_next)

        questionSortRecycler = findViewById(R.id.question_sort_question_recycler)
        questionSortRecycler.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        questionSortRecycler.layoutManager = AutoFitGridLayoutManager(this, questionSortRecycler.measuredWidth)
        val arrayList = question.answer.split(" ") as ArrayList<String>
        arrayList.shuffle()
        questionSortRecycler.adapter = QuestionSortAdapter(arrayList, question.answer, this)
        questionText.text = question.question
    }

    fun nextAnswer(){
        nextQuestionLayout.visibility = View.VISIBLE
        nextQuestionButton.setOnClickListener {
            nextAnswer(this.questionIndex + 1)
        }
    }

    private fun nextAnswer(index: Int){
        setContentView(R.layout.activity_question_loading)
        val question = questionList[index]
        if (question.typeSpecificQuestion.isNotEmpty()){
            val type = question.type
            if (type == 3 || type == 6 || type == 7 || type == 8){
                for (fPath in question.filePaths){
                    val file = File(fPath)
                    file.delete()
                }
            }
        }

        handleUI(index + 1)
    }

    companion object{
        private const val TAG = "QuestionsActivity"
    }

}