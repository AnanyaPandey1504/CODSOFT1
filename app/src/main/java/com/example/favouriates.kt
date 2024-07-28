package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class favouriates : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favouriates)

        val listViewFavorites: ListView = findViewById(R.id.listViewFavorites)
        val btnReturnToMain:Button = findViewById(R.id.btnReturnToMain)

        val sharedPreference = getSharedPreferences("FAV_QUOTES", Context.MODE_PRIVATE)
        val favQuoteSet = sharedPreference.getStringSet("quotes", setOf()) ?: setOf()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, favQuoteSet.toList())
        listViewFavorites.adapter = adapter

        btnReturnToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        }


        }
}
