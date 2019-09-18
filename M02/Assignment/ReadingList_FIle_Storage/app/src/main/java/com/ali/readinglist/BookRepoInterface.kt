package com.ali.readinglist

interface BookRepoInterface {

    fun createBook(book: Book)
    fun readAllBooks(): MutableList<Book>
    fun updateBook(book: Book)

}