package filipe.rehder.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    RecyclerView recyclerMovies;
    ProgressBar pbLoadingMovies;
    TextView tvConnectionProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbLoadingMovies = findViewById(R.id.pbLoadingMovies);
        tvConnectionProblem = findViewById(R.id.tvConnectionProblem);

        NetworkController controller = new NetworkController();

        setupRecyclerView();
        controller.onStart();
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

        void onStart() {
            NetworkUtils networkUtils = new NetworkUtils();

            Retrofit retrofit = (networkUtils.buildRetrofit());
            MovieService apiService = retrofit.create(MovieService.class);

            Call<DiscoverResponse> movieList = apiService.discoverMovies(BuildConfig.API_KEY, "popularity.desc");
            movieList.enqueue(this);
        }

        @Override
        public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
            pbLoadingMovies.setVisibility(View.GONE);
            tvConnectionProblem.setVisibility(View.GONE);
            recyclerMovies.setVisibility(View.VISIBLE);

            ArrayList<MovieModel> movieList = response.body().getResults();
            adapterMovies.setMovieData(movieList);
        }

        @Override
        public void onFailure(Call<DiscoverResponse> call, Throwable t) {
            recyclerMovies.setVisibility(View.GONE);
            pbLoadingMovies.setVisibility(View.GONE);
            tvConnectionProblem.setVisibility(View.VISIBLE);

            Log.d("Error", t.getMessage());
        }
    }
}
