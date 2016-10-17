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

package rocks.ecox.flickster.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Movie implements Parcelable {

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating() {
        if (rating > 0) {
            return rating;
        } else {
            return 0.0;
        }
    }

    public String getReleaseDate() { return releaseDate; }

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    Double rating;
    String releaseDate;

    public Movie(JSONObject jsonObject) throws JSONException, ParseException{
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.rating = jsonObject.getDouble("vote_average");
        this.releaseDate = formatReleaseDate(jsonObject.getString("release_date"));
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    // Reformats TMDB release date from yyyy-MM-dd to MM-dd-yyyy
    private String formatReleaseDate(String releaseDate) throws ParseException {
        if(releaseDate != null && !releaseDate.equals("")) {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date d = inFormat.parse(releaseDate);
            return outFormat.format(d);
        } else {
            return "Unknown";
        }
    }

    private Movie(Parcel parcel) {
        posterPath = parcel.readString();
        backdropPath = parcel.readString();
        originalTitle = parcel.readString();
        overview = parcel.readString();
        rating = parcel.readDouble();
        releaseDate = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /** Creates a parcel from a Movie object */
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(posterPath);
        parcel.writeString(backdropPath);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
