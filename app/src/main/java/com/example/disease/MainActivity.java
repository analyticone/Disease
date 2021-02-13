package com.example.disease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.disease.models.State;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    // https://corona.lmao.ninja/docs/
    String url = "https://corona.lmao.ninja/v2/states?sort=&yesterday=";

    List<State> states;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        states = new ArrayList<State>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, json.toString());
                Log.d(TAG, json.jsonArray.toString());
                try {
                    states.addAll(State.fromJsonArray(json.jsonArray));
                    Log.d(TAG, "Size: " + states.size());
                    Collections.sort(states, new Comparator<State>() {
                        @Override
                        public int compare(State x, State y) {
                            return y.casesPerOneMillion - x.casesPerOneMillion;
                        }
                    });
                    Log.d(TAG, "Top State: " + states.get(0).state + " " + (states.get(0).casesPerOneMillion/10000)+"%");
                    Log.d(TAG, State.listToString(states));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "Error Response", throwable);
            }
        });
    }
}