// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.photolibrary.R;
import com.example.photolibrary.adapter.customAdapter;
import com.example.photolibrary.model.Photo;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    GridView gridView;

    ArrayList<String> image = new ArrayList<String>();

    Button searchButton;

    Spinner tagNames;
    Spinner tagNames1;

    RadioButton andRadioButton;
    RadioButton orRadioButton;

    EditText searchInput;
    EditText searchInput2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        image.clear();

        tagNames = findViewById(R.id.spinner);
        tagNames1 = findViewById(R.id.spinner2);

        ArrayAdapter<String> nameAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.tagNames));

        tagNames.setAdapter(nameAdapter);
        tagNames1.setAdapter(nameAdapter);

        andRadioButton = findViewById(R.id.and);
        orRadioButton = findViewById(R.id.or);

        searchInput = findViewById(R.id.firstTagValue);
        searchInput2 = findViewById(R.id.secondTagValue);

        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                searchImages();
            }
        });

        gridView = findViewById(R.id.imageGrid);

    }

    public void setPictures() {
        customAdapter customAdapter = new customAdapter(getApplicationContext(), image);
        gridView.setAdapter(customAdapter);
    }

    public void searchImages(){
        image.clear();
        ArrayList<Photo> results = new ArrayList<Photo>();
        int b = 0;

        if (searchInput.getText().length() <= 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Enter a search term");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            for (int i = 0; i < Albums.arrayList.size(); i++ ) {
                if (b==1) {
                    break;
                }
                for (int j = 0; j < Albums.arrayList.get(i).getPhotos().size(); j++) {

                    if (searchInput2.getText().length() <= 0){
                        searchInput2.setText("");
                    }
                    for (int k = 0; k < Albums.arrayList.get(i).getPhotos().get(j).getTags().size(); k++) {
                        if (orRadioButton.isChecked() || (!andRadioButton.isChecked() && !orRadioButton.isChecked())) {
                            if (searchInput.getText().toString().toLowerCase().equalsIgnoreCase(Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue()) ||
                                    searchInput2.getText().toString().equalsIgnoreCase(Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue())) {
                                results.add(Albums.arrayList.get(i).getPhotos().get(j));
                                break;
                            }
                            else if (Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue().toLowerCase().startsWith(searchInput.getText().toString().toLowerCase()) ||
                                    Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue().toLowerCase().startsWith(searchInput2.getText().toString().toLowerCase())){
                                results.add(Albums.arrayList.get(i).getPhotos().get(j));
                                break;
                            }

                        }

                        else if (andRadioButton.isChecked()) {
                            if (searchInput.getText().toString().equalsIgnoreCase(Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue())) {
                                for (int l = 0; l < Albums.arrayList.get(i).getPhotos().get(j).getTags().size(); l++ ) {
                                    if (searchInput2.getText().toString().equalsIgnoreCase(Albums.arrayList.get(i).getPhotos().get(j).getTags().get(l).getValue())) {
                                        results.add(Albums.arrayList.get(i).getPhotos().get(j));
                                        break;
                                    }
                                }
                            }
                            else if (Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue().toLowerCase().startsWith(searchInput.getText().toString().toLowerCase())) {
                                for (int l = 0; l < Albums.arrayList.get(i).getPhotos().get(j).getTags().size(); l++ ) {
                                    if (Albums.arrayList.get(i).getPhotos().get(j).getTags().get(k).getValue().toLowerCase().startsWith(searchInput2.getText().toString().toLowerCase())) {
                                        results.add(Albums.arrayList.get(i).getPhotos().get(j));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (Photo p : results){
                image.add(p.getImage());
            }
            setPictures();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Albums.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(menuItem);

    }

}