package com.example.manajemenuser.ui.add

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.manajemenuser.R
import com.example.manajemenuser.db.DatabaseHelper
import com.example.manajemenuser.network.ApiClient
import com.example.manajemenuser.network.ApiInterface
import com.example.manajemenuser.pojo.ResponseInsert
import com.example.manajemenuser.ui.home.HomeActivity
import com.example.manajemenuser.ui.home.HomeActivity.Companion.NAME_NOT_SYNCED_WITH_SERVER
import com.example.manajemenuser.ui.home.HomeActivity.Companion.NAME_SYNCED_WITH_SERVER
import kotlinx.android.synthetic.main.activity_add.*
import retrofit2.Call
import retrofit2.Response

class AddActivity : AppCompatActivity() {
    private var db: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        db = DatabaseHelper(this)
        actionClick()
    }

    private fun actionClick() {
        btnSimpan.setOnClickListener {

            val name = "${edtFirstName.text.toString()} ${edtFirstName.text.toString()}"
            val email = edtEmail.text.toString()
            if (edtEmail.text.toString().isEmpty()) {
                edtEmail.setError("Email tidak bisi kosong")
            } else if (edtFirstName.text.toString().isEmpty()) {
                edtFirstName.setError("Nama depan tidak bisa kosong")
            } else if (edtLastName.text.toString().isEmpty()) {
                edtLastName.setError("Nama Belakang tidak bisa kosong")

            } else {
                val service = ApiClient.getClient().create(
                    ApiInterface::class.java
                )
                val call =
                    service.insertData(
                        HomeActivity.KEY,
                        name,
                        email
                    )

                call.enqueue(object : retrofit2.Callback<ResponseInsert> {
                    override fun onFailure(call: Call<ResponseInsert>, t: Throwable) {
                        db?.addName(name, email, "", NAME_NOT_SYNCED_WITH_SERVER)
                        finish()
                    }

                    override fun onResponse(
                        call: Call<ResponseInsert>,
                        response: Response<ResponseInsert>
                    ) {
                        if (response.isSuccessful) {
                            db?.addName(
                                response.body()?.name,
                                response.body()?.email,
                                response.body()?.date,
                                NAME_SYNCED_WITH_SERVER
                            )

                            Toast.makeText(
                                this@AddActivity,
                                "success insert data to server",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()

                        } else {
                            finish()
                            db?.addName(name, email, "", NAME_NOT_SYNCED_WITH_SERVER)
                        }
                    }
                })
            }
        }
    }
}