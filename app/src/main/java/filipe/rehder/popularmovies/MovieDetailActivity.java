package filipe.rehder.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    String overview;
    String title;
    String releaseDate;
    String backdrop;
    String poster;
    Double rating;

    TextView tvOverview;
    TextView tvMovieTitle;
    TextView tvReleaseDate;
    TextView tvRating;
    ImageView ivBackdropMovie;
    ImageView ivMoviePoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        tvOverview = findViewById(R.id.tvOverview);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvRating = findViewById(R.id.tvRating);
        ivBackdropMovie = findViewById(R.id.ivBackdropMovie);
        ivMoviePoster = findViewById(R.id.ivMoviePoster);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);

        overview = getIntent().getExtras().getString("overview");
        title = getIntent().getExtras().getString("title");
        releaseDate = getIntent().getExtras().getString("releaseDate");
        backdrop = getIntent().getExtras().getString("backdrop");
        poster = getIntent().getExtras().getString("poster");
        rating = getIntent().getExtras().getDouble("rating");

        tvOverview.setText(overview);
        tvMovieTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvRating.setText(rating.toString());

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w300/" + backdrop)
                .placeholder(R.drawable.default_backdrop)
                .into(ivBackdropMovie);

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + poster)
                .placeholder(R.drawable.empty_movie)
                .into(ivMoviePoster);
    }
}
