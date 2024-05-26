package com.syntax_institut.whatssyntax.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syntax_institut.whatssyntax.BuildConfig
import com.syntax_institut.whatssyntax.data.datamodels.Calls
import com.syntax_institut.whatssyntax.data.datamodels.Chat
import com.syntax_institut.whatssyntax.data.datamodels.Contact
import com.syntax_institut.whatssyntax.data.datamodels.Messages
import com.syntax_institut.whatssyntax.data.datamodels.Note
import com.syntax_institut.whatssyntax.data.datamodels.Profile
import com.syntax_institut.whatssyntax.data.local.WhatsSyntaxDatabase
import com.syntax_institut.whatssyntax.data.remote.WhatsSyntaxApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// Repository class for managing data operations
class Repository(private val api: WhatsSyntaxApi, private val database: WhatsSyntaxDatabase) {


    // TAG for logging
    private val TAG = "RepoTAG"

    // Number and key for API requests
    private val number = 5
    private val key = BuildConfig.apiKey


    // LiveData for contacts
    private val _contact = MutableLiveData<List<Contact>>()
    val contact: LiveData<List<Contact>>
        get() = _contact


    // LiveData for profile
    private val _profile = MutableLiveData<List<Profile>>()
    val profile: LiveData<List<Profile>>
        get() = _profile


    // LiveData for chats
    private val _chat = MutableLiveData<List<Chat>>()
    val chat: LiveData<List<Chat>>
        get() = _chat


    // LiveData for calls
    private val _call = MutableLiveData<List<Calls>>()
    val call: LiveData<List<Calls>>
        get() = _call


    // LiveData for Messages
    private val _messages = MutableLiveData<List<Messages>>()
    val messages: LiveData<List<Messages>>
        get() = _messages


    //LiveData for Notes
    private val _notes = database.databaseDao.getAll()
    val note: LiveData<List<Note>>
        get() = _notes


    // Function to fetch chat data from the API
    suspend fun getChat() {
        try {
            Log.i(TAG, "loading chat data")
            val conversationData = api.retrofitService.getChats(number, key)
            _chat.value = conversationData
            Log.i(TAG, "chat Data Loaded $conversationData")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading chat data $e")
        }
    }


    // Function to fetch Messages data from the API
    suspend fun getMessage(id: Int) {
        try {
            Log.i(TAG, "loading Message data")
            val conversationData = api.retrofitService.getMessages(number, id, key)
            _messages.value = conversationData
            Log.i(TAG, "Message Data Loaded $conversationData")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Message data $e")
        }
    }


    // Function to send Messages to the API
    suspend fun sendMessage(id: Int, messages: Messages) {
        try {
            Log.i(TAG, "loading Message data")
            val conversationData = api.retrofitService.sendMessage(number, id, messages, key)
            Log.i(TAG, "Message sent to chat $conversationData")
            Log.i(TAG, "Message sent to chat $id")
        } catch (e: Exception) {
            Log.e(TAG, "Error sending message: $e")
        }
    }


    // Function to fetch contact data from the API
    suspend fun getContact() {
        try {
            Log.i(TAG, "loading contact data")
            val conversationData = api.retrofitService.getContacts(number, key)
            _contact.value = conversationData
            Log.i(TAG, "Contact Data Loaded $conversationData")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading contact data $e")
        }
    }


    // Function to fetch call data from the API
    suspend fun getCall() {
        try {
            Log.i(TAG, "loading Call data")
            val conversationData = api.retrofitService.getCalls(number, key)
            _call.value = conversationData
            Log.i(TAG, "Call Data Loaded $conversationData")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Call data $e")
        }
    }


    // Function to fetch profile data from the API
    suspend fun getProfile() {
        try {
            Log.i(TAG, "loading profile data")
            val profileData = api.retrofitService.getProfile(number, key)
            _profile.value = listOf(profileData)
            Log.i(TAG, "profile Data Loaded $profileData")
        } catch (e: Exception) {
            Log.e(TAG, "Error loading profile data $e")
        }
    }


    // Function to set profile data on the API
    suspend fun setProfile(profile: Profile) {
        try {
            Log.i(TAG, "profile data loading")
            api.retrofitService.setProfile(number, profile, key)
            Log.i(TAG, "Profile set successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error setting profile $e")
        }
    }


    // Function to insert the notes to the database
    suspend fun insert(note: Note) {
        try {
            database.databaseDao.insert(note)
            Log.i(TAG, "Data insert Completed")
        } catch (e: Exception) {
            Log.e(TAG, "Error writing into database: $e")
        }
    }

    // Function to delete on note at the time
    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            try {
                database.databaseDao.deleteById(note.id)
                Log.i(TAG, "deleting note data Successful")
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting the note data... $e")
            }
        }
    }

    // Function to delete all notes
    suspend fun deleteAllNote() {
        withContext(Dispatchers.IO) {
            try {
                database.databaseDao.deleteAll()
                Log.i(TAG, "deleting note data Successful")
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting the note data... $e")
            }
        }
    }

}