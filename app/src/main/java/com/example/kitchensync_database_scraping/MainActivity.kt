package com.example.kitchensync_database_scraping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequestBlocking
import com.fleeksoft.ksoup.select.Elements
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore

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

        val pantryRef = db.collection("appdata").document("appPantryItems")

        val webList = arrayOf("fruits", "vegetables", "meat", "seafood", "dairy-products",
            "nuts-and-oilseeds", "sugar-and-sugar-products", "cereals-and-pulses",
            "spices-and-herbs", "other-ingredients")

        var pageNum = 1
        webList.forEach {
            while (true) {
                val doc =
                    Ksoup.parseGetRequestBlocking(url = "https://food.ndtv.com/ingredient/loadmore/$it/$pageNum/18")
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
                    pantryRef.collection(it).document(foodName)
                        .set(hashMapOf("imageURL" to foodURL), SetOptions.merge())
                }
                //println("" + foodUrlNames.size + " " + foodUrlPics.size)
                //println(ingList)
                pageNum++
            }
            Toast.makeText(applicationContext, "gathered $it info", Toast.LENGTH_SHORT)
            pageNum = 1
        }
    }

    private fun scrapeRecipes() {

    }
}

data class RecipeFoodItem(val foodName: String="", val imageUrl: String="")