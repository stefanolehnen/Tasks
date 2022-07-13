package com.example.tasks.service.repository

import android.content.Context
import com.example.tasks.R
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonRepository(val context: Context) {

    private val mRemote = RetrofitClient.createService(PersonService::class.java)

    //fara a chamada de fato para a API
    fun login(email: String, password: String, listener: APIListener) {
        val call: Call<PersonModel> = mRemote.login(email,password)

        //chamada assíncrona
        call.enqueue(object : Callback<PersonModel>{
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS){
                    val validation = Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                }
                else{
                    response.body()?.let { listener.onSucess(it) }
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))  //vai replicar a mensagem de erro no APP
            }
        })

    }

    fun create(name:String, email: String, password: String, listener: APIListener) {
        val call: Call<PersonModel> = mRemote.create(name, email,password)

        //chamada assíncrona
        call.enqueue(object : Callback<PersonModel>{
            override fun onResponse(call: Call<PersonModel>, response: Response<PersonModel>) {
                if (response.code() != TaskConstants.HTTP.SUCCESS){
                    val validation = Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(validation)
                }
                else{
                    response.body()?.let { listener.onSucess(it) }
                }
            }

            override fun onFailure(call: Call<PersonModel>, t: Throwable) {
                listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))  //vai replicar a mensagem de erro no APP
            }
        })

    }
}