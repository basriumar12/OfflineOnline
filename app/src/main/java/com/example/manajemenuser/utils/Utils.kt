package com.example.manajemenuser.utils

class Utils {

    fun isValidEmailAddress(email: String): Boolean {
        val stricterFilter = true
        val stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
        val laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*"
        val emailRegex = if (stricterFilter) stricterFilterString else laxString
        val p = java.util.regex.Pattern.compile(emailRegex)
        val m = p.matcher(email)
        return m.matches()
    }



}