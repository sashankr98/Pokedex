package com.example.sashank.pokedex;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainFragment extends Fragment implements View.OnClickListener{

    EditText searchText;
    Button searchButton;

    Communicator communicator;
    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity temp = (Activity) context;
        communicator = (Communicator) temp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        searchText = (EditText) view.findViewById(R.id.search_text);

        searchButton = (Button) view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String input = searchText.getText().toString();
        communicator.startResultFragment(input);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
