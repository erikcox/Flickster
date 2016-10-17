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

package rocks.ecox.flickster;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rocks.ecox.flickster.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = this.getIntent();
        // Change the ActionBar title
        getSupportActionBar().setTitle(R.string.title_movie_details);

        // Populate the ImageView and TextViews from the parcelable
        TextView title = (TextView) findViewById(R.id.tvTitle);
        ImageView poster = (ImageView) findViewById(R.id.ivPoster);
        Drawable placeholder = (Drawable) ContextCompat.getDrawable(this, R.drawable.poster_placeholder_backdrop);
        TextView releaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        RatingBar rating = (RatingBar) findViewById(R.id.rbRating);
        TextView overview = (TextView) findViewById(R.id.tvOverviewDetail);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        if (intent != null && intent.hasExtra("movie")) {
            Movie movie = intent.getExtras().getParcelable("movie");

            Picasso.with(getApplicationContext())
                    .load(movie.getBackdropPath())
                    .error(placeholder)
                    .placeholder(placeholder)
                    .into(poster);

            title.setText(movie.getOriginalTitle());
            releaseDate.setText(movie.getReleaseDate());
            rating.setRating(movie.getRating().intValue());
            overview.setText(movie.getOverview());
        }
    }
}
