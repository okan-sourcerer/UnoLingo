package com.example.unolingo.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.util.Log
import com.example.unolingo.model.ForumSummaryEntity
import com.example.unolingo.model.LessonProgress
import com.example.unolingo.model.Question
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.*

object Utils {

    private const val TAG = "Utils"

    val forumSummaryList: ArrayList<ForumSummaryEntity> = ArrayList()

    var userName: String = ""

    fun storeImages(questions: ArrayList<Question>, context: Context){
        val storage = Firebase.storage

        for(question in questions){
            Log.d(TAG, "storeImages: for ${question.questionNum} ${question.typeSpecificQuestion} ${question.type}")
            if (question.type == 3){ // sesli soru. ses kaydet
                question.filePaths = arrayListOf(storeAudio(storage, question.typeSpecificQuestion, context))
            }
            else if(question.type == 6 || question.type == 8 || question.type == 4){ // tek resim alınacak
                question.filePaths = arrayListOf(storeImage(storage, question.typeSpecificQuestion, context))
            }
            else if(question.type == 7){
                val al = arrayListOf<String>()
                al.add(storeImage(storage, question.option1, context))
                al.add(storeImage(storage, question.option2, context))
                al.add(storeImage(storage, question.option3, context))
                al.add(storeImage(storage, question.option4, context))
                question.filePaths = al
            }
        }
    }

    private fun storeAudio(storage: FirebaseStorage, path: String, context: Context): String{
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val KB_100: Long = 100 * 1024
        Log.d(TAG, "storeAudio: path is $path for")
        val file = File(directory, path.split("/")[1])
        if (file.exists()){
            return file.absolutePath
        }
        file.createNewFile()
        storage.reference.child(path).getFile(file).addOnSuccessListener {
            Log.d(TAG, "storeAudio: stored audio file successfully")
        }
            .addOnFailureListener {
                Log.e(TAG, "storeAudio: failed to download audio file $it")
            }
        return file.absolutePath
    }

    private fun storeImage(storage: FirebaseStorage, path: String, context: Context): String{
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val ONE_MEGABYTE: Long = 1024 * 1024

        Log.d(TAG, "storeImage: path is $path")
        val pathList = path.split("/")
        val file = File(directory, pathList[1])
        if (file.exists()){
            return file.absolutePath
        }
        val fileOutputStream = FileOutputStream(file)
        storage.reference.child(path).getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bm = BitmapFactory.decodeByteArray(it, 0, it.size)
            bm.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream)
            Log.d(TAG, "storeImage: file is saved to ${file.absolutePath}")

            fileOutputStream.close()
        }
            .addOnFailureListener {
                Log.e(TAG, "storeImage: process has failed. $it")
            }
        return file.absolutePath
    }

    fun loadImageFromInternal(path: String): Bitmap?{
        var b: Bitmap? = null
        try {
            Log.d(TAG, "loadImageFromStorage: image path $path")
            val f = File(path)
            Log.d(TAG, "loadImageFromStorage: ${f.absolutePath}")
            b = BitmapFactory.decodeStream(FileInputStream(f))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return b
    }
}