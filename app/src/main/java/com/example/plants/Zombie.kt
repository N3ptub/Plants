package com.example.plants

import android.util.Log
import android.widget.ImageView

class Zombie(var imageView: ImageView, var lane: Int) {
    var counter = 0
    var health = 100
    var speed = (-5..5).random().toFloat()
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
    }

    fun eat(plant: Plant) {
        if (isEating == false) {
            isEating = true
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
        imageView.translationX -= (5.2f + (speed/10f))
    }
}