package com.example.plantify;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Homefragment extends Fragment implements View.OnClickListener{


 Button button;
 Button button_croplist_garden_plant;
 ImageView imagecard,imageView,i1,i2,i3,i4,i5,i6;

 CardView scard1,scard2,scard3,scard4,scard5,scard6,scard7,scard8,scard9,scard10;

/** public CardView c1,c2,c3,c4,c5,c6;**/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homefragment,
                container, false);



        Button button = (Button) view.findViewById(R.id.button);
        ImageView imagecard=(ImageView) view.findViewById(R.id.imagecard) ;
        Button button_croplist_garden_plant=(Button) view.findViewById(R.id.button_croplist_garden_plant) ;
        ImageView i1=(ImageView) view.findViewById(R.id.i1);
        ImageView i2=(ImageView) view.findViewById(R.id.i2);
        ImageView i3=(ImageView) view.findViewById(R.id.i3);
        ImageView i4=(ImageView) view.findViewById(R.id.i4);
        ImageView i5=(ImageView) view.findViewById(R.id.i5);
        ImageView i6=(ImageView) view.findViewById(R.id.i6);

        scard1=view.findViewById(R.id.scard1);
        scard2=view.findViewById(R.id.scard2);
        scard3=view.findViewById(R.id.scard3);
        scard4=view.findViewById(R.id.scard4);
        scard5=view.findViewById(R.id.scard5);
        scard6=view.findViewById(R.id.scard6);
        scard7=view.findViewById(R.id.scard7);
        scard8=view.findViewById(R.id.scard8);
        scard9=view.findViewById(R.id.scard9);
        scard10=view.findViewById(R.id.scard10);


        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),x_potato.class);
                startActivity(intent);

            }

        });

        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),x_potato.class);
                startActivity(intent);

            }

        });

        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),x_potato.class);
                startActivity(intent);

            }

        });

        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),x_potato.class);
                startActivity(intent);

            }

        });

        scard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),x_potato.class);
                startActivity(intent);

            }

        });



       /** c1=(CardView) getView().findViewById(R.id.c1);
        c2=(CardView) getView().findViewById(R.id.c2);
        c3=(CardView) getView().findViewById(R.id.c3);
        c4=(CardView) getView().findViewById(R.id.c4);
        c5=(CardView) getView().findViewById(R.id.c5);
        c6=(CardView) getView().findViewById(R.id.c6);
       // potplant=(CardView) getActivity().findViewById(R.id.potplant);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
      //  potplant.setOnClickListener(this);
**/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openactivity();
            }
        });

        button_croplist_garden_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        return view;
    }
    private void openactivity(){
        Intent intent=new Intent(this.getActivity(),CroplistActivity.class);
        startActivity(intent);
    }

    private void openActivity2(){
        Intent intent=new Intent(this.getActivity(),Croplist_gardenActivity.class);
        startActivity(intent);
    }
    private void openActivity3(){
        Intent intent=new Intent(this.getActivity(),available_soon.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {



     /**   Intent intent;
        switch (v.getId()) {
            case R.id.c1:
                intent = new Intent(this.getActivity(), available_soon.class);
                startActivity(intent);
                break;

            case R.id.c2:
                intent = new Intent(this.getActivity(), available_soon.class);
                startActivity(intent);
                break;

            case R.id.c3:
                intent = new Intent(this.getActivity(), available_soon.class);
                startActivity(intent);
                break;

            case R.id.c4:
                intent = new Intent(this.getActivity(), available_soon.class);
                startActivity(intent);
                break;

            case R.id.c5:
                intent = new Intent(this.getActivity(), available_soon.class);
                startActivity(intent);
                break;

            case R.id.c6:
                intent = new Intent(this.getActivity(), available_soon.class);
                startActivity(intent);
                break;

        }**/
        /** @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        /** Button add = (Button) getActivity().findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        openactivity();
        }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homefragment, container, false);

        }
         **/

   }



}