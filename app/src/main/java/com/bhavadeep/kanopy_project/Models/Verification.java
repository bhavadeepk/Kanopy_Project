
package com.bhavadeep.kanopy_project.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verification {

    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("signature")
    @Expose
    private Object signature;
    @SerializedName("payload")
    @Expose
    private Object payload;

    public Boolean getVerified() {
        return verified;
    }

    public String getReason() {
        return reason;
    }

    public Object getSignature() {
        return signature;
    }

    public Object getPayload() {
        return payload;
    }

}
