package com.example.praktikum1mp_g131230050_miftah

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.security.MessageDigest

class EditProfileActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DB_Helper // iki

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        databaseHelper = DB_Helper(this)  // iki


        // Ambil dan Tampilkan Data Intent
        val dataNIM: String = intent.getStringExtra("nim").toString()
        val dataNama: String = intent.getStringExtra("nama").toString()
        val dataEmail: String = intent.getStringExtra("email").toString()


        // Kode Inisialisasi
        var editNIM = findViewById<EditText>(R.id.editNim)
        var editNama = findViewById<EditText>(R.id.editNama)
        var editEmail = findViewById<EditText>(R.id.editEmail)
        var editPassword = findViewById<EditText>(R.id.editPassword)

        editNIM.isEnabled = false
        editNIM.setText(dataNIM)
        editNama.setText(dataNama)
        editEmail.setText(dataEmail)

        val btnKonfirmasi = findViewById<Button>(R.id.btnKonfirmasi)
        val btnBatal= findViewById<Button>(R.id.btnEditBatal)
        var newNama: String = ""
        var newEmail: String = ""

        databaseHelper = DB_Helper(this)

        btnKonfirmasi.setOnClickListener {
            newNama = editNama.text.toString()
            newEmail = editEmail.text.toString()
            val newPassword: String = editPassword.text.toString()
            // Verifikasi Data
            if (newNama.equals("") || newEmail.equals("")) {
                Toast.makeText(
                    applicationContext, "Nama dan Email tidak boleh Kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val db = databaseHelper.writableDatabase

                var updateValues: ContentValues? = null
                if (newPassword.equals("")) {
                    updateValues = ContentValues().apply {
                        put("nama", newNama)
                        put("email", newEmail)
                    }
                }
                else
                {
                    updateValues = ContentValues().apply {
                        put("nama", newNama)
                        put("email", newEmail)
                        put("password", newPassword)
                    }

                }


                var result = db.update(
                    "TBL_MHS", updateValues,
                    "nim = ? AND nama = ?", arrayOf(dataNIM, dataNama)
                )
                if (result > 0) {
                    Toast.makeText(applicationContext, "Update Berhasil", Toast.LENGTH_LONG)
                        .show()
                    // Tambah ini
//                    val resultIntent = Intent()
//                    resultIntent.putExtra("updated_nama", newNama)
//                    resultIntent.putExtra("updated_email", newEmail)
//                    setResult(RESULT_OK, resultIntent)
//                    finish() // Kembali ke ProfileActivity
                }
                else {
                    Toast.makeText(applicationContext, "Update Gagal", Toast.LENGTH_LONG).show()
                }
            }

            btnBatal.setOnClickListener {
                val intent = Intent(this, Profile_activity::class.java)
                intent.putExtra("nim", dataNIM)
                intent.putExtra("nama", newNama)
                intent.putExtra("email", newEmail)
                startActivity(intent) // Menggunakan launcher
            }


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
                )
                insets
            }
        }
    }
}