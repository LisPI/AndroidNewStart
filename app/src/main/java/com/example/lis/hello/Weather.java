package com.example.lis.hello;

import com.google.gson.annotations.SerializedName;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Weather {

    public static class Forecast{
        @SerializedName("temperature")
        public Float temperature;

        @SerializedName("weather_descriptions")
        public String[] condition;
    }

    public static class ApiResult{
        @SerializedName("current")
        public Forecast current;
    }

    public interface WeatherService{
        @GET("/current?access_key=b73c90878581f11f42558f37bde2e993")
        Call<ApiResult> getResult(@Query("query") String city, @Query("lang") String lang);
    }

    public static void get(String city, final MyConsumer callback){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);


        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        Call<ApiResult> call = retrofit
                .create(WeatherService.class)
                .getResult(city, "ru");

        call.enqueue(new Callback<ApiResult>() {
            @Override
            public void onResponse(Call<ApiResult> call, Response<ApiResult> response) {
                ApiResult apiResult = response.body();

                String result = (apiResult != null) ? "Там сейчас " + apiResult.current.condition[0] + ", где-то " + apiResult.current.temperature.intValue() + " градусов": "Данные не найдены.";
                callback.myAccept(result);
            }

            @Override
            public void onFailure(Call<ApiResult> call, Throwable t) {

            }
        });
    }

}
