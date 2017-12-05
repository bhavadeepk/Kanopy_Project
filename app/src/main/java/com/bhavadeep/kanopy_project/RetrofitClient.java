package com.bhavadeep.kanopy_project;
import com.bhavadeep.kanopy_project.Models.MasterCommit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


class RetrofitClient{

    private static final String url= "https://api.github.com/repos/torvalds/linux/";


    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static GitHub getGitHUbService() {
        return getRetrofitInstance().create(GitHub.class);
    }

    public interface GitHub {
        @GET("commits")
        Call<List<MasterCommit>> listCommits();
    }
}
