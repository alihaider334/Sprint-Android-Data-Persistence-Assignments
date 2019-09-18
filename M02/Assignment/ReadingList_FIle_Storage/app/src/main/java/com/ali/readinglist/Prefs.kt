package com.ali.readinglist

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color

// A Shared Preferences helper class
class Prefs(context: Context) {
    companion object {
        private const val BOOK_PREFERENCES = "BookPreferences"

        //KEYS for Shared Preferences can be defined as Constants here
        private const val ID_LIST_KEY = "id_list"
        private const val BOOK_ITEM_KEY_PREFIX = "book_"
        private const val NEXT_ID_KEY = "next_id"

    }

    val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(BOOK_PREFERENCES, Context.MODE_PRIVATE)


    //Each Journal Entry will be its own book in shared preferences
    // create a new book
    fun createEntry(book: Book) {
        // read list of book ids
        val ids = getListOfIds()

        if (book.id.toString().toInt() != Book.INVALID_ID && !ids.contains(book.id)) {
            // new book
            val editor = sharedPrefs.edit()

            var nextId = sharedPrefs.getInt(NEXT_ID_KEY, 0)
            book.id = nextId.toString()
            // store updated next id
            editor.putInt(NEXT_ID_KEY, ++nextId)

            // add id to list of ids

            ids.add(Integer.toString(book.id.toString().toInt()))
            // store updated id list
            val newIdList = StringBuilder()
            for (id in ids) {
                newIdList.append(id).append(",")
            }

            editor.putString(ID_LIST_KEY, newIdList.toString())

            // store new book
            editor.putString(BOOK_ITEM_KEY_PREFIX + book.id, book.toCsvString())
            editor.apply()
        } else {
            updateEntry(book)
        }
    }
    private fun getListOfIds(): java.util.ArrayList<String> {
        val idList = sharedPrefs.getString(ID_LIST_KEY, "")
        val oldList = idList!!.split(",")

        val ids = ArrayList<String>(oldList.size)
        if (idList.isNotBlank()) {
            ids.addAll(oldList)
        }
        return ids
    }

    // read an existing entry
    private fun readEntry(id: Int): Book? {
        val entryCsv = sharedPrefs.getString(BOOK_ITEM_KEY_PREFIX + id, "invalid")!!
        return if (entryCsv != "invalid") {
            Book(entryCsv)
        } else {
            null
        }
    }

    // read all entries
    fun readAllEntries(): MutableList<Book> {
        // read list of entry ids
        val listOfIds = getListOfIds()

        // step through that list and read each entry
        val entryList = java.util.ArrayList<Book>()
        for (id in listOfIds) {

            if (id.isNotBlank()) {
                readEntry(id.toInt())?.let {
                    entryList.add(it)
                }
            }
        }
        return entryList
    }

    //  Update an entry - use CSV technique to "serialize" a Journal Entry
    // edit an existing entry
    fun updateEntry(entry: Book) {
        val editor = sharedPrefs.edit()
        editor.putString(BOOK_ITEM_KEY_PREFIX + entry.id, entry.toCsvString())
        editor.apply()
    }
    fun removePrefs() {
        sharedPrefs.edit().clear().commit()
    }
}