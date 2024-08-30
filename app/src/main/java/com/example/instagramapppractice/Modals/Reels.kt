package com.example.instagramapppractice.Modals

class Reels {
    var videoUrl:String? = null
    var caption:String?=null
    var profileUrl:String? = null
    constructor()
    constructor(caption: String?, videoUrl: String?,profileUrl:String?) {
        this.caption = caption
        this.videoUrl = videoUrl
        this.profileUrl=profileUrl
    }

}