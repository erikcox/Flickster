/*
 * Copyright (c) 2016 Erik Cox
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package rocks.ecox.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import rocks.ecox.flickster.R;
import rocks.ecox.flickster.models.Movie;

import static rocks.ecox.flickster.R.id.tvOverview;
import static rocks.ecox.flickster.R.id.tvTitle;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    // View lookup cache
    private static class ViewHolder {
        Drawable placeholder;
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for position
        Movie movie = getItem(position);
        // Determine orientation
        int orientation = getContext().getResources().getConfiguration().orientation;
        // Determine the movie rating
        Double rating = movie.getRating();
        final Double highRating = 5.0;
        String layout = "";

        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        viewHolder = new ViewHolder();

        // Choose whether layout should have movie details
        if (orientation == Configuration.ORIENTATION_PORTRAIT && rating >= highRating) {
            convertView = inflater.inflate(R.layout.item_movie_high_rating, parent, false);
            layout = "highRating";
        } else {
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            layout = "lowRating";
        }
        // Find the ImageView
        viewHolder.placeholder = (Drawable) ContextCompat.getDrawable(getContext(), R.drawable.poster_placeholder);
        viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
        viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
        // Cache the viewHolder object inside the fresh view
        convertView.setTag(viewHolder);

        // Set the background to black
        convertView.setBackgroundColor(Color.BLACK);
        // Clear out image from convertView
        viewHolder.ivImage.setImageResource(0);

        // Populate data
        if (layout.equals("lowRating")) {
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT && layout.equals("lowRating")) {
            Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .error(viewHolder.placeholder)
                    .placeholder(viewHolder.placeholder)
                    .into(viewHolder.ivImage);
        } else {
            Picasso.with(getContext())
                    .load(movie.getBackdropPath())
                    .error(viewHolder.placeholder)
                    .placeholder(viewHolder.placeholder)
                    .into(viewHolder.ivImage);
        }

        // Return the view
        return convertView;
    }
}
