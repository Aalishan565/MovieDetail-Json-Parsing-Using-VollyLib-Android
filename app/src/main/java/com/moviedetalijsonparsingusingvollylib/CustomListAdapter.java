package com.moviedetalijsonparsingusingvollylib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by aalishan on 12/10/16.
 */
public class CustomListAdapter extends BaseAdapter {
    private List<MovieModel> mListMovieModel;
    private LayoutInflater inflater;
    ImageLoader imageLoader = VollySingleton.getInstance().getImageLoader();

    public CustomListAdapter(MainActivity mainActivity, List<MovieModel>modelList) {
        mListMovieModel= modelList;
        inflater = LayoutInflater.from(mainActivity);
    }

    @Override
    public int getCount() {
        return mListMovieModel.size();
    }

    @Override
    public Object getItem(int position) {
        return mListMovieModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row, null);
            viewHolder.thumbNail = (NetworkImageView) convertView.findViewById(R.id.imageView);
            viewHolder.tvMovieName = (TextView) convertView.findViewById(R.id.tv_moviename);
            viewHolder.tvTagline = (TextView) convertView.findViewById(R.id.tv_tagline);
            viewHolder.tvYear = (TextView) convertView.findViewById(R.id.tv_year);
            viewHolder.tvDuration = (TextView) convertView.findViewById(R.id.tv_duration);
            viewHolder.tvDirector = (TextView) convertView.findViewById(R.id.tv_director);
            viewHolder.rating = (RatingBar) convertView.findViewById(R.id.ratingbar);
            viewHolder.tvCast = (TextView) convertView.findViewById(R.id.tv_cast);
            viewHolder.tvStory = (TextView) convertView.findViewById(R.id.tv_story);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.thumbNail.setImageUrl(mListMovieModel.get(position).getInage(),imageLoader);
        viewHolder.tvMovieName.setText(mListMovieModel.get(position).getMovie());
        viewHolder.tvTagline.setText(mListMovieModel.get(position).getTagline());
        viewHolder.tvYear.setText("Year :" + mListMovieModel.get(position).getYear());
        viewHolder.tvDuration.setText("Duration :" + mListMovieModel.get(position).getDuration());
        viewHolder.tvDirector.setText("Director :" + mListMovieModel.get(position).getDirector());
        viewHolder.rating.setRating(mListMovieModel.get(position).getRating() / 2);

        StringBuffer sb = new StringBuffer();
        for (MovieModel.Cast cast : mListMovieModel.get(position).getCastList()) {
            sb.append(cast.getName() + ",");

        }
        viewHolder.tvCast.setText("Cast:" + sb);
        viewHolder.tvStory.setText(mListMovieModel.get(position).getStory());
        return convertView;
    }
    class ViewHolder {
       private NetworkImageView thumbNail;
        private TextView tvMovieName;
        private TextView tvTagline;
        private TextView tvYear;
        private TextView tvDuration;
        private TextView tvDirector;
        private RatingBar rating;
        private TextView tvCast;
        private TextView tvStory;

    }
}
