package com.example.plants

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract.Helpers.update
import android.text.TextUtils.indexOf
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.plants.databinding.ActivityGameBinding
import kotlin.properties.Delegates


open class GameActivity : AppCompatActivity() {
    companion object {
        var sun by Delegates.notNull<Int>()
        var projectiles = mutableListOf<Projectile>()
        var gameContext = this
    }

    init {
        sun = 75
    }
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sunDisplayValue = binding.sunDisplayValue
        val sunDisplayValueIcon = binding.sunDisplayValueIcon
        val gameOverText = binding.GameOverText
        gameOverText.text = ""
        val waveText = binding.waveNumberText
        sunDisplayValue.text = sun.toString()

        var purchasePlantSelected = 0
        var gameCounter = 0
        var zombieCounter = 0
        var wave = 0
        var previousProjectile = Projectile(sunDisplayValueIcon, 0, 0, 0)

        val row1 = arrayOf(binding.plantSlot1, binding.plantSlot6, binding.plantSlot11, binding.plantSlot16, binding.plantSlot21, binding.plantSlot26, binding.plantSlot31, binding.plantSlot36, binding.plantSlot41)
        val row2 = arrayOf(binding.plantSlot2, binding.plantSlot7, binding.plantSlot12, binding.plantSlot17, binding.plantSlot22, binding.plantSlot27, binding.plantSlot32, binding.plantSlot37, binding.plantSlot42)
        val row3 = arrayOf(binding.plantSlot3, binding.plantSlot8, binding.plantSlot13, binding.plantSlot18, binding.plantSlot23, binding.plantSlot28, binding.plantSlot33, binding.plantSlot38, binding.plantSlot43)
        val row4 = arrayOf(binding.plantSlot4, binding.plantSlot9, binding.plantSlot14, binding.plantSlot19, binding.plantSlot24, binding.plantSlot29, binding.plantSlot34, binding.plantSlot39, binding.plantSlot44)
        val row5 = arrayOf(binding.plantSlot5, binding.plantSlot10, binding.plantSlot15, binding.plantSlot20, binding.plantSlot25, binding.plantSlot30, binding.plantSlot35, binding.plantSlot40, binding.plantSlot45)
        val plantSlots = arrayOf(row1, row2, row3, row4, row5)

        var zombieSpawners = arrayOf(binding.zombieSpawner1, binding.zombieSpawner2, binding.zombieSpawner3, binding.zombieSpawner4, binding.zombieSpawner5)

        val backgrounds = binding.background
        backgrounds.setImageResource(R.drawable.background)

        val purchaseSunflowerButton = binding.purchaseSunflowerButton
        val purchasePeashooterButton = binding.purchasePeashooterButton

        purchaseSunflowerButton.setOnClickListener {
            purchasePlantSelected = 1
        }

        purchasePeashooterButton.setOnClickListener {
            purchasePlantSelected = 2
        }

        var plants = mutableListOf<Plant>()
        var zombies = mutableListOf<Zombie>()

        fun clicked(plant: ImageView, x: Int, lane: Int) {
            if (purchasePlantSelected != 0) {
                var duplicate = true
                for (i in plants) {
                    if (i.imageView == plant) {
                        duplicate = false
                        break
                    }
                }
                if (duplicate) {
                    if ((purchasePlantSelected == 1) && (sun >= 50)) {
                        sun -= 50
                        val newPlant = Plant(purchasePlantSelected, plant, x, (lane + 1))
                        plants.add(newPlant)
                    }
                    else if ((purchasePlantSelected == 2) && (sun >= 100)) {
                        sun -= 100
                        val newPlant = Plant(purchasePlantSelected, plant, x, (lane + 1))
                        plants.add(newPlant)
                    }
                    purchasePlantSelected = 0
                }
            }
        }

        for (i in 0..4) {
            for (j in 0..8) {
                plantSlots[i][j].setOnClickListener {
                    clicked(plantSlots[i][j], j, i)
                }
            }
        }

        fun addProjectile(projectile: Projectile) {
            Log.d("GameActivity", "ADDED PROJECTILE")
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(50, 50)
            imageView.translationX = (projectile.x * 200f) + 700f
            imageView.translationY = (projectile.y * 235f) + 15f

            imageView.setImageResource(R.drawable.pea)

            binding.GameLayout.addView(imageView)
            projectile.imageView = imageView
            projectile.imageViewChanged = true
        }

