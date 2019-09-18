package com.ali.readinglist
import android.app.Application

val prefs: Prefs by lazy {
    App.prefs!!
}
class App : Application() {

    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()

        prefs = Prefs(applicationContext)

    }
}