package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.model.PersonModel
import com.example.tasks.service.model.ValidationModel
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val _login = MutableLiveData<ValidationModel>()
    val login: LiveData<ValidationModel> = _login



    private val mLoggedUser = MutableLiveData<Boolean>()
    var loggedUser: LiveData<Boolean> = mLoggedUser



     /**
     * Faz login usando API
     */
    fun doLogin(email: String, password: String) {
        mPersonRepository.login(email,password, object: APIListener {
            override fun onSucess(model: PersonModel) {

                _login.value = ValidationModel()
                //SHARED PREFERENCES para salvar em memória as informações base que sempre serão utilizadas
                //ou então utlizadas com muita frequência
                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY,model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY,model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME,model.name)

                //mLogin.value = ValidationListener()
            }

            override fun onFailure(message: String) {

                _login.value = ValidationModel(message)

            }

        })
    }

    /**
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

        val token = mSharedPreferences.get(TaskConstants.SHARED.TOKEN_KEY)
        val personKey = mSharedPreferences.get(TaskConstants.SHARED.PERSON_KEY)

        val logged = (token != "" && personKey != "")
        mLoggedUser.value = logged

    }

}