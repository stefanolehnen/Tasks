package com.example.tasks.service.repository

import android.app.Person
import com.example.tasks.service.repository.remote.PersonService
import com.example.tasks.service.repository.remote.RetrofitClient

class PersonRepository {

    val remote = RetrofitClient.createService(PersonService::class.java)

    //fara a chamada de fato para a API
    fun login(email: String, password: String) {
    }