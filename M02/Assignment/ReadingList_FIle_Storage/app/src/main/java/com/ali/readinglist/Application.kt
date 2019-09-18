package com.ali.readinglist
import android.app.Application

val repo: BookRepoInterface by lazy {

    App.repo!!

}

class App : Application() {

    companion object {
        var repo: BookRepoInterface? = null
    }
    override fun onCreate() {
        super.onCreate()
        repo = BookFileRepo(applicationContext)

    }
}