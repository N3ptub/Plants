package com.example.plants

import android.util.Log
import android.widget.ImageView

class Zombie(var imageView: ImageView) {
    var counter = 0
    var health = 100
    var speed = (-5..5).random().toFloat()
    var isEating = false

    fun update(time: Int) {
        counter += 1
        if ((counter%2 == 0) && (isEating == false)) {
            move()
        }
        if (counter%20 == 0) {
            //Log.d("ZombieClass", "Zombie Position: ${imageView.x} and ${imageView.y}")
        }
    }

    fun eat() {
        isEating = true
    }

    fun move() {
        imageView.translationX -= (1.2f + (speed/10f))
    }
}