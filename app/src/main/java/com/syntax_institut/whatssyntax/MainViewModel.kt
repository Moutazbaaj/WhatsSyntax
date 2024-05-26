package com.syntax_institut.whatssyntax

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.syntax_institut.whatssyntax.data.Repository
import com.syntax_institut.whatssyntax.data.datamodels.Messages
import com.syntax_institut.whatssyntax.data.datamodels.Note
import com.syntax_institut.whatssyntax.data.datamodels.Profile
import com.syntax_institut.whatssyntax.data.local.getDatabase
import com.syntax_institut.whatssyntax.data.remote.WhatsSyntaxApi
import kotlinx.coroutines.launch


// Enum class to represent the status of API requests
enum class ApiStatus { LOADING, ERROR, DONE }


// ViewModel class responsible for managing UI-related data and operations
class MainViewModel(application: Application) : AndroidViewModel(application) {


    // TAG for logging
    val TAG = "MainViewModelTAG"

    // instance for database
    private val database = getDatabase(application)

    // Repository instance for data operations
    private val repository = Repository(WhatsSyntaxApi, database)


    // LiveData to represent the loading status of API requests
    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading


    // LiveData to indicate completion status
    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean>
        get() = _complete



    // LiveData objects for various data entities:

    val chat = repository.chat

    val contact = repository.contact

    val profile = repository.profile

    val call = repository.call

    val message = repository.messages

    val note = repository.note




    // Initialize data loading operations upon ViewModel creation
    init {
        loadChatData()
        loadContactData()
        loadCallData()
        loadProfileData()
    }



    ////////// API Methods //////////




    // Method to load chat data asynchronously
    fun loadChatData() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Loading chat data")
            try {
                repository.getChat()
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Done loading chat data")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading chat data $e")
            }
        }
    }

    // Method to load message data asynchronously
    fun loadMessage(id: Int) {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Loading message data")
            try {
                repository.getMessage(id)
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Done loading message data")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading message data $e")
            }
        }
    }

    // Method to send a message
    fun sendMessage(id: Int, messages: Messages) {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Sending message")
            try {

                repository.sendMessage(id, messages)
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Message sent")
            } catch (e: Exception) {
                Log.e(TAG, "Error sending message $e")
            }
        }
    }


    // Method to load contact data asynchronously
    fun loadContactData() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Loading contact data")
            try {
                repository.getContact()
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Done loading contact data")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading contact data $e")
            }
        }
    }


    // Method to load call data asynchronously
    fun loadCallData() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Loading call data")
            try {
                repository.getCall()
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Done loading call data")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading call data $e")
            }
        }

    }


    // Method to load profile data asynchronously
    fun loadProfileData() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Loading Profile data")
            try {
                repository.getProfile()
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Done loading Profile data")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading Profile data $e")
            }
        }
    }


    // Method to set profile data asynchronously
    fun setProfileData(profile: Profile) {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            Log.i(TAG, "Setting Profile data")
            try {
                //val profile = Profile(name, number, imageUri)
                repository.setProfile(profile)
                _loading.value = ApiStatus.DONE
                Log.i(TAG, "Profile data set successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error setting Profile data $e")
            }
        }
    }



    ////////// Database Methods //////////



    // Method to insert note
    fun insertNote(titel: String, message: String) {
        viewModelScope.launch {
            val note = Note(name = titel, text = message)
            repository.insert(note)
            _complete.value = true
            Log.i(TAG, "Insert completed : '${note.text}'")

        }
    }


    // Method to delete note
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
            _complete.value = true
            Log.i(TAG, "Deleting Note with id: ${note.id} completed")

        }
    }

    // Method to delete all notes
    fun deleteAllNotes(){
        viewModelScope.launch {
            repository.deleteAllNote()
            _complete.value = true
            Log.i(TAG, "Deleting Note list is complete ")
        }
    }


    // Method to unset completion status
    fun unsetComplete() {
        _complete.value = false
    }

}


