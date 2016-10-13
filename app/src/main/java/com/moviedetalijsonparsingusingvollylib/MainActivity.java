package com.moviedetalijsonparsingusingvollylib;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mLvData;
    RequestQueue requestQueue;

    ProgressDialog progressDialog;
    private String urlforList = "http://jsonparsing.parseapp.com/jsonData/moviesData.txt";
    private String TAG = "MainActivity";
    private MovieModel mMovieModel;
    private List<MovieModel> mListMovieModel;
    private CustomListAdapter mCustomListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListMovieModel=new ArrayList<>();
        requestQueue = VollySingleton.getInstance().getRequestQueue();
        progressDialog = new ProgressDialog(MainActivity.this);
        mLvData = (ListView) findViewById(R.id.listView);
        callListDataToLoad();
    }

    private void callListDataToLoad() {
        progressDialog.setMessage("loading");
        progressDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlforList, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject jsonObj = new JSONObject(response.toString());
                            JSONArray jsonArray = jsonObj.getJSONArray("movies");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonFinalObject = jsonArray.getJSONObject(i);
                                MovieModel movieModel = new MovieModel();
                                movieModel.setMovie(jsonFinalObject.getString("movie"));
                                movieModel.setYear(jsonFinalObject.getInt("year"));
                                movieModel.setRating((float) jsonFinalObject.getDouble("rating"));
                                movieModel.setDirector(jsonFinalObject.getString("director"));
                                movieModel.setDuration(jsonFinalObject.getString("duration"));
                                movieModel.setTagline(jsonFinalObject.getString("tagline"));
                                movieModel.setInage(jsonFinalObject.getString("image"));
                                movieModel.setStory(jsonFinalObject.getString("story"));
                                List<MovieModel.Cast> castList = new ArrayList<>();
                                for (int j = 0; j < jsonFinalObject.getJSONArray("cast").length(); j++) {
                                    JSONObject castObject = jsonFinalObject.getJSONArray("cast").getJSONObject(j);
                                    MovieModel.Cast cast = new MovieModel.Cast();
                                    cast.setName(castObject.getString("name"));
                                    castList.add(cast);
                                }
                                movieModel.setCastList(castList);
                                mListMovieModel.add(movieModel);
                            }
                            mCustomListAdapter = new CustomListAdapter(MainActivity.this, mListMovieModel);
                            mLvData.setAdapter(mCustomListAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                progressDialog.hide();
            }
        });
        requestQueue.add(jsonObjReq);
    }
}
