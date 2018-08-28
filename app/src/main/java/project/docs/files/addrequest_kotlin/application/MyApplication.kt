package project.docs.files.addrequest_kotlin.application

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)

    }


    companion object {
        var appContext: Context? = null
            private set
    }


}