package com.example.android.saveme.common.data.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("height")
    @Expose
    public Long height;
    @SerializedName("html_attributions")
    @Expose
    public List<String> htmlAttributions = null;
    @SerializedName("photo_reference")
    @Expose
    public String photoReference;
    @SerializedName("width")
    @Expose
    public Long width;

}