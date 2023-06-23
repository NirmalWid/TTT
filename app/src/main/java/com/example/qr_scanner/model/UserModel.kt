package com.example.qr_scanner.model

class UserModel(

    val userid : String? = "",
    val Fname: String? = "",
    val Lname: String? = "",
    val Email: String? = "",
    val Id: String? = "",
    val Uname: String? ="",
    val Pass: String? ="",
    val Cpass: String? ="",

){

    constructor() : this("", "", "", "", "", "", "", "")


    fun getuserid(): String? {
        return userid
    }

    fun getfname(): String? {
        return Fname
    }

    fun getlname(): String? {
        return Lname
    }

    fun getemail(): String? {
        return Email
    }

    fun getid(): String? {
        return Id
    }

    fun getuname(): String? {
        return Uname
    }

    fun getpass(): String? {
        return Pass
    }

    fun getcpass(): String? {
        return Cpass
    }
}