package com.bhavadeep.kanopy_project;
import com.bhavadeep.kanopy_project.Models.MasterCommit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by ${Bhavadeep} on 12/4/2017.
 */
public class RetrofitClient{

    private static String url= "https://api.github.com/repos/torvalds/linux/";


    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static GitHub getGitHUbService() {
        return getRetrofitInstance().create(GitHub.class);
    }

    public interface GitHub {
        @GET("commits")
        Call<List<MasterCommit>> listCommits();
    }
}
