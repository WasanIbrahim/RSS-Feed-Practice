package com.example.rssfeedpractice

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.net.URL

class XmlParser {
    private val articles = ArrayList<Article>()
    private var text = ""

    private var title = ""

    fun parse(): ArrayList<Article>{
        try {

            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            val url = URL("https://stackoverflow.com/feeds")
            parser.setInput(url.openStream(),null)
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT){

                var tagName = parser.name

                when(eventType){
                    XmlPullParser.TEXT -> text = parser.text
                    XmlPullParser.END_TAG -> when{
                        tagName.equals("title",true) -> {
                            title = text
                            articles.add(Article(title))
                        }
                    }
                    else -> {}
                }
                eventType = parser.next()
            }
        }catch (e: XmlPullParserException){
            e.printStackTrace()

        }catch (e: IOException){
            e.printStackTrace()
        }
        return articles
    }
}