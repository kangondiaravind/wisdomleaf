package com.aravind.wisdomleaf.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    ProgressDialog progressDoalog;
    RecyclerView recyclerView;
    RecyclerView mRecyclerView;
    PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        showProgress();
        updateNewDataSource();
    }

    private void updateNewDataSource() {
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<List<ApiResponse>> picSum = apiService.getPicSum(2, 20);
        picSum.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                progressDoalog.dismiss();
                List<ApiResponse> body = response.body();
                Log.d("RepositoryData", "Response is :::" + new Gson().toJson(body));
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {

            }
        });
    }

    private void showProgress() {
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    private void generateDataList(List<ApiResponse> apiResponses) {
        recyclerView = findViewById(R.id.recyclerView);
        photosAdapter = new PhotosAdapter(apiResponses, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photosAdapter);
    }

}