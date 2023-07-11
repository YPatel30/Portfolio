// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.photolibrary.R;
import com.example.photolibrary.adapter.customAdapter;
import com.example.photolibrary.model.Photo;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Photos extends AppCompatActivity {

    private GridView photoList;
    static int chosenPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        chosenPicture = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (Albums.chosenAlbum >=0)
        actionBar.setTitle("" + Albums.arrayList.get(Albums.chosenAlbum).getName());

        actionBar.setDisplayHomeAsUpEnabled(true);

        photoList = findViewById(R.id.photos);

        setImage();
        photoList.setOnItemClickListener((list, text, pos, id) -> indexPosition(pos));
    }





    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.photos_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                Albums.chosenAlbum =-1;
                Intent intent = new Intent(this, Albums.class);
                startActivity(intent);
                return true;

            case R.id.displayPicture:
                display();
                return true;

            case R.id.addPhoto:
                onClick(new View(this));
                return true;

            case R.id.deletePhoto:
                deleteImage();
                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }




    @SuppressLint("WrongConstant")
    protected void onActivityResult(int requestingCode, int resultingCode, Intent data){
        super.onActivityResult(requestingCode, resultingCode, data);

        if (resultingCode == RESULT_OK && data != null){
            Uri URI = data.getData();
            Albums.arrayList.get(Albums.chosenAlbum).addPhoto(new Photo(URI.toString()));
            try {
                writeObject();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        setImage();
    }

    public void onClick(View view){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }

    public void setImage() {
        ArrayList<String> images = new ArrayList<String>();

        for (Photo photo : Albums.arrayList.get(Albums.chosenAlbum).getPhotos()){
            images.add(photo.getImage());
        }
        customAdapter customAdapter = new customAdapter(getApplicationContext(), images);
        photoList.setAdapter(customAdapter);

        if (images.size()==0){
            chosenPicture =-1;
        }
    }


    public void indexPosition(int position){
        chosenPicture = position;
        photoList.setSelected(true);
        photoList.setChoiceMode(position);
    }

    public void display() {
        if (chosenPicture >= 0) {
            Intent intent = new Intent(this, DisplayPhoto.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No photo selected.");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }




    public void deleteImage(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        if (chosenPicture >= 0) {
            alertDialogBuilder.setMessage("Are you sure you want to delete this photo?");
            alertDialogBuilder.setNegativeButton("NO",null);
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Albums.arrayList.get(Albums.chosenAlbum).getPhotos().remove(Photos.chosenPicture);
                    setImage();
                 try {
                    writeObject();
                }
                 catch (Exception e) {
                    e.printStackTrace();
                }
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        else {
            alertDialogBuilder.setMessage("No photo selected.");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    private void writeObject() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("/data/data/com.example.photolibrary/files/data.dat");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(Albums.arrayList);
        objectOutputStream.close();
        fileOutputStream.close();

    }

}

