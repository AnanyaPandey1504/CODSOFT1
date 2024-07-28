package com.example

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    var listQuotes= mutableListOf(
        R.string.quote_string1,
        R.string.quote_string2,
        R.string.quote_string3,
        R.string.quote_string4,
        R.string.quote_string5,
        R.string.quote_string6,
        R.string.quote_string7,
        R.string.quote_string8,
        R.string.quote_string9,
        R.string.quote_string10
    )

    var quoteNumber=0
    var mainText=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quoteOnAppLoad()
        clickNewQuote()
        setupFavoriteCheckBox()

    }

    private fun clickNewQuote() {
        val fab_NewQuote:FloatingActionButton=findViewById(R.id.fab)
        fab_NewQuote.setOnClickListener {
            fab_NewQuote.isEnabled=false

            if(quoteNumber==listQuotes.size){
                quoteOnAppLoad()
            }
            else{
                typeText(getString(listQuotes[quoteNumber]))
                ++quoteNumber
            }

        }
    }

    private fun quoteOnAppLoad(){
        val fab_NewQuote:FloatingActionButton=findViewById(R.id.fab)
        fab_NewQuote.isEnabled=false
        quoteNumber=0
        listQuotes.shuffle()
        typeText(getString(listQuotes[quoteNumber]))
        ++quoteNumber
    }

    @SuppressLint("SuspiciousIndentation")
    private fun typeText(text:String){
        mainText=""
        val textDelay:Long = 50L

        val checkBox: CheckBox = findViewById(R.id.cbHeart)
        checkBox.setOnCheckedChangeListener(null)
        checkBox.isChecked = false
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            updateFavoriteStatus(isChecked)
        }


        GlobalScope.launch(Dispatchers.IO){
            val sb=StringBuilder()
            val updatedText=""

            for(i in text.indices ){
              mainText=sb.append(updatedText+text[i]).toString()
                Thread.sleep(textDelay)
            }
        }

    val handler = Handler()
    Log.d("Main", "Handler Started")
    val runnable = object : Runnable {
        override fun run() {
            val quoteTextView: TextView = findViewById(R.id.tv)
            quoteTextView.text = "$mainText|"
            handler.postDelayed(this, 10)

            if (text == mainText) {
                handler.removeCallbacks(this)
                quoteTextView.text = mainText
                val fabNewQuote: FloatingActionButton = findViewById(R.id.fab)
                fabNewQuote.isEnabled = true
                Log.d("Main", "Quote displayed, updating checkbox")
                runOnUiThread { updateFavoriteCheckBox() }
            }
        }
    }

    handler.postDelayed(runnable, 0)
}

    private fun setupFavoriteCheckBox() {
        val checkBox: CheckBox = findViewById(R.id.cbHeart)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            updateFavoriteStatus(isChecked)
        }
    }

    private fun updateFavoriteStatus(isChecked: Boolean) {
        val sharedPref = getSharedPreferences("FAV_QUOTES", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val favQuotesSet = sharedPref.getStringSet("quotes", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        if (isChecked) {
            favQuotesSet.add(mainText)
        } else {
            favQuotesSet.remove(mainText)
        }

        editor.putStringSet("quotes", favQuotesSet)
        editor.apply()
    }


    private fun updateFavoriteCheckBox() {
        val checkBox: CheckBox = findViewById(R.id.cbHeart)
        val sharedPref = getSharedPreferences("FAV_QUOTES", Context.MODE_PRIVATE)
        val favQuotesSet = sharedPref.getStringSet("quotes", mutableSetOf()) ?: mutableSetOf()
        checkBox.setOnCheckedChangeListener(null)
        checkBox.isChecked = favQuotesSet.contains(mainText)
        Log.d("Main", "Checkbox updated: ${checkBox.isChecked}")
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref.edit()
            val updatedFavQuotesSet = sharedPref.getStringSet("quotes", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            if (isChecked) {
                updatedFavQuotesSet.add(mainText)
            } else {
                updatedFavQuotesSet.remove(mainText)
            }

            editor.putStringSet("quotes", updatedFavQuotesSet)
            editor.apply()
        }
    }

    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.nav_share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT,mainText)
                startActivity(Intent.createChooser(shareIntent,"Share this Quote"))
                true
            }
            R.id.nav_favorites -> {
                val intent=Intent(this,favouriates::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    

    }
