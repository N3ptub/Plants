package com.example.plants

import android.widget.ImageView

class PeashooterPlant(var imageView: ImageView) {
    var shootCounter = 0
    var playingAnimation = false
    var animationCounter = 0

    init {
        imageView.setImageResource(R.drawable.peashooter)
    }

    fun update(time : Int) {
        shootCounter += time
        shootCounter += time
        if (shootCounter/1000 >= 1) {
            shoot()
        }
        if (playingAnimation == true) {
            animationCounter += 1
            animate()
        }
    }

    fun shoot() {
        shootCounter = 0
        //GameActivity.sun += SUN_VALUE
        playingAnimation = true
    }

    fun animate() {
        if (animationCounter == 1) {
            imageView.setImageResource(R.drawable.sunflower)
        }
        else if (animationCounter == 2) {
            imageView.setImageResource(R.drawable.sun)
        }
        else if (animationCounter == 3) {
            imageView.setImageResource(R.drawable.peashooter)
            animationCounter = 0
            playingAnimation = false
        }
    }
}