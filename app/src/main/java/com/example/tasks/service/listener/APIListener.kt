package com.example.tasks.service.listener

import com.example.tasks.service.model.PersonModel

interface APIListener {

    fun onSucess(model: PersonModel)

    fun onFailure(str: String)
}