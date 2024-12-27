package com.example.praktikum1mp_g131230050_miftah

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var databaseHelper: DB_Helper // <- tambah ini
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        databaseHelper = DB_Helper(this) // <- tambah ini

        val btntutup: Button = findViewById(R.id.btntutup)
        val btndaftar: Button = findViewById(R.id.btndaftar)
        val btnsubmit: Button = findViewById<Button>(R.id.btnsubmit)
        val inputUsername = findViewById<EditText>(R.id.InputUsername)
        val inputPassword: EditText = findViewById(R.id.InputPasword)

        btnsubmit.setOnClickListener {
            val username: String = inputUsername.text.toString()
            val password: String = inputPassword.text.toString()

            if (username.equals("") || password.equals("")) {
                Toast.makeText(
                    applicationContext,
                    "Username / password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val query =
                    "SELECT * FROM TBL_MHS WHERE nim ='" + username + "'AND password='" + password + "'"

                val db = databaseHelper.readableDatabase
                val cursor = db.rawQuery(query, null)

                val result = cursor.moveToFirst()
                if (result == true) {
                    Toast.makeText(applicationContext, "username;" + username + "password:" + password, Toast.LENGTH_LONG).show()

                    val inpNIM= cursor.getString(cursor.getColumnIndexOrThrow("nim"))
                    val inpName= cursor.getString(cursor.getColumnIndexOrThrow("nama"))
                    val inpEmail= cursor.getString(cursor.getColumnIndexOrThrow("email"))

                    val intent : Intent = Intent(this, Profile_activity::class.java)
                    intent.putExtra("nim",inpNIM)
                    intent.putExtra("nama",inpName)
                    intent.putExtra("email",inpEmail)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Login gagal", Toast.LENGTH_LONG).show()
                }
            }






            btndaftar.setOnClickListener {
                val intent: Intent = Intent(this, daftarActifity::class.java)
                startActivity(intent)
            }

            btntutup.setOnClickListener {
                finish()
            }


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}