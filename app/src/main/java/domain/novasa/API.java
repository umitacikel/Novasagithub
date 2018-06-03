package domain.novasa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;



public interface API {

    String Base_Url = "https://novasa.com/api/job/";

    @GET("list/5.json")
    Call<Objectss> getObj();

    @POST("list/5/products")
    Call<Objectss> addObj(@Body Objectss obj);

    @DELETE("list/5/products/{id}")
    Call<Objectss> deleteObj(@Path("id") String id);

}
