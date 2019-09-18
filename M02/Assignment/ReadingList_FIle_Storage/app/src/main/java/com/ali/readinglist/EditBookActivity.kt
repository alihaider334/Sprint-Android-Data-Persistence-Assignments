package com.ali.readinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edit_book.*

class EditBookActivity : AppCompatActivity() {

   var bookID:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        if(intent.getStringExtra("CSV_BOOK")!=null) {
            var csvBook = intent.getStringExtra("CSV_BOOK")
            var book=Book(csvBook.toString())
            editBookTitle.setText(book.title)
            editBookReasonToRead.setText(book.reasonToRead)
            CheckBoxHasBeenRead.isChecked=book.hasBeenRead
            bookID=book.id

        }
        else
            bookID=intent.getIntExtra("NEW_BOOK_ID",1).toString()


    }
    fun returnData(){
        var book=Book(editBookTitle.text.toString(),editBookReasonToRead.text.toString(),CheckBoxHasBeenRead.isChecked,bookID)
        val intent = Intent()
        intent.putExtra("RETURNED_BOOK", book.toCsvString())
        setResult(Activity.RESULT_OK, intent)
        finish()

    }
    override fun onBackPressed() {
        returnData()
    }
}
