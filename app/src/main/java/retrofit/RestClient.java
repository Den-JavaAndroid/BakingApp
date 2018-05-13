package retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import recipes.Recipe;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient implements BackingAppApi{
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    private final BackingAppApi backingAppApi;

    public RestClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        backingAppApi = retrofit.create(BackingAppApi.class);
    }

    @Override
    public Call<List<Recipe>> getReceips() {
        return backingAppApi.getReceips();
    }
}
