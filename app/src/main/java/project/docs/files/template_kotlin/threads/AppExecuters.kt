package project.docs.files.template_kotlin.threads

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class AppExecuters{

    private val LOCK = Any()
    private var sInstance: AppExecuters? = null
    private lateinit var diskIO: Executor
    private lateinit var mainThread: Executor
    private lateinit var networkIO: Executor


    /*
    private fun AppExecuters(diskIO: Executor, networkIO: Executor, mainThread: Executor): AppExecuters? {
        this.diskIO = diskIO
        this.networkIO = networkIO
        this.mainThread = mainThread

    }


    fun getInstance(): AppExecuters? {
        if (sInstance == null) {
            synchronized(LOCK) {
                sInstance = AppExecuters(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        MainThreadExecutor())
            }
        }
        return sInstance
    }


    fun diskIO(): Executor {
        return diskIO
    }


    fun mainThread(): Executor {
        return mainThread
    }


    fun networkIO(): Executor {
        return networkIO
    }


    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
    */


}