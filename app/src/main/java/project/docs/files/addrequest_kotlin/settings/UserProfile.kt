package project.docs.files.addrequest_kotlin.settings

import android.preference.PreferenceManager
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.application.MyApplication
import project.docs.files.addrequest_kotlin.utils.NameUtils


object UserProfileSettings {


    private val PREFERENCES_KEY_USERID = "preferences_key_userId"
    private val PREFERENCES_KEY_USERNAME = "preferences_key_userName"
    private val PREFERENCES_KEY_FIRSTNAME = "preferences_key_firstName"
    private val PREFERENCES_KEY_LASTNAME = "preferences_key_lastName"
    private val PREFERENCES_KEY_USER_PHOTO_URL = "preferences_key_userPhotoUrl"


    fun getUserID(): String {

        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        return prefs.getString(PREFERENCES_KEY_USERID, MyApplication.appContext?.getString(R.string.default_userid))

    }


    fun getUsername(): String {

        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        return prefs.getString(PREFERENCES_KEY_USERNAME, MyApplication.appContext?.getString(R.string.default_username))

    }

    fun getFirstname(): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        return prefs.getString(PREFERENCES_KEY_FIRSTNAME, MyApplication.appContext?.getString(R.string.default_username))
    }


    fun getLastname(): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        return prefs.getString(PREFERENCES_KEY_LASTNAME, MyApplication.appContext?.getString(R.string.default_username))
    }


    fun getUserPhotoURL(): String {

        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        return prefs.getString(PREFERENCES_KEY_USER_PHOTO_URL, MyApplication.appContext?.getString(R.string.default_userphotourl))

    }


    fun setUserProfileAtLogin(userId: String, userName: String?, userPhotoUrl: String) {

        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        val editor = prefs.edit()
        editor.putString(PREFERENCES_KEY_USERID, userId)
        editor.putString(PREFERENCES_KEY_USERNAME, userName)
        editor.putString(PREFERENCES_KEY_USER_PHOTO_URL, userPhotoUrl)
        editor.putString(PREFERENCES_KEY_FIRSTNAME, NameUtils.getFirstName(userName!!))
        editor.putString(PREFERENCES_KEY_LASTNAME, NameUtils.getLastName(userName))

        editor.apply()

    }


    fun setUserProfileAtLogout() {

        val prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.appContext)
        val editor = prefs.edit()
        editor.putString(PREFERENCES_KEY_USERID, MyApplication.appContext?.getString(R.string.default_userid))
        editor.putString(PREFERENCES_KEY_USERNAME, MyApplication.appContext?.getString(R.string.default_username))
        editor.putString(PREFERENCES_KEY_USER_PHOTO_URL, MyApplication.appContext?.getString(R.string.default_userphotourl))
        editor.putString(PREFERENCES_KEY_FIRSTNAME, MyApplication.appContext?.getString(R.string.default_username))
        editor.putString(PREFERENCES_KEY_LASTNAME, MyApplication.appContext?.getString(R.string.default_username))

        editor.apply()

    }

}