
package com.bhavadeep.kanopy_project.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tree {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("url")
    @Expose
    private String url;

    public String getSha() {
        return sha;
    }

    public String getUrl() {
        return url;
    }

}
