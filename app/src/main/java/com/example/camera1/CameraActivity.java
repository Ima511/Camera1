package com.example.camera1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    Button _Capture;
    private String imgPath;
    private Object ContentResolver;
    Uri imageUri;
    private Object Camera;
    private int CAMERA;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        _Capture = (Button) findViewById(R.id._Capture);
        imageView = (ImageView) findViewById(R.id.imageView);

       _Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

             //   startActivity(cameraIntent);

                Uri imagePath = createImage();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imagePath);
                startActivityForResult(cameraIntent, CAMERA);

               // setImageUri(cameraIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CAMERA){
        if(resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "Image captured Successfully", Toast.LENGTH_SHORT).show();
            imageView.setImageURI(imageUri);
        }

    }



    }
//    private Uri setImageUri(Intent cameraIntent) {
//        ContextWrapper cw = new ContextWrapper(getApplicationContext());
//        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//        File file = new File(directory,System.currentTimeMillis() + ".png");
//        Uri imgUri = Uri.fromFile(file);
//        this.imgPath = file.getAbsolutePath();
//        return imgUri;
//    }

    private Uri createImage(){
        Uri uri = null;
        ContentResolver = getContentResolver();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            uri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }else{

            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        }

        String imgName = String.valueOf(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imgName + ".jpg");
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/");
        Uri finalUri = getContentResolver().insert(uri, contentValues);
        imageUri = finalUri;
        return  finalUri;

    }


}