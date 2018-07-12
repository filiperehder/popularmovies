package filipe.rehder.popularmovies.network;

import filipe.rehder.popularmovies.models.DiscoverResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/{endpoint}")
    Call<DiscoverResponse> discoverMovies(@Path(value = "endpoint", encoded = true) String endpoint,
                                          @Query("api_key") String api_key);

}