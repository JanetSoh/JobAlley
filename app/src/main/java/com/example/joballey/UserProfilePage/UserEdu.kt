package com.example.joballey.UserProfilePage

class UserEdu {
    var uSchool:String?=null
    var subject:String ? = null
    var schStart : String?=null
    constructor(){}

    constructor(
        uSchool:String?,
        subject:String?,
        schStart:String?
    ){
        this.uSchool = uSchool
        this.subject = subject
        this.schStart =schStart
    }

}