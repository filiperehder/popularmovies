package filipe.rehder.popularmovies.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import filipe.rehder.popularmovies.R;
import filipe.rehder.popularmovies.models.MovieModel;

class MoviesViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private ImageView ivMoviePoster;

    MoviesViewHolder(View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tvTitle);
        ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
    }

    void bind(final MovieModel item, final OnItemClickListener listener) {
        tvTitle.setText(item.getTitle());
        Picasso.get().load("http://image.tmdb.org/t/p/w185/" + item.getPosterPath()).into(ivMoviePoster);

        Log.d("Backdrop",  item.getBackdropPath());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}
