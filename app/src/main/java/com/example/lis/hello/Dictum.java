package com.example.lis.hello;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Dictum {

    public static class ApiResult{
        @SerializedName("quoteText")
        public String quoteText;
        @SerializedName("quoteAuthor")
        public String quoteAuthor;
    }

    public interface DictumService{
        @GET("/api/1.0/?method=getQuote&format=json&lang=ru")
        Call<ApiResult> getResult();
    }

    public static void get(final MyConsumer callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.forismatic.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<ApiResult> call = retrofit
                .create(DictumService.class)
                .getResult();

        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResult = response.body();
                if(apiResult.quoteAuthor.isEmpty())
                    apiResult.quoteAuthor = "Автор неизвестен";
                String result = apiResult.quoteText + "\n " + apiResult.quoteAuthor;
                callback.myAccept(result);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

            }
        });
    }

}
