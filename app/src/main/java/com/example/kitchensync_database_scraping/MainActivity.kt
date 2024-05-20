package com.example.kitchensync_database_scraping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequestBlocking
import com.fleeksoft.ksoup.select.Elements
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.blog).setOnClickListener {
            scrapeBlog()
        }
        findViewById<Button>(R.id.pantry).setOnClickListener {
            scrapePantry()
        }
        findViewById<Button>(R.id.recipe).setOnClickListener {
            scrapeRecipes()
        }
    }

    private fun scrapeBlog() {

    }

    private fun scrapePantry() {

        val pantry = db.collection("appdata").document("appPantryItems")
        println("database ref")

        val webList = arrayOf("fruits", "vegetables", "meat", "seafood", "dairy-products",
            "nuts-and-oilseeds", "sugar-and-sugar-products", "cereals-and-pulses",
            "spices-and-herbs", "other-ingredients")

        var webName = webList[0]
        var pageNum = 1
        println(webName)
        while (true) {
            val doc =
                Ksoup.parseGetRequestBlocking(url = "https://food.ndtv.com/ingredient/loadmore/$webName/$pageNum/18")
            if (!doc.hasText())
                break

            println("scraping")
            val foodUrlNames: Elements = doc.select(".IngrLst-Ar_img")
            val foodUrlPics: Elements = doc.select(".lz_img.IngrLst-Ar_img-full")
            for (i in 0..<foodUrlNames.size) {
                var name = foodUrlNames[i]
                var url = foodUrlPics[i]
                val foodName = name.attr("title")
                val foodURL = url.attr("src")

                println(foodName)
                pantry.set(RecipeFoodItem(foodName, foodURL))
            }
            //println("" + foodUrlNames.size + " " + foodUrlPics.size)
            //println(ingList)
            pageNum++
        }
    }

    private fun scrapeRecipes() {

    }
}

data class RecipeFoodItem(var foodName: String="", var imageUrl: String="")