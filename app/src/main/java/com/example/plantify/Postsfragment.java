package com.example.plantify;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Postsfragment extends Fragment {

   /** @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_postsfragment, container, false);
    }**/

   //Button button3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_postsfragment,
                container, false);
        //Button button = (Button) view.findViewById(R.id.button3);


       /** button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity();
            }
        });**/

        return view;
    }
    private void openactivity(){
        Intent intent=new Intent(this.getActivity(), x_potato.class);
        startActivity(intent);
    }
}
