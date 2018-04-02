package com.example.android.saveme.core.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.util.Log;

import com.example.android.saveme.SaveMeApplication;
import com.example.android.saveme.common.data.pojo.HospitalResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    private final String TAG = MainActivityViewModel.class.getSimpleName();
    private MutableLiveData<HospitalResponse> hospitals;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<HospitalResponse> getNearbyHospitals(Location location, String API_KEY) {
        if (hospitals == null) {
            hospitals = new MutableLiveData<>();
        }

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        String locationString = lat +  "," + lng;
        long radius = 7000;

        compositeDisposable.add(SaveMeApplication.getAPI()
                .getHospitals(locationString, radius, "hospital", API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hospitals -> this.hospitals.postValue(hospitals),
                        throwable -> Log.e(TAG, throwable.getMessage())));

        return hospitals;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
