package com.example.consumirrestconretrofit.Interface;

import android.widget.EditText;

import com.example.consumirrestconretrofit.Model.j_id;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RevistasUteq {



    @GET("issues.php")
    Call <List<j_id>> getRevistas(
            @Query("j_id") String id
    );





}
