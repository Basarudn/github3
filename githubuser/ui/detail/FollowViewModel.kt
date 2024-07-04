package com.example.githubuser.ui.detail

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.FollowerResponse
import com.example.githubuser.data.response.FollowingResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val  _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val _isLoadingFollowers = MutableLiveData<Boolean>()
    val isLoadingFollowers: LiveData<Boolean> = _isLoadingFollowers

    fun getFollowing(username: String) {
        _isLoadingFollowing.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<FollowingResponse> {
            override fun onResponse(call: Call<FollowingResponse>, response: Response<FollowingResponse>) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    _following.value = (response. body()?.followingResponse ?: emptyList()) as List<ItemsItem>?
                } else {
                    _following.value = emptyList()
                    Log.e("FollowViewModel", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FollowingResponse>, t: Throwable) {
                _isLoadingFollowing.value = false
                _following.value = emptyList()
                Log.e("FollowViewModel", "Error: ${t.message}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoadingFollowers.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<FollowerResponse> {
            override fun onResponse(call: Call<FollowerResponse>, response: Response<FollowerResponse>) {
                _isLoadingFollowers.value = false
                if (response.isSuccessful) {
                    _followers.value = (response. body()?.followerResponse ?: emptyList()) as List<ItemsItem>?
                } else {
                    _followers.value = emptyList()
                    Log.e("FollowViewModel", "Error: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<FollowerResponse>, t: Throwable) {
                _isLoadingFollowers.value = false
                _followers.value = emptyList()
                Log.e("FollowViewModel", "Error: ${t.message}")
            }
        })
    }
}