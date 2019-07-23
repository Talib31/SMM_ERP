package com.android.erp.Network.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientResponse {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("displayname")
    @Expose
    private String displayname;
    @SerializedName("paketId")
    @Expose
    private String paketId;
    @SerializedName("isAdmin")
    @Expose
    private String isAdmin;
    @SerializedName("adminName")
    @Expose
    private String adminName;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("paketName")
    @Expose
    private String paketName;
    @SerializedName("site")
    @Expose
    private String site;

    public ClientResponse() {
    }

    public ClientResponse(String userId, String username, String password, String token, String displayname, String paketId, String isAdmin,
                          String adminName, String telephone, String place, String paketName, String site) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.token = token;
        this.displayname = displayname;
        this.paketId = paketId;
        this.isAdmin = isAdmin;
        this.adminName = adminName;
        this.telephone = telephone;
        this.place = place;
        this.paketName = paketName;
        this.site = site;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getPaketId() {
        return paketId;
    }

    public void setPaketId(String paketId) {
        this.paketId = paketId;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPaketName() {
        return paketName;
    }

    public void setPaketName(String paketName) {
        this.paketName = paketName;
    }
}
