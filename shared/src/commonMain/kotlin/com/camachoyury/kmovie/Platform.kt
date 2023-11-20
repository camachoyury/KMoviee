package com.camachoyury.kmovie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform