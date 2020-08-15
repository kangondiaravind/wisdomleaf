package com.aravind.wisdomleaf.remote;

import com.aravind.wisdomleaf.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiService {

    @GET("/v2/list")
    Call<List<ApiResponse>> getPicSum(
            @Query("page") int page,
            @Query("limit") int limit
    );

}
