package filipe.rehder.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.util.ArrayList;
import filipe.rehder.popularmovies.adapter.MoviesAdapter;
import filipe.rehder.popularmovies.adapter.OnItemClickListener;
import filipe.rehder.popularmovies.models.DiscoverResponse;
import filipe.rehder.popularmovies.models.MovieModel;
import filipe.rehder.popularmovies.network.MovieService;
import filipe.rehder.popularmovies.network.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private MoviesAdapter adapterMovies;
    private String filterQuery;

    RecyclerView recyclerMovies;
    ProgressBar pbLoadingMovies;
    LinearLayout llConnectionProblem;
    NetworkController controller;
    Button btnConnectionError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbLoadingMovies = findViewById(R.id.pbLoadingMovies);
        llConnectionProblem = findViewById(R.id.llConnectionProblem);
        btnConnectionError = findViewById(R.id.btnConnectionError);
        controller = new NetworkController();

        setupRecyclerView();
        controller.onStart(getResources().getString(R.string.filterPopularity));
    }

    private void setupRecyclerView() {
        recyclerMovies = findViewById(R.id.rvMovies);
        recyclerMovies.setLayoutManager(new GridLayoutManager(this, 2));

        adapterMovies = new MoviesAdapter(this);
        recyclerMovies.setAdapter(adapterMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.byPopularMovies:
                controller.onStart(getResources().getString(R.string.filterPopularity));
                break;

            case R.id.byRating:
                controller.onStart(getResources().getString(R.string.filterRating));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(MovieModel item) {
        Intent intent = new Intent(this, MovieDetailActivity.class);

        intent.putExtra("overview", item.getOverview());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("backdrop", item.getBackdropPath());
        intent.putExtra("rating", item.getRating());
        intent.putExtra("poster", item.getPosterPath());
        intent.putExtra("releaseDate", item.getReleaseDate());

        startActivity(intent);
    }

    public class NetworkController implements Callback<DiscoverResponse> {

        void onStart(String sortBy) {
            filterQuery = sortBy;

            llConnectionProblem.setVisibility(View.GONE);
            pbLoadingMovies.setVisibility(View.VISIBLE);
            recyclerMovies.setVisibility(View.GONE);

            NetworkUtils networkUtils = new NetworkUtils();

            Retrofit retrofit = (networkUtils.buildRetrofit());
            MovieService apiService = retrofit.create(MovieService.class);

            Call<DiscoverResponse> movieList = apiService.discoverMovies(BuildConfig.API_KEY, sortBy);
            movieList.enqueue(this);
        }

        @Override
        public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
            pbLoadingMovies.setVisibility(View.GONE);
            llConnectionProblem.setVisibility(View.GONE);
            recyclerMovies.setVisibility(View.VISIBLE);

            ArrayList<MovieModel> movieList = response.body().getResults();
            adapterMovies.setMovieData(movieList);
        }

        @Override
        public void onFailure(Call<DiscoverResponse> call, Throwable t) {
            recyclerMovies.setVisibility(View.GONE);
            pbLoadingMovies.setVisibility(View.GONE);
            llConnectionProblem.setVisibility(View.VISIBLE);

            btnConnectionError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.onStart(filterQuery);
                }
            });
        }
    }
}