        fun update(time: Int) {
            for (plant in plants) {
                plant.update(time)
            }
            for (zombie in zombies) {
                zombie.update(time)
            }
            for (projectile in projectiles) {
                projectile.update(time)
            }
            sunDisplayValue.text = sun.toString()
            gameCounter += 1

            if (projectiles.indexOf(previousProjectile) != projectiles.lastIndex) {
                if (projectiles.indexOf(previousProjectile) != -1) {
                    for (index in projectiles.indexOf(previousProjectile)..(projectiles.lastIndex-1)) {
                        addProjectile(projectiles[index])
                    }
                }
                previousProjectile = projectiles.last()
            }
        }

        fun addZombie(lane : Int) {
            if ((lane >= 1) && (lane <= 5)) {
                val imageView = ImageView(this)
                imageView.layoutParams = LinearLayout.LayoutParams(200, 300)
                imageView.translationX = (2700 + (-25..25).random()).toFloat()
                imageView.translationY = (((lane - 1) * 250) + 50 + (-15..15).random()).toFloat()

                imageView.setImageResource(R.drawable.zombie)

                binding.GameLayout.addView(imageView)

                zombieCounter += 1
                val newZombie = Zombie(imageView, lane, zombieCounter, ((wave*10) + 100))
                zombies.add(newZombie)
            }
        }

        @SuppressLint("SetTextI18n")
        fun checkCollisions() {
            for (zombie in zombies) {
                if (zombie.imageView.x <= 0) {
                    gameOverText.text = "Game Over!"
                }
            }
            for (plant in plants) {
                //Log.d("GameActivity", "Top Left Plant Position: ${plant.imageView.x} and ${plant.imageView.y}")
                for (zombie in zombies) {
                    if (zombie.lane == plant.lane) {
                        if ((zombie.imageView.x <= plant.imageView.x + 100) && (zombie.imageView.x >= plant.imageView.x - 100)) {
                            if (!zombie.isEating) {
                                if (plant.health > 0) {
                                   zombie.eat(plant)
                                }
                                else {
                                    plant.imageView.setImageResource(android.R.color.transparent)
                                    plants = plants.filter { (it != plant)} as MutableList<Plant>
                                }
                            }
                        }
                    }
                }
            }
            for (i in 1..5) {
                var zombiesInLane = false
                for (zombie in zombies) {
                    if (zombie.lane == i) {
                        zombiesInLane = true
                    }
                }
                if (zombiesInLane == false) {
                    for (plant in plants) {
                        if (plant.lane == i && plant.plantID != 1) {
                            plant.peashooter?.areZombies = false ?: true
                        }
                    }
                }
                else {
                    for (plant in plants) {
                        if (plant.lane == i) {
                            plant.peashooter?.areZombies = true ?: true
                        }
                    }
                }
            }
            for (projectile in projectiles) {
                for (zombie in zombies) {
                    if (zombie.lane == projectile.y) {
                        if (zombie.imageView.x <= projectile.imageView.x) {
                            projectile.imageView.setImageResource(android.R.color.transparent)
                            projectiles = projectiles.filter { (it != projectile)} as MutableList<Projectile>
                            zombie.health -= 10
                            if (zombie.health <= 0) {
                                zombie.imageView.setImageResource(android.R.color.transparent)
                                zombies = zombies.filter { (it != zombie)} as MutableList<Zombie>
                            }
                        }
                        else if (projectile.imageView.x >= 3000) {
                            projectile.imageView.setImageResource(android.R.color.transparent)
                            projectiles = projectiles.filter { (it != projectile)} as MutableList<Projectile>
                        }
                    }
                }
            }
        }

        fun gameLoop() {

            update(40)

            if (gameCounter == 500) {
                wave = 1
                waveText.text = "Wave 1"
            }

            if (gameCounter == 1500) {
                wave = 2
                waveText.text = "Wave 2"
            }

            if (gameCounter == 2500) {
                wave = 3
                waveText.text = "Wave 3"
            }
            if (gameCounter == 3000) {
                wave = 4
                waveText.text = "Wave 4"
            }
            if (gameCounter == 4000) {
                wave = 15
                waveText.text = "Wave 5"
            }

            if (wave == 1) {
                if (gameCounter%500 == 0) {
                    addZombie((1..5).random())
                }
            }
            if (wave == 2) {
                if (gameCounter%250 == 0) {
                    addZombie((1..5).random())
                }
            }
            if (wave == 3) {
                if (gameCounter%125 == 0) {
                    addZombie((1..5).random())
                }
            }
            if (wave == 4) {
                if (gameCounter%50 == 0) {
                    addZombie((1..5).random())
                }
            }
            if (wave == 15) {
                if (gameCounter%50 == 0) {
                    addZombie((1..5).random())
                }
            }

            if (gameCounter%10 == 0) {
                checkCollisions()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                gameLoop()
            }, 40)
        }
        gameLoop()
    }

}
