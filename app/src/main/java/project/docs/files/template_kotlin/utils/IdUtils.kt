package project.docs.files.template_kotlin.utils

import java.util.Random

object IdUtils {

    fun newID(): Int {

        val min = 100000000
        val max = 200000000

        val random = Random()

        return random.nextInt(max - min + 1) + min

    }

}