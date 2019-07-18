package com.android.erp.Network;

import com.android.erp.Network.Response.CategoriesResponse;
import com.android.erp.Network.Response.HomeResponse;
import com.android.erp.Network.Response.LoginResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php/")
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("forgotpassrequest.php/")
    Observable<LoginResponse> forgotPassword(@Field("username") String username);

    @FormUrlEncoded
    @POST("nbposts.php/")
    Observable<HomeResponse> getMain(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getallposts.php/")
    Observable<List<CategoriesResponse>> getPosts(@Field("userId") String userId,
                                                 @Field("categoryId") String categoryId);
}
