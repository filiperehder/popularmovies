package filipe.rehder.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    String overview;
    String title;
    String releaseDate;
    String backdrop;
    String poster;
    Double rating;

    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvMovieTitle) TextView tvMovieTitle;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.tvRating) TextView tvRating;
    @BindView(R.id.ivBackdropMovie) ImageView ivBackdropMovie;
    @BindView(R.id.ivMoviePoster) ImageView ivMoviePoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

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
                .load(getResources().getString(R.string.image_path_300) + backdrop)
                .placeholder(R.drawable.default_backdrop)
                .into(ivBackdropMovie);

        Picasso.get()
                .load(getResources().getString(R.string.image_path_185)  + poster)
                .placeholder(R.drawable.empty_movie)
                .into(ivMoviePoster);
    }
}
