package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("numbers")
    @Expose
    private Numbers numbers;

    public HomeResponse() {
    }

    public HomeResponse(String result, Numbers numbers) {
        this.result = result;
        this.numbers = numbers;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Numbers getNumbers() {
        return numbers;
    }

    public void setNumbers(Numbers numbers) {
        this.numbers = numbers;
    }
}
