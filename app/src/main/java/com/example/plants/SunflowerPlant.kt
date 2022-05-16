package com.example.plants

import android.widget.ImageView

class SunflowerPlant(var imageView: ImageView) {
    var SUN_VALUE = 25
    var sunCounter = 0
    var shootCounter = 0

    init {
        imageView.setImageResource(R.drawable.sunflower)
    }

    fun update(time : Int) {
        sunCounter += time
        shootCounter += time
        if (sunCounter/1000 >= 5) {
            dropSun()
        }

        if (shootCounter/1000 >= 2) {
            shoot()
        }
    }

    fun dropSun() {
        sunCounter = 0
        GameActivity.sun += SUN_VALUE
    }

    fun shoot() {

    }
}