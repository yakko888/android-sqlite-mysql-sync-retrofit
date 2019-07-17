package net.simplifiedcoding.androidmysqlsync;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

/*    @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();*/

    @FormUrlEncoded
    @POST("saveName.php")
    Call<ResponseName> saveName(@Field("name") String name);

}
