package com.example.kitchensync_database_scraping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blogButton = findViewById<Button>(R.id.blog)
        val pantryButton = findViewById<Button>(R.id.pantry)
        val recipeButton = findViewById<Button>(R.id.recipe)

        blogButton.setOnClickListener {

        }
    }
}