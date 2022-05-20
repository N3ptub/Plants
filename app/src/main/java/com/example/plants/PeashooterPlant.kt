package com.example.plants

import android.widget.ImageView

class PeashooterPlant(var imageView: ImageView) {
    var counter = 0
    var health = 100
    var playingShootAnimation = false
    var playingIdleAnimation = true
    var animationShootCounter = 0
    var animationIdleCounter = 0

    init {
        imageView.setImageResource(R.drawable.peashooter)
    }

    fun update(time : Int) {
        counter += time
        if (counter%2000 == 0) {
            shoot()
        }
        if (counter%120 == 0) {
            animate()
        }
        if (counter%2000 == 0 && counter%120 == 0) {
            counter = 0
        }
    }

    fun shoot() {
        playingShootAnimation = true
        playingIdleAnimation = false
    }

    fun animate() {
        if (playingIdleAnimation == true) {
            animationIdleCounter += 1
            if (animationIdleCounter == 1) {
                imageView.setImageResource(R.drawable.peashooter1)
            } else if (animationIdleCounter == 2) {
                imageView.setImageResource(R.drawable.peashooter2)
            } else if (animationIdleCounter == 3) {
                imageView.setImageResource(R.drawable.peashooter3)
            } else if (animationIdleCounter == 4) {
                imageView.setImageResource(R.drawable.peashooter4)
            } else if (animationIdleCounter == 5) {
                imageView.setImageResource(R.drawable.peashooter5)
            } else if (animationIdleCounter == 6) {
                imageView.setImageResource(R.drawable.peashooter6)
            } else if (animationIdleCounter == 7) {
                imageView.setImageResource(R.drawable.peashooter7)
            } else if (animationIdleCounter == 8) {
                imageView.setImageResource(R.drawable.peashooter8)
                animationIdleCounter = 0
                animationShootCounter = 0
            }
        }
        else if (playingShootAnimation == true) {
            animationShootCounter += 1
            if (animationShootCounter == 1) {
                imageView.setImageResource(R.drawable.peashooter_shooting1)
            } else if (animationShootCounter == 2) {
                imageView.setImageResource(R.drawable.peashooter_shooting2)
            } else if (animationShootCounter == 3) {
                imageView.setImageResource(R.drawable.peashooter_shooting3)
                animationShootCounter = 0
                animationIdleCounter = 0
                playingShootAnimation = false
                playingIdleAnimation = true
            }

        }
    }
}