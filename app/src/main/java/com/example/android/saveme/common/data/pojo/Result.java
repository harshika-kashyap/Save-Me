package com.example.android.saveme.common.data.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("opening_hours")
    @Expose
    public OpeningHours openingHours;
    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("scope")
    @Expose
    public String scope;
    @SerializedName("types")
    @Expose
    public List<String> types = null;
    @SerializedName("vicinity")
    @Expose
    public String vicinity;
    @SerializedName("photos")
    @Expose
    public List<Photo> photos = null;
    @SerializedName("rating")
    @Expose
    public Double rating;

}