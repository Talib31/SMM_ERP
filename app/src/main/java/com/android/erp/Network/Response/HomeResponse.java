package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("numbers")
    @Expose
    private Numbers numbers;
    @SerializedName("paketName")
    @Expose
    private PaketName paketName;
    @SerializedName("paketContents")
    @Expose
    private List<InfoResponse> paketContents = null;

    public HomeResponse() {
    }

    public HomeResponse(String result, Numbers numbers, PaketName paketName, List<InfoResponse> paketContents) {
        this.result = result;
        this.numbers = numbers;
        this.paketName = paketName;
        this.paketContents = paketContents;
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

    public PaketName getPaketName() {
        return paketName;
    }

    public void setPaketName(PaketName paketName) {
        this.paketName = paketName;
    }

    public List<InfoResponse> getPaketContents() {
        return paketContents;
    }

    public void setPaketContents(List<InfoResponse> paketContents) {
        this.paketContents = paketContents;
    }
}
