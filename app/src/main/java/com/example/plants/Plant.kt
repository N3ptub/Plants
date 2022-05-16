package com.example.plants

import android.widget.ImageView

class Plant(var plantID: Int, var imageView: ImageView, var posX: Int, var posY: Int) {

    var sunflower: SunflowerPlant? = null
    var peashooter: SunflowerPlant? = null

    init {
        if (plantID == 1) {
            sunflower = SunflowerPlant(imageView)
        }
        else if (plantID == 2) {
            peashooter = SunflowerPlant(imageView)
        }
    }

    fun update(time: Int) {
        if (plantID == 1) {
            sunflower?.update(time)
        }
        else if (plantID == 2) {
            peashooter?.update(time)
        }
    }
}