package project.docs.files.addrequest_kotlin.threads

import android.arch.persistence.room.Room
import android.os.Handler
import android.os.Looper
import project.docs.files.addrequest_kotlin.application.MyApplication
import project.docs.files.addrequest_kotlin.data.AppDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors


abstract class AppExecuters{











    companion object {


        private var sInstance: AppExecuters? = null
        private val LOCK = Any()
        private lateinit var diskIO: Executor
        private lateinit var mainThread: Executor
        private lateinit var networkIO: Executor


        private fun AppExecuters(diskIO: Executor, networkIO: Executor, mainThread: Executor): AppExecuters? {
            Companion.diskIO = diskIO
            Companion.networkIO = networkIO
            Companion.mainThread = mainThread

            return AppExecuters(diskIO, networkIO, mainThread)

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


    }





    /*
    fun getInstance(): AppExecuters =
            sInstance ?: synchronized(this) {
                sInstance = createExecuter(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        MainThreadExecutor())
            } as AppExecuters
            */








}