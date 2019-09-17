package com.ali.readinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_book.*

class EditBookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

       var newBookID=intent.getIntExtra("NEW_BOOK_ID",1)

        if(intent.getStringExtra("CSV_BOOK")!=null) {
            var csvBook = intent.getStringExtra("CSV_BOOK")
            var book=Book(csvBook.toString())
            editBookTitle.setText(book.title)
            editBookReasonToRead.setText(book.reasonToRead)
            CheckBoxHasBeenRead.isChecked=book.hasBeenRead

        }


    }
    fun returnData(csvBook:Book){
       // var book=Book(editBookTitle.text.toString(),editBookReasonToRead.text.toString(),CheckBoxHasBeenRead.isChecked,intent.getStringExtra("CSV_BOOK").)

    }
    override fun onBackPressed() {
        super.onBackPressed()


    }
}
