package filipe.rehder.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import filipe.rehder.popularmovies.R;
import filipe.rehder.popularmovies.models.MovieModel;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    private ArrayList<MovieModel> movieList;
    private final OnItemClickListener mClickHandler;

    public MoviesAdapter(OnItemClickListener clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(movieList.get(position), mClickHandler);
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public void setMovieData(ArrayList movies) {
        movieList = movies;
        notifyDataSetChanged();
    }
}
