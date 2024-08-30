package com.example.instagramapppractice.Modals

class Posts {
    var postUrl : String? = null
    var caption:String? = null
    var time:String?=null
    var uid:String?=null
constructor()
    constructor(postUrl: String?, caption: String?) {
        this.postUrl = postUrl
        this.caption = caption
    }

    constructor( caption: String?, postUrl: String?,time: String?,uid:String?) {
        this.time = time
        this.caption = caption
        this.postUrl = postUrl
        this.uid=uid
    }



}