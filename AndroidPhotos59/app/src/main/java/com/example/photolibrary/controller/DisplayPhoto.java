// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.photolibrary.R;
import com.example.photolibrary.model.Album;
import com.example.photolibrary.model.Tag;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DisplayPhoto extends AppCompatActivity {

    ImageView imageView;
    Toolbar myToolbar;

    Button previousButton;
    Button nextButton;
    Button addTagButton;
    Button deleteTagButton;

    private ListView tags;
    static ArrayAdapter<String> adapter;

    private int indexTag = -1;

    @Override
    protected void onCreate(Bundle stateOfSavedInstance) {
        super.onCreate(stateOfSavedInstance);
        setContentView(R.layout.display_photo);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        previousButton = findViewById(R.id.previous);
        previousButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                previous();
            }
        });

        nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                next();
            }
        });

        addTagButton = findViewById(R.id.addTagButton);
        addTagButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                addTag();
            }
        });
        deleteTagButton = findViewById(R.id.deleteTagButton);
        deleteTagButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                deleteTag();
            }
        });

        String URI = Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getImage();
        imageView = findViewById(R.id.displaypic);
        imageView.setImageURI(Uri.parse(URI));

        tags = findViewById(R.id.tags);

        ArrayList<String> photoTags = new ArrayList<String>();
        for (Tag t : Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags()) {
            photoTags.add(t.toString());
        }
        adapter = new ArrayAdapter<>(this, R.layout.custom_list, R.id.textView, photoTags);
        tags.setAdapter(adapter);

        try {
            writeObject();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        tags.setOnItemClickListener((list,text,pos,id) -> indexOfTag(pos));
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.display_menu,menu);
        return true;
    }

    public void indexOfTag(int position){
        indexTag = position;
        tags.setSelected(true);
        tags.setChoiceMode(position);
    }




    public void deletePhoto(){
        if (Photos.chosenPicture >= 0) {
            Albums.arrayList.get(Albums.chosenAlbum).getPhotos().remove(Photos.chosenPicture);
        }

        if (Albums.arrayList.get(Albums.chosenAlbum).getPhotos().size() == 0){
            Intent intent = new Intent(this, Photos.class);
            startActivity(intent);
        }

        else
          next();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Photos.class);
                startActivity(intent);
                return true;

            case R.id.move:
                move();
                return true;

            case R.id.deletePhoto:
                deletePhoto();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void move() {
        AlertDialog.Builder tags = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.move_dialog,null);
        tags.setTitle("Move to album...");

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner3);

        ArrayList<String> albums = new ArrayList<String>();

        for (Album a : Albums.arrayList) {
            albums.add(a.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, albums);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tags.setPositiveButton("Move", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                int a = spinner.getSelectedItemPosition();
                Albums.arrayList.get(a).getPhotos().add(Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture));
                Albums.arrayList.get(Albums.chosenAlbum).getPhotos().remove(Photos.chosenPicture);

                try {
                    writeObject();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                next();
            }
        });

        tags.setNegativeButton("Cancel",null);
        tags.setView(view);
        AlertDialog dialog = tags.create();
        dialog.show();
    }



    public void next(){
        Photos.chosenPicture++;

        if (Photos.chosenPicture >= Albums.arrayList.get(Albums.chosenAlbum).getPhotos().size()) {
            if (Albums.arrayList.get(Albums.chosenAlbum).getPhotos().size() == 0) {
                Intent intent = new Intent(this, Photos.class);
                startActivity(intent);
            }
            else {
                Photos.chosenPicture = 0;
            }
        }
        displayInformation();
    }

    public void displayInformation(){
        String URI = Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getImage();
        imageView.setImageURI(Uri.parse(URI));

        ArrayList<String> photoTags = new ArrayList<String>();

        for (Tag t : Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags()) {
            photoTags.add(t.toString());
        }

        adapter = new ArrayAdapter<>(this, R.layout.custom_list, R.id.textView, photoTags);
        tags.setAdapter(adapter);

    }

    public void previous(){
        Photos.chosenPicture--;

        if (Photos.chosenPicture < 0) {
            if (Albums.arrayList.get(Albums.chosenAlbum).getPhotos().size() == 0) {
                Intent intent = new Intent(this, Photos.class);
                startActivity(intent);
            }
            else {
                Photos.chosenPicture = Albums.arrayList.get(Albums.chosenAlbum).getPhotos().size() - 1;
            }
        }
        displayInformation();
    }

    public void addTag(){
        AlertDialog.Builder tag = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.tag_dialog,null);
        tag.setTitle("Add a tag");

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner3);

        EditText editText = view.findViewById(R.id.textd);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, getResources().getStringArray(R.array.tagNames));
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        tag.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean isEmpty = false;
                boolean isLocationRepeated = false;
                boolean isTagRepeated = false;

                String tagValue = editText.getText().toString();

                if (tagValue.length()<=0){
                    isEmpty = true;
                }

                for (Tag t: Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags()) {
                    if (t.getName().equals(spinner.getSelectedItem().toString()) && t.getValue().equals(tagValue.toLowerCase())){
                        isTagRepeated = true;
                    }

                    if (t.getName().equals("location") && spinner.getSelectedItem().toString().equals("location")) {
                        isLocationRepeated = true;
                    }
                }

                if (!isTagRepeated && !isEmpty && spinner.getSelectedItemPosition() == 0) {
                    Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags().add(new Tag("person",tagValue));
                }

                else if (!isLocationRepeated && !isTagRepeated && !isEmpty && spinner.getSelectedItemPosition()==1) {
                    Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags().add(new Tag("location",tagValue));
                }

                errorTags(isEmpty, isLocationRepeated, isTagRepeated, spinner.getSelectedItem().toString());
            }
        });

        tag.setNegativeButton("Cancel",null);
        tag.setView(view);
        AlertDialog dialog = tag.create();
        dialog.show();

    }

    public void deleteTag(){
        if (indexTag >= 0) {
            Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags().remove(indexTag);
        }
        ArrayList<String> photoTags = new ArrayList<String>();

        for (Tag t : Albums.arrayList.get(Albums.chosenAlbum).getPhotos().get(Photos.chosenPicture).getTags()) {
            photoTags.add(t.toString());
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.activity_main, photoTags);
        tags.setAdapter(adapter);

        try {
            writeObject();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void errorTags(boolean empty, boolean one, boolean check, String n) {
        if (empty) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Must enter a tag value.");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        else if(check) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("This tag already exists.");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else if (one) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Cannot have more than one of this tagName: " + n);
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else {
            displayInformation();
            try {
                writeObject();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
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