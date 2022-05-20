package com.example.plants

import android.widget.ImageView

class SunflowerPlant(var imageView: ImageView) {
    var SUN_VALUE = 25
    var counter = 0
    var health = 100
    var playingDropSunAnimation = false
    var playingIdleAnimation = true
    var animationDropSunCounter = 0
    var animationIdleCounter = 0

    init {
        imageView.setImageResource(R.drawable.sunflower)
    }

    fun update(time : Int) {
        counter += time
        if (counter%10000 == 0) {
            dropSun()
        }
        if (counter%120 == 0) {
            animate()
        }
        if (counter%10000 == 0 && counter%120 == 0) {
            counter = 0
        }
    }

    fun dropSun() {
        GameActivity.sun += SUN_VALUE
        playingDropSunAnimation = true
        playingIdleAnimation = false
    }

    fun animate() {
        if (playingIdleAnimation == true) {
            animationIdleCounter += 1
            if (animationIdleCounter == 1) {
                imageView.setImageResource(R.drawable.sunflower1)
            } else if (animationIdleCounter == 2) {
                imageView.setImageResource(R.drawable.sunflower2)
            } else if (animationIdleCounter == 3) {
                imageView.setImageResource(R.drawable.sunflower3)
            } else if (animationIdleCounter == 4) {
                imageView.setImageResource(R.drawable.sunflower4)
            } else if (animationIdleCounter == 5) {
                imageView.setImageResource(R.drawable.sunflower5)
            } else if (animationIdleCounter == 6) {
                imageView.setImageResource(R.drawable.sunflower6)
            } else if (animationIdleCounter == 7) {
                imageView.setImageResource(R.drawable.sunflower7)
            } else if (animationIdleCounter == 8) {
                imageView.setImageResource(R.drawable.sunflower8)
                animationIdleCounter = 0
                animationDropSunCounter = 0
            }
        }
        else if (playingDropSunAnimation == true) {
            animationDropSunCounter += 1
            if (animationDropSunCounter == 1) {
                imageView.setImageResource(R.drawable.sunflower_dropping1)
                animationDropSunCounter = 0
                animationIdleCounter = 0
                playingDropSunAnimation = false
                playingIdleAnimation = true
            }

        }
    }
}