package com.example.plantify;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Disease_solution extends AppCompatActivity {
TextView textviewset,diseaseDescription,textviewSolution;
ImageView setimg1,setimg2,setimg3,setimg4;


String dis;


String diseasepreicted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_solution);



        textviewset=(TextView) findViewById(R.id.textviewset);
        diseaseDescription=(TextView)findViewById(R.id.diseaseDescription);
        textviewSolution=(TextView)findViewById(R.id.textviewSolution);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            diseasepreicted = extras.getString("key");

            //The key argument here must match that used in the other activity
            dis=diseasepreicted;

        }
        setimg1=findViewById(R.id.setimg1);
        setimg2=findViewById(R.id.setimg2);
        setimg3=findViewById(R.id.setimg3);
        setimg4=findViewById(R.id.setimg4);

        /**
         setimg1.setImageResource(R.drawable.);
         setimg2.setImageResource(R.drawable.);
         setimg3.setImageResource(R.drawable.);
         setimg4.setImageResource(R.drawable.);
         **/


        //potato


        if(dis.equals("Disease : Potato Early Blight")){

            textviewset.setText(dis);
            diseaseDescription.setText("Potato early blight disease is caused by the fungal pathogen Alternaria solani.\n" +
                    "\n" +
                    "The disease leads to small, dark, and sunken lesions on potato leaves, stems, and tubers.\n" +
                    "\n" +
                    "These lesions can merge and cause significant damage to the plant, reducing yield.\n" +
                    "\n" +
                    "The fungus thrives in warm, humid conditions and can spread through contaminated soil and plant material.\n" +
                    "\n" +
                    "Early detection and management are essential to prevent economic losses in potato production.");


            textviewSolution.setText("Cultural practices, such as crop rotation, removing infected plant parts, and use of resistant potato varieties, can help manage early blight disease.\n" +
                    "\n" +
                    "Fungicides can also be effective in controlling the disease, but their effectiveness may decrease over time.\n" +
                    "\n" +
                    "Integrated pest management strategies, which combine cultural practices, resistant varieties, and judicious use of fungicides, can provide effective prevention and management of early blight disease.\n" +
                    "\n" +
                    "Regular monitoring of potato crops for symptoms of the disease can aid in early detection and timely intervention.\n" +
                    "\n" +
                    "Overall, the goal is to minimize the impact of the disease on potato production through a combination of preventive measures and management strategies.");

            setimg1.setImageResource(R.drawable.z_peb_a);
            setimg2.setImageResource(R.drawable.z_peb_b);
            setimg3.setImageResource(R.drawable.z_peb_c);
            setimg4.setImageResource(R.drawable.z_peb_d);
        }

        else if (dis.equals( "Disease : Potato Late blight")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_potatolb_a);
            setimg2.setImageResource(R.drawable.z_potatolb_b);
            setimg3.setImageResource(R.drawable.z_potatolb_c);
            setimg4.setImageResource(R.drawable.z_potatolb_d);
        }

        //tomato


        else if (dis.equals("Disease : Tomato Bacterial spot")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.strawberry_bg);

        }
        else if (dis.equals( "Disease : Tomato Early blight")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.strawberry_bg);
        }

        else if (dis.equals("Disease :  Tomato Late blight")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.strawberry_bg);

        }

        else if (dis.equals( "Disease : Tomato Leaf_Mold")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.strawberry_bg);
        }

        //pepperbell

        else if (dis.equals("Disease : Pepper_Bell Bacterial spot")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_peb_a);
            setimg2.setImageResource(R.drawable.z_peb_b);
            setimg3.setImageResource(R.drawable.z_peb_c);
            setimg4.setImageResource(R.drawable.z_peb_d);
        }


        //corn


        else if (dis.equals("Disease : Cercospora leaf spot Gray leaf spot ")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_corn_gray_leaf_spot_d);
            setimg2.setImageResource(R.drawable.z_corn_gray_leaf_spot_a);
            setimg3.setImageResource(R.drawable.z_corn_gray_leaf_spot_b);
            setimg4.setImageResource(R.drawable.z_corn_gray_leaf_spot_c);

        }
        else if (dis.equals( "Disease : Common rust")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_corn_common_rust_a);
            setimg2.setImageResource(R.drawable.z_corn_common_rust_b);
            setimg3.setImageResource(R.drawable.z_corn_common_rust_c);
            setimg4.setImageResource(R.drawable.z_corn_common_rust_d);

        }
        else if (dis.equals("Disease : Northern_Leaf_Blight")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_corn_northern_leaf_blight_a);
            setimg2.setImageResource(R.drawable.z_corn_northern_leaf_blight_b);
            setimg3.setImageResource(R.drawable.z_corn_northern_leaf_blight_c);
            setimg4.setImageResource(R.drawable.z_corn_northern_leaf_blight_d);

        }

        //cherry


        else if (dis.equals("Disease: Cherry Powdery mildew")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_cherry_powdery_midew_a);
            setimg2.setImageResource(R.drawable.z_cherry_powdery_midew_b);
            setimg3.setImageResource(R.drawable.z_cherry_powdery_midew_c);
            setimg4.setImageResource(R.drawable.z_cherry_powdery_midew_d);

        }


        //grapes

        else if (dis.equals("Disease : Grape Black rot")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_grape_black_rot_a);
            setimg2.setImageResource(R.drawable.z_grape_black_rot_b);
            setimg3.setImageResource(R.drawable.z_grape_black_rot_c);
            setimg4.setImageResource(R.drawable.z_grape_black_rot_d);


        }
        else if (dis.equals("Disease : Grape Esca (Black_Measles)")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_grape_esca_bm_a);
            setimg2.setImageResource(R.drawable.z_grape_esca_bm_b);
            setimg3.setImageResource(R.drawable.z_grape_esca_bm_c);
            setimg4.setImageResource(R.drawable.z_grape_esca_bm_d);
        }

        else if (dis.equals("Grape Leaf blight (Isariopsis Leaf Spot)")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_grape_leaf_blight_a);
            setimg2.setImageResource(R.drawable.z_grape_leaf_blight_b);
            setimg3.setImageResource(R.drawable.z_grape_leaf_blight_c);
            setimg4.setImageResource(R.drawable.z_grape_leaf_blight_d);
        }



        //peach
        else if (dis.equals("Disease : Peach Bacterial spot")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_peach_bacterial_spot_a);
            setimg2.setImageResource(R.drawable.z_peach_bacterial_spot_b);
            setimg3.setImageResource(R.drawable.z_peach_bacterial_spot_c);
            setimg4.setImageResource(R.drawable.z_peach_bacterial_spot_d);
        }



        //apple


        else if (dis.equals("Disease : Apple_scab")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_appleas_a);
            setimg2.setImageResource(R.drawable.z_appleas_c);
            setimg3.setImageResource(R.drawable.z_pplas_b);
            setimg4.setImageResource(R.drawable.z_appleas_d);

        }
        else if (dis.equals("Disease : Black_rot")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_apple_black_rot_a);
            setimg2.setImageResource(R.drawable.z_apple_black_rot_b);
            setimg3.setImageResource(R.drawable.z_apple_black_rot_c);
            setimg4.setImageResource(R.drawable.z_apple_black_rot_d);
        }

        else if (dis.equals("Disease : Cedar Apple rust")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_apple_cr_a);
            setimg2.setImageResource(R.drawable.z_apple_cr_b);
            setimg3.setImageResource(R.drawable.z_apple_cr_c);
            setimg4.setImageResource(R.drawable.z_apple_cr_d);
        }

        //strawberry

        else if (dis.equals("Disease : Strawberry Leaf scorch")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_strawberry_leaf_scorch_a);
            setimg2.setImageResource(R.drawable.z_strawberry_leaf_scorch_b);
            setimg3.setImageResource(R.drawable.z_strawberry_leaf_scorch_c);
            setimg4.setImageResource(R.drawable.z_strawberry_leaf_scorch_d);
        }

        //cotton

        else if (dis.equals("Disease : Bacterial blight")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_cotton_bacterial_blight_a);
            setimg2.setImageResource(R.drawable.z_cotton_bacterial_blight_b);
            setimg3.setImageResource(R.drawable.z_cotton_bacterial_blight_c);
            setimg4.setImageResource(R.drawable.z_cotton_bacterial_blight_d);
        }
        else if (dis.equals("Disease : curl virus")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_cotton_curl_virus_a);
            setimg2.setImageResource(R.drawable.z_cotton_curl_virus_b);
            setimg3.setImageResource(R.drawable.z_cotton_curl_virus_c);
            setimg4.setImageResource(R.drawable.z_cotton_curl_virus_d);
        }

        else if (dis.equals("Disease : Fussarium wilt")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_cotton_fussarium_wilt_a);
            setimg2.setImageResource(R.drawable.z_cotton_fussarium_wilt_b);
            setimg3.setImageResource(R.drawable.z_cotton_fussarium_wilt_c);
            setimg4.setImageResource(R.drawable.z_cotton_fussarium_wilt_d);
        }

        //rose

        else if (dis.equals("Disease : Black Spot")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_rose_black_spot_a);
            setimg2.setImageResource(R.drawable.z_rose_black_spot_b);
            setimg3.setImageResource(R.drawable.z_rose_black_spot_c);
            setimg4.setImageResource(R.drawable.z_rose_black_spot_d);
        }

        else if (dis.equals("Disease : Downy Mildew")) {
            textviewset.setText(dis);
            setimg1.setImageResource(R.drawable.z_rose_downy_mildew_a);
            setimg2.setImageResource(R.drawable.z_rose_downy_mildew_b);
            setimg3.setImageResource(R.drawable.z_rose_downy_mildew_c);
            setimg4.setImageResource(R.drawable.z_rose_downy_mildew_d);        }


        else{
            textviewset.setText("no disease selected");
        }

    }
}