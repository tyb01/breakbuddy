package com.pawsup.cats

object CatAssetResolver {
    fun entranceVideo(catId: String) = "cats/$catId/entrance.mp4"
    fun idleVideo(catId: String)     = "cats/$catId/idle.mp4"
    fun outroVideo(catId: String)    = "cats/$catId/outro.mp4"
    fun poster(catId: String)        = "cats/$catId/poster.webp"
}
