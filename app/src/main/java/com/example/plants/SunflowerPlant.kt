package com.example.plants

class SunflowerPlant {
    var SUN_VALUE = 25
    var sunCounter = 0
    var shootCounter = 0

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