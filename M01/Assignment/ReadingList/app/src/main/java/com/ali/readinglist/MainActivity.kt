package com.ali.readinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bookList= mutableListOf<Book>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bookList.add(Book("Book A","like it",false,"0"))
        bookList.add(Book("Book B","love it",true,"1"))
        bookList.add(Book("Book C","interesting",false,"2"))
        bookList.add(Book("Book D","very good",false,"3"))

        bookList.forEach {
            listLayout.addView(buildItemView(it))
        }

        btn_add_new_book.setOnClickListener {
            var addNewBookIntent= Intent(this,EditBookActivity::class.java)
            addNewBookIntent.putExtra("NEW_BOOK_ID",listLayout.childCount)
            startActivity(addNewBookIntent)
        }


    }
   fun buildItemView(book: Book):TextView{
       val view= TextView (this)
       view.text=book.title
       view.setOnClickListener {
           var editBookIntent=Intent(this,EditBookActivity::class.java)
           editBookIntent.putExtra("CSV_BOOK",book.toCsvString())
       }
       return view

   }
}
