package com.example.unolingo

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unolingo.model.Question
import com.example.unolingo.utils.Utils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.unolingo", appContext.packageName)
    }

    @Test
    fun controlImagePathStoreImage() {

        val arrayList = ArrayList<Question>()
        FirebaseFirestore.getInstance().collection("lessons").addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            if (value != null) {
                arrayList.removeAll { true }
                for (index in value.iterator().withIndex()) {
                    val document = value.documents[index.index]
                    val lesson = document.toObject<Question>()
                    if (lesson != null) {
                        arrayList.add(lesson)
                    }
                }
                Utils.storeImages(arrayList, InstrumentationRegistry.getInstrumentation().targetContext)
                assert(File(arrayList[0].filePaths[0]).exists())
            }
        }
    }
}