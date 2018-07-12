package filipe.rehder.popularmovies.network;

import filipe.rehder.popularmovies.models.DiscoverResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query("api_key") String api_key,
                                          @Query("sort_by") String sortBy);

}