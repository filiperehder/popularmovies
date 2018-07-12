package filipe.rehder.popularmovies.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    public Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(makeGson()))
                .client(okHttpClient())
                .build();
    }

    private Gson makeGson() {
        return new GsonBuilder().create();
    }
}
