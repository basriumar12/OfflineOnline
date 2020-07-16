package com.example.manajemenuser.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.example.manajemenuser.db.DatabaseHelper
import com.example.manajemenuser.network.ApiClient
import com.example.manajemenuser.network.ApiInterface
import com.example.manajemenuser.pojo.ResponseInsert
import com.example.manajemenuser.ui.home.HomeActivity
import com.example.manajemenuser.ui.home.HomeActivity.Companion.KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkStateChecker : BroadcastReceiver() {
    private var context: Context? = null
    private var db: DatabaseHelper? = null
    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        db = DatabaseHelper(context)

        //cek jaringan
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
//        call?.enqueue(object : Callback<ResponseInsert> {
//            override fun onResponse(
//                call: Call<ResponseInsert>,
//                response: Response<ResponseInsert>
//            ) {
//                val error = response.code()
//                if (error == 200) {
//                    db?.updateNameStatus(
//                        id,
//                        HomeActivity.NAME_SYNCED_WITH_SERVER,
//                        response.body()?.date
//                    )
//                    //sending the broadcast to refresh the list
//                    context?.sendBroadcast(Intent(HomeActivity.DATA_SAVED_BROADCAST))
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ResponseInsert>,
//                t: Throwable
//            ) {
//            }
//        })
    }
}