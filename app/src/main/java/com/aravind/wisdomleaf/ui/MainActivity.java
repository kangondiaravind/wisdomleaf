package com.aravind.wisdomleaf.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aravind.wisdomleaf.R;
import com.aravind.wisdomleaf.model.ApiResponse;
import com.aravind.wisdomleaf.remote.RestApiService;
import com.aravind.wisdomleaf.remote.RetrofitInstance;
import com.aravind.wisdomleaf.utils.ConnectivityCheck;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDoalog;
    RecyclerView recyclerView;
    PhotosAdapter photosAdapter;
    SwipeRefreshLayout swipeRefresh;
    public int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefresh = findViewById(R.id.swiperefresh);
        limit = 10;
        getNewDataSource();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewDataSource();
            }
        });
    }

    private void getNewDataSource() {
        if (ConnectivityCheck.isNetworkConnected(MainActivity.this)) {
            updateNewDataSource();
        } else {
            swipeRefresh.setRefreshing(false);
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNewDataSource() {
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<List<ApiResponse>> picSum = apiService.getPicSum(1, limit);
        picSum.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                swipeRefresh.setRefreshing(false);
                List<ApiResponse> body = response.body();
                Log.d("RepositoryData", "Response is :::" + new Gson().toJson(body));
                generateDataList(response.body());
                limit = limit + 10;
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {

            }
        });
    }

    private void generateDataList(List<ApiResponse> apiResponses) {
        recyclerView = findViewById(R.id.recyclerView);
        Collections.reverse(apiResponses);
        photosAdapter = new PhotosAdapter(apiResponses, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new PhotosAdapter(apiResponses, MainActivity.this));
        photosAdapter.notifyDataSetChanged();
    }

}