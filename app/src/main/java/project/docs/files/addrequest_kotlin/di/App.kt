package project.docs.files.addrequest_kotlin.di

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import project.docs.files.addrequest_kotlin.di.component.DaggerApplicationComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
                .builder()
                .create(this)
                .build()
    }

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}