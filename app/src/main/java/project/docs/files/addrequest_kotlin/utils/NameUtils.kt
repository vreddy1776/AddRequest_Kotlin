package project.docs.files.addrequest_kotlin.utils


/**
 * Name Utils
 *
 * Get first, middle, and last names from username
 *
 * @author Vijay T. Reddy
 * @version 1.0.0
 */
object NameUtils {


    fun getFirstName(fullname: String): String {

        var firstname = ""
        var middlename = ""
        var lastname = ""

        val start = fullname.indexOf(' ')
        val end = fullname.lastIndexOf(' ')

        if (start >= 0) {
            firstname = fullname.substring(0, start)
            if (end > start)
                middlename = fullname.substring(start + 1, end)
            lastname = fullname.substring(end + 1, fullname.length)
        }

        return firstname

    }

    fun getMiddleName(fullname: String): String {

        var firstname = ""
        var middlename = ""
        var lastname = ""

        val start = fullname.indexOf(' ')
        val end = fullname.lastIndexOf(' ')

        if (start >= 0) {
            firstname = fullname.substring(0, start)
            if (end > start)
                middlename = fullname.substring(start + 1, end)
            lastname = fullname.substring(end + 1, fullname.length)
        }

        return middlename

    }

    fun getLastName(fullname: String): String {

        var firstname = ""
        var middlename = ""
        var lastname = ""

        val start = fullname.indexOf(' ')
        val end = fullname.lastIndexOf(' ')

        if (start >= 0) {
            firstname = fullname.substring(0, start)
            if (end > start)
                middlename = fullname.substring(start + 1, end)
            lastname = fullname.substring(end + 1, fullname.length)
        }

        return lastname

    }

}