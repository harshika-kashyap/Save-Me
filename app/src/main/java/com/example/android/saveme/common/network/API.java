package com.example.android.saveme.common.network;

import com.example.android.saveme.common.data.pojo.Hospital;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by harshika on 1/4/18.
 * The retrofit service for network transactions
 */

public interface API {

    @GET("json")
    Observable<Hospital> getHospitals(@Query(value = "location") String location,
                                      @Query(value = "radius") long radius,
                                      @Query(value = "type") String type,
                                      @Query(value = "key") String key);
}
