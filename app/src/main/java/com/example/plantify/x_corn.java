package com.example.plantify;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantify.ml.CornTflite;
import com.example.plantify.ml.PotatoTflite;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class x_corn extends AppCompatActivity{
    // Button camera, gallery;
    ImageView imageView;
    TextView result;
    Integer flag;
    String diseasepreicted;

    int imageSize = 256;

    public CardView c1,c2,c3;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcorn);


        c1=(CardView) findViewById(R.id.c1);
        c2=(CardView) findViewById(R.id.c2);
        c3=(CardView) findViewById(R.id.c3);

        flag=0;
        //camera = findViewById(R.id.button);
        // gallery = findViewById(R.id.button2);

        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView_profile_dp);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });



        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==1){
                    opensolution();
                }

                else if(flag==0){
                    Toast.makeText(x_corn.this, "Please Detect the disease to see solutions", Toast.LENGTH_LONG).show();
                }
                else if(flag==3){
                    Toast.makeText(x_corn.this, "Your Plant is Healty", Toast.LENGTH_SHORT).show();
                }

            }



        });

    }
    public void classifyImage(Bitmap image) {

        try {
            CornTflite model = CornTflite.newInstance(getApplicationContext());
            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
            //bytebuffer contain pixel values from our image
            //float takes 4 bytes,img size ,img size because we have hat much pixel,3 for RGB value size
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            CornTflite.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();


            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Disease : Cercospora leaf spot Gray leaf spot ", "Disease : Common rust","Disease : Northern_Leaf_Blight","Your plant is Healthy"};
            result.setText(classes[maxPos]);
            diseasepreicted=classes[maxPos];
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    /** try {
     Model model = Model.newInstance(getApplicationContext());

     // Creates inputs for reference.
     TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
     ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
     byteBuffer.order(ByteOrder.nativeOrder());

     int[] intValues = new int[imageSize * imageSize];
     image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
     int pixel = 0;
     //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
     for(int i = 0; i < imageSize; i ++){
     for(int j = 0; j < imageSize; j++){
     int val = intValues[pixel++]; // RGB
     byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
     byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
     byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
     }
     }
     */
    /** inputFeature0.loadBuffer(byteBuffer);

     // Runs model inference and gets result.
     Model.Outputs outputs = model.process(inputFeature0);
     TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

     float[] confidences = outputFeature0.getFloatArray();
     // find the index of the class with the biggest confidence.
     int maxPos = 0;
     float maxConfidence = 0;
     for (int i = 0; i < confidences.length; i++) {
     if (confidences[i] > maxConfidence) {
     maxConfidence = confidences[i];
     maxPos = i;
     }
     }
     String[] classes = {"Apple", "Banana", "Orange"};
     result.setText(classes[maxPos]);

     // Releases model resources if no longer used.
     model.close();
     } catch (IOException e) {
     // TODO Handle the exception
     }
     }**/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == 3){
                Bitmap image = (Bitmap) data.getExtras().get("data");

                //resize bitmap image...because we have model to depict image in square

                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);


                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
            // Universal Resource Identifier
            //In the URI, the file path may be empty. A Uniform Resource Locator (URL), or web address,
            // is the most common form of URI. It is used for unambiguously identifying and locating websites or other web-connected resources.

            else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
            if(diseasepreicted.equals("Your plant is Healthy")){
                flag=3;
            }
            else
            {
                flag=1;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void opensolution(){



        String id="1";
        Intent intent=new Intent(this,Disease_solution.class);
        intent.putExtra("key",diseasepreicted);
        startActivity(intent);
    }
}