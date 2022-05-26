package com.example.plants

import android.widget.ImageView

class Projectile(var imageView: ImageView, var id: Int, var x: Int, var y: Int) {
    var counter = 0
    var playingHitAnimation = false
    var animationHitCounter = 0
    var imageViewChanged = false

    fun update(time: Int) {
        counter += time
        if (imageViewChanged == true) {
            imageView.translationX += 10f
        }
    }
}