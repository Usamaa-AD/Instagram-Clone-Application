package com.example.instagramapppractice.Modals

class User {
     var name: String? = null
     var pass: String? = null
     var email: String? = null
     var profileUrl: String? = null
    constructor()
    constructor(name: String?, pass: String?, email: String?) {
        this.name = name
        this.pass = pass
        this.email = email
    }
    constructor(name: String?, pass: String?, email: String?, profileUrl: String?) {
        this.name = name
        this.pass = pass
        this.email = email
        this.profileUrl = profileUrl
    }
}