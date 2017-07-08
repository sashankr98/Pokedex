package com.example.sashank.pokedex;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment implements RequestQueue.RequestFinishedListener<JsonObjectRequest>{

    String name;

    TextView idText;
    TextView nameText;
    ImageView sprite;
    TextView height;
    TextView heightValue;
    TextView weight;
    TextView weightValue;
    TextView types;
    TextView typeValues;

    ProgressBar progressBar;

    Communicator communicator;

    private static final String pokeURL = "http://pokeapi.co/api/v2/pokemon/";
    private VolleySingleton volleySingleton;
    private MyPokemon myPokemon;

    private DatabaseHelper databaseHelper;

    public static SearchResultFragment newInstance(String input) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString("Name", input);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            name = getArguments().getString("Name");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        idText = (TextView) view.findViewById(R.id.search_result_id);
        nameText = (TextView) view.findViewById(R.id.search_result_name);
        sprite = (ImageView) view.findViewById(R.id.sprite);
        height = (TextView) view.findViewById(R.id.height);
        heightValue = (TextView) view.findViewById(R.id.height_value);
        weight = (TextView) view.findViewById(R.id.weight);
        weightValue = (TextView) view.findViewById(R.id.weight_value);
        types = (TextView) view.findViewById(R.id.types);
        typeValues = (TextView) view.findViewById(R.id.types_values);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //databaseHelper = DatabaseHelper.getInstance(getContext());

        volleySingleton = VolleySingleton.getInstance();
        RequestQueue requestQueue = volleySingleton.getRequestQueue();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, pokeURL+name, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null && response.length() != 0) {
                    myPokemon = new MyPokemon(response);
                    setViews(myPokemon);
                    //communicator.sendHistory(myPokemon);
//                    long id = databaseHelper.insertRow(myPokemon);
//                    if(id<0)
//                        Toast.makeText(getContext(), "Unable to add search to history", Toast.LENGTH_SHORT);
                }

                else {
                    Toast.makeText(getContext(), "Unable to find " + name, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
        requestQueue.addRequestFinishedListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity temp = (Activity) context;
        communicator = (Communicator) temp;
    }

    private void setViews(MyPokemon data) {
        idText.setText(data.formId());
        nameText.setText(data.getName());

        height.setText(R.string.height);
        double tempHeight = Math.floor(data.getHeight()*10)/10;
        String heightV = Double.toString(tempHeight) + "m";
        heightValue.setText(heightV);

        weight.setText(R.string.weight);
        double tempWeight = Math.floor(data.getWeight()*10)/10;
        String weightV = Double.toString(tempWeight) + "kg";
        weightValue.setText(weightV);

        types.setText(R.string.types);
        ArrayList<String> tempTypes = data.getTypes();
        String typeV = "";
        for(String typeName : tempTypes) {
            typeV += typeName;
            if(tempTypes.size()>1 && tempTypes.indexOf(typeName)!=(tempTypes.size()-1))
                typeV += ", ";
        }
        typeValues.setText(typeV);

        ImageLoader imageLoader = volleySingleton.getImageLoader();
        imageLoader.get(data.getFrontDefaultSprite(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                sprite.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Unable to show sprite", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestFinished(Request<JsonObjectRequest> request) {
        progressBar.setVisibility(View.INVISIBLE);
    }
}
