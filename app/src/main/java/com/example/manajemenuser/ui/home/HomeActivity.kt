package com.example.manajemenuser.ui.home

import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.manajemenuser.R
import com.example.manajemenuser.adapter.ManajemenAdapter
import com.example.manajemenuser.db.DatabaseHelper
import com.example.manajemenuser.network.ApiClient
import com.example.manajemenuser.network.ApiInterface
import com.example.manajemenuser.pojo.ResponseGetAll
import com.example.manajemenuser.pojo.ResponseGetAllOffline
import com.example.manajemenuser.pojo.ResponseInsert
import com.example.manajemenuser.receiver.NetworkStateChecker
import com.example.manajemenuser.ui.add.AddActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeActivity : AppCompatActivity() {
    //Broadcast receiver to know the sync status
    private var broadcastReceiver: BroadcastReceiver? = null
    var manajemenAdapter: ManajemenAdapter? = null
    private var db: DatabaseHelper? = null
    private var list: MutableList<ResponseGetAllOffline>? = null
    private var dataFromServer: List<ResponseGetAll>? = null
    var listViewNames: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listViewNames = findViewById(R.id.listViewNames)
//        registerReceiver(
//            NetworkStateChecker(),
//            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        )

        db = DatabaseHelper(this)
        list = ArrayList()
        dataFromServer = ArrayList()



        btnTambah.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        btnSync.setOnClickListener {
            syncData()
        }

    }

    override fun onResume() {
        super.onResume()
        loadNames()
        checkSyncVisible()


    }

    private fun checkSyncVisible() {
        val cursor = db?.unsyncedNames
        if (cursor != null)

            if (cursor.count > 0) {
                btnSync.visibility = View.VISIBLE
            } else {
                btnSync.visibility = View.GONE
            }
    }

    private fun syncData() {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val acNetworkInfo = cm.activeNetworkInfo
        //cek jaringan
        if (acNetworkInfo != null) {
            if (acNetworkInfo.type == ConnectivityManager.TYPE_WIFI || acNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                val cursor = db!!.unsyncedNames
                if (cursor.moveToFirst()) {
                    do {
                        saveName(
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL))
                        )
                    } while (cursor.moveToNext())
                }
            }
        }
    }

    private fun saveName(id: Int, nama: String, email: String) {
        val service =
            ApiClient.client?.create(ApiInterface::class.java)
        val call = service?.insertData(KEY, nama, email)
        call?.enqueue(object : Callback<ResponseInsert> {
            override fun onResponse(
                call: Call<ResponseInsert>,
                response: Response<ResponseInsert>
            ) {
                val error = response.code()
                if (error == 200) {
                    Toast.makeText(this@HomeActivity, "Suksess sync", Toast.LENGTH_LONG).show()
                    db?.updateNameStatus(
                        id,
                       NAME_SYNCED_WITH_SERVER,
                        response.body()?.date
                    )
                    list?.clear()
                    manajemenAdapter?.notifyDataSetChanged()
                    loadNames()
                    checkSyncVisible()
                }
            }

            override fun onFailure(
                call: Call<ResponseInsert>,
                t: Throwable
            ) {
                Toast.makeText(this@HomeActivity, "Gagal sync", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun loadNames() {
        //getdataFromServer();
        list?.clear()
        val cursor = db?.names
        if (cursor?.moveToFirst()!!) {
            do {
                val name = ResponseGetAllOffline(
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                )
                list?.add(name)
            } while (cursor.moveToNext())
        }
        if (list.isNullOrEmpty()) {
            tvEmpty.visibility = View.VISIBLE
        } else {
            tvEmpty.visibility = View.GONE
        }

        listViewNames?.layoutManager = LinearLayoutManager(this)
        manajemenAdapter = list?.let { ManajemenAdapter(it, this, this) }
        listViewNames?.adapter = manajemenAdapter
        manajemenAdapter?.notifyDataSetChanged()
    }


    companion object {
        //magic number
        const val NAME_SYNCED_WITH_SERVER = 1
        const val NAME_NOT_SYNCED_WITH_SERVER = 0
        const val KEY = "bariusss"

        //a broadcast to know weather the data is synced or not
        const val DATA_SAVED_BROADCAST = "com.example.manajemenuser"
    }
}