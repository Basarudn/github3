package com.example.githubuser.data.retrofit

import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.FollowerResponse
import com.example.githubuser.data.response.FollowingResponse
import com.example.githubuser.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("search/users")
    fun getListUsers(
        @Path("q") query: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username:String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username:String
    ): Call<FollowerResponse>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<FollowingResponse>


}