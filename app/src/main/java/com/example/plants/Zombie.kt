package com.example.plants

import android.util.Log
import android.widget.ImageView

class Zombie(var imageView: ImageView, var lane: Int, var priority: Int, var health: Int) {
    var counter = 0
    var playingWalkingAnimation = true
    var playingEatingAnimation = false
    var animationWalkingCounter = 0
    var animationEatingCounter = 0
    //var speed = (-5..5).random().toFloat()
    var isEating = false
    var target: Plant? = null

    fun update(time: Int) {
        counter += 1
        if ((counter%2 == 0) && (isEating == false)) {
            move()
        }
        if (counter%25 == 0) {
            //Log.d("ZombieClass", "Zombie Position: ${imageView.x} and ${imageView.y}")
            eat()
        }
        if (counter%4 == 0) {
            animate()
        }
    }

    fun eat(plant: Plant) {
        if (isEating == false) {
            isEating = true
            playingEatingAnimation = true
            playingWalkingAnimation = false
            target = plant
            //Log.d("ZombieClass", "SET EAT")
        }
    }

    fun eat() {
        //Log.d("ZombieClass", "TEST")
        if ((target?.health ?: 0) <= 0) {
            isEating = false
            target = null
        }
        else {
            target?.health = target?.health?.minus(10)!!
            //Log.d("ZombieClass", "Target Health: ${target!!.health}")
        }
    }

    fun move() {
        imageView.translationX -= (2.2f) //+ (speed/10f))
    }

    fun animate() {
        if (playingWalkingAnimation == true) {
            animationWalkingCounter += 1
            if (animationWalkingCounter == 1) {
                imageView.setImageResource(R.drawable.zombie_walking1)
            } else if (animationWalkingCounter == 2) {
                imageView.setImageResource(R.drawable.zombie_walking2)
            } else if (animationWalkingCounter == 3) {
                imageView.setImageResource(R.drawable.zombie_walking3)
            } else if (animationWalkingCounter == 4) {
                imageView.setImageResource(R.drawable.zombie_walking4)
                animationWalkingCounter = 0
                animationEatingCounter = 0
            }
        }
        else if (playingEatingAnimation == true) {
            animationEatingCounter += 1
            if (animationEatingCounter == 1) {
                imageView.setImageResource(R.drawable.zombie_eating1)
            } else if (animationEatingCounter == 2) {
                imageView.setImageResource(R.drawable.zombie_eating2)
            } else if (animationEatingCounter == 3) {
                imageView.setImageResource(R.drawable.zombie_eating3)
            } else if (animationEatingCounter == 4) {
                imageView.setImageResource(R.drawable.zombie_eating4)
            } else if (animationEatingCounter == 5) {
                imageView.setImageResource(R.drawable.zombie_eating5)
            } else if (animationEatingCounter == 6) {
                imageView.setImageResource(R.drawable.zombie_eating6)
            } else if (animationEatingCounter == 7) {
                imageView.setImageResource(R.drawable.zombie_eating7)
                animationEatingCounter = 0
                animationWalkingCounter = 0
                if (isEating) {
                    playingEatingAnimation = true
                    playingWalkingAnimation = false
                }
                else {
                    playingEatingAnimation = false
                    playingWalkingAnimation = true
                }

            }
        }
    }
}