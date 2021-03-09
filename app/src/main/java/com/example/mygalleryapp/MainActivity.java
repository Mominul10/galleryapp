package com.example.mygalleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdaptar customAdaptar;
    List<String>myList;
    ArrayList<File>myimagefile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recylerview);
        CheckUserPermsions();
        myList = new ArrayList<>();

    }
    void CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        display();  // init the contact list

    }




    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    display();  // init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText(this, "your message", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private ArrayList<File> findimage(File file) {
        ArrayList< File > imageList = new ArrayList<>();

       File[] imageFile = file.listFiles();

       for ( File singleImage : imageFile){


           if (singleImage.isDirectory() && !singleImage.isHidden()) {
               imageList.addAll(findimage(singleImage));
           } else {
               if (singleImage.getName().endsWith(".jpg") ||
               singleImage.getName().endsWith(".jpg" ))
               {

                    imageList.add(singleImage);
               }

           }

       }


        return imageList;
     }
    private void display() {

        myimagefile = findimage(Environment.getExternalStorageDirectory());

        for(int j=0 ; j < myimagefile.size(); j++){
            myList.add(String.valueOf(myimagefile.get(j)));
            customAdaptar = new CustomAdaptar(myList);
            recyclerView.setAdapter(customAdaptar);
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        }

    }



}