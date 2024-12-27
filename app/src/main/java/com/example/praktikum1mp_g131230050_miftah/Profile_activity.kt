package com.example.praktikum1mp_g131230050_miftah

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.MessageDigest

class Profile_activity : AppCompatActivity() {

    private lateinit var databaseHelper: DB_Helper // iki

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Ambil dan Tampilkan Data Intent
        val dataNIM: String = intent.getStringExtra("nim").toString()
        val dataNama: String = intent.getStringExtra("nama").toString()
        val dataEmail: String = intent.getStringExtra("email").toString()

        var tvNIM = findViewById<TextView>(R.id.tvNIM)
        var tvNama = findViewById<TextView>(R.id.tvNama)
        var tvEmail = findViewById<TextView>(R.id.tvEmail)
        val btnProfileUpdate = findViewById<Button>(R.id.btnProfileUpdate)
        val btnProfileKeluar = findViewById<Button>(R.id.btnProfileKeluar)

        tvNIM.text= dataNIM
        tvNama.text= dataNama
        tvEmail.text= dataEmail


        databaseHelper = DB_Helper(this)

        btnProfileKeluar.setOnClickListener{
            finish()
        }

        btnProfileUpdate.setOnClickListener{
            val intent: Intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            intent.putExtra("nim", dataNIM)
            intent.putExtra("nama", dataNama)
            intent.putExtra("email", dataEmail)
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

