package com.example.disease.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class State {
    public String state;
    int cases;
    public int casesPerOneMillion;
    double casesPercent;

    public static final String TAG = "State";

    public State(JSONObject jsonObject) throws JSONException {
        state = jsonObject.getString("state");
        cases = jsonObject.getInt("cases");
        casesPerOneMillion = jsonObject.getInt("casesPerOneMillion");
        casesPercent = casesPerOneMillion/10000.0;
        Log.d(TAG, toString());
    }

    @Override
    public String toString() {
        return "State{" +
                "state='" + state + '\'' +
                ", cases=" + cases +
                ", casesPerOneMillion=" + casesPerOneMillion +
                ", casesPercent=" + String.format("%.2f%%", casesPercent) +
                '}';
    }

    public static String listToString(List<State> states)
    {
        String result = "\n";
        for (State s : states)
            result += s.toString() +"\n";
        return result;
    }

    public static List<State> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<State> states = new ArrayList<State>();

        for (int i=0; i<jsonArray.length(); i++)
            states.add(new State(jsonArray.getJSONObject(i)));

        return states;
    }
}