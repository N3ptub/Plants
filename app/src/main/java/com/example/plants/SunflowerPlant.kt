package com.example.plants

import android.widget.ImageView

class SunflowerPlant(var imageView: ImageView) {
    var SUN_VALUE = 25
    var sunCounter = 0
    var animationCounter = 0

    init {
        imageView.setImageResource(R.drawable.sunflower)
    }

    fun update(time : Int) {
        sunCounter += time
        animationCounter += 1
        if (sunCounter/1000 >= 1) {
            dropSun()
        }
        //animate()
    }

    fun dropSun() {
        sunCounter = 0
        GameActivity.sun += SUN_VALUE
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
        }
    }
}