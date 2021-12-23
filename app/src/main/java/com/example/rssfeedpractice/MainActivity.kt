package com.example.rssfeedpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var mainRV:RecyclerView
    lateinit var myAdapter : RecyclerViewAdapter
    lateinit var article: List<Article>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        article = listOf()

        //setting the recyclerview
        mainRV = findViewById(R.id.mainRV)
        myAdapter = RecyclerViewAdapter(article)
        mainRV.adapter = myAdapter
        mainRV .layoutManager = LinearLayoutManager(this)

        parseRSS()
    }

    private fun parseRSS() {
        CoroutineScope(IO).launch {

            val data = async {
                val parser = XmlParser()
                parser.parse()
            }.await()
            try {
                withContext(Main){
                    myAdapter.update(data)
                }
            }catch (e: java.lang.Exception) {

                Log.d("MAIN", "Unable to gat data")
            }
        }
    }
}