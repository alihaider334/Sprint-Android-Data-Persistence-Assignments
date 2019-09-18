package com.ali.readinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val NEW_BOOK_REQUEST = 1
        const val EDIT_BOOK_REQUEST = 2

    }

 var bookList = mutableListOf<Book>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     // prefs.removePrefs()
        btn_add_new_book.setOnClickListener {
            val addNewBookIntent = Intent(this, EditBookActivity::class.java)
            addNewBookIntent.putExtra("NEW_BOOK_ID", listLayout.childCount)
            startActivityForResult(addNewBookIntent, NEW_BOOK_REQUEST)
        }
        bookList= prefs.readAllEntries()
        bookList.forEach {
          listLayout.addView(buildItemView(it))
       }
       // println("test${bookList.size}")




    }

    override fun onResume() {
        super.onResume()
        listLayout.removeAllViews()
        bookList.forEach { book ->
            listLayout.addView(buildItemView(book))
        }
    }

    fun buildItemView(book: Book): TextView {
        val view = TextView(this)
        view.text = book.title
        view.textSize=36f
        view.setOnClickListener {
            val editBookIntent = Intent(this, EditBookActivity::class.java)
            editBookIntent.putExtra("CSV_BOOK", book.toCsvString())
            startActivityForResult(editBookIntent, EDIT_BOOK_REQUEST)
        }
        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var book=Book(data?.getStringExtra("RETURNED_BOOK").toString())
            if (requestCode == NEW_BOOK_REQUEST) {
                bookList.add(book)
               listLayout.addView(buildItemView(book))
                prefs.createEntry(book)
            } else if (requestCode == EDIT_BOOK_REQUEST) {
                bookList[book.id?.toInt().toString().toInt()]=book
               var view:TextView= listLayout.getChildAt(book.id?.toInt().toString().toInt()) as TextView
                view.text= book.title
                prefs.updateEntry(book)

            }

        }
    }



}
