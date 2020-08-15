package com.aravind.wisdomleaf.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.aravind.wisdomleaf.R;
import com.aravind.wisdomleaf.model.ApiResponse;
import com.aravind.wisdomleaf.remote.RestApiService;
import com.aravind.wisdomleaf.remote.RetrofitInstance;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateNewDataSource(10);
    }

    private void updateNewDataSource(int limit) {
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<List<ApiResponse>> picSum = apiService.getPicSum(2, limit);
        picSum.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                List<ApiResponse> body = response.body();
                Log.d("RepositoryData", "Response is :::" + new Gson().toJson(body));
                //generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {

            }
        });
    }
}