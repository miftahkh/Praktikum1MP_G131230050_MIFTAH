package com.example.praktikum1mp_g131230050_miftah

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Patterns
import java.security.MessageDigest

class daftarActifity : AppCompatActivity() {
    private lateinit var databaseHelper: DB_Helper
    fun isValidEmail(email : String): Boolean{
        val result:Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return result
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar_actifity)

        databaseHelper = DB_Helper(this)

        //kode inisialisasi
        var inpNIM: EditText = findViewById(R.id.inpNIM)
        var inpNama: EditText = findViewById(R.id.inpNama)
        var inpEmail: EditText = findViewById(R.id.inpEmail)
        var inpPassword: EditText = findViewById(R.id.inpPassword)
        val btnDaftar: Button = findViewById(R.id.btnDaftar)
        val btnBersih: Button = findViewById(R.id.btnBersih)
        val btnBatal: Button = findViewById(R.id.btnBatal)

        btnDaftar.setOnClickListener {
            //ambil data dari edit data
            val NIM: String = inpNIM.text.toString()
            val Nama: String = inpNama.text.toString()
            val Email: String = inpEmail.text.toString()
            val Password: String = inpPassword.text.toString()



            if (NIM.equals("") || Nama.equals("") || Email.equals("") || Password.equals("")) {

                Toast.makeText(this, "Input Data Masih Kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (!isValidEmail(Email)) {
                    Toast.makeText(this, "Email Tidak Valid", Toast.LENGTH_SHORT).show()
                } else {
                }
            }
            val db = databaseHelper.readableDatabase
            val insertValues = ContentValues().apply {
                put("nim", NIM)
                put("nama", Nama)
                put("email", Email)
                put("password", Password)

            }
            val result = db.insert("TBL_MHS", null, insertValues)


            db.close()
            if (result != -1L) {
                Toast.makeText(applicationContext, "Kueri Sukses", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Kueri Gagal", Toast.LENGTH_SHORT).show()
            }

        }





    btnBersih.setOnClickListener{
        //bersihkan input data

        inpNIM.setText("")
        inpNama.setText("")
        inpEmail.setText("")
        inpPassword.setText("")
    }
    btnBatal.setOnClickListener{
        // batalkan dengan finish

        finish()
    }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}