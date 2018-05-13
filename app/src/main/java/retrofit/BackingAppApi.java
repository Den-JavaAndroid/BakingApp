package retrofit;



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import recipes.Recipe;

public interface BackingAppApi {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getReceips();
}
