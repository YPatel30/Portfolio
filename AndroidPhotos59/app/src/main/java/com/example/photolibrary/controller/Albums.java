// Yash Patel(ykp15)
// Om Shah (os207)

package com.example.photolibrary.controller;

import com.example.photolibrary.R;
import com.example.photolibrary.model.Album;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Albums extends AppCompatActivity {

    private ListView listView;
    static ArrayAdapter<String> arrayAdapter;
    public static ArrayList<Album> arrayList = new ArrayList<Album>();
    public static int chosenAlbum = -1;

    static int i = -1;

    @Override
    protected void onCreate(Bundle stateOfSavedInstance) {
        super.onCreate(stateOfSavedInstance);
        setContentView(R.layout.albums);

        if (i ==-1){
            try {
               readApp();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
            catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        listView = findViewById(R.id.albums);

        ArrayList<String> albums = new ArrayList<String>();

        for (Album al : arrayList) {
            albums.add(al.getName());
        }

        arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_list, R.id.textView, albums);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((list,text,pos,id) -> indexAl(pos));

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.addAlbum:
                AddAlbum(this);
                return true;

            case R.id.deleteAlbum:
                delete();
                return true;

            case R.id.openAlbum:
                showPhoto();
                return true;

            case R.id.renameAlbum:
                rename(this);
                return true;

            case R.id.search:
                startActivity(new Intent(this, Search.class));
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.albums_menu,menu);
        return true;
    }



    public void indexAl(int position){
        chosenAlbum = position;
        listView.setSelected(true);
        listView.setChoiceMode(position);
    }

    private void AddAlbum(Context context) {
        final EditText taskEditText = new EditText(context);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Add Album")
                .setMessage("Enter a new album name")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String task = String.valueOf(taskEditText.getText());

                        if (task.length() > 0) {
                            arrayList.add(new Album(task.trim()));
                            arrayAdapter.add(task);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        try {
            writeObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPhoto() {
        if (chosenAlbum >= 0) {
            Intent intent = new Intent(this, Photos.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("No album selected.");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void delete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        if (chosenAlbum >= 0) {
            alertDialogBuilder.setMessage("Are you sure you want to delete this album?");
            alertDialogBuilder.setNegativeButton("NO",null);
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Albums.arrayList.remove(Albums.chosenAlbum);
                    setListAdapter();
                    try {
                        writeObject();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        else {
            alertDialogBuilder.setMessage("No album selected.");
            alertDialogBuilder.setNegativeButton("OK",null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }




    public void setListAdapter() {
        ArrayList<String> albums = new ArrayList<String>();
        for (Album al : arrayList) {
            albums.add(al.getName());
        }
        arrayAdapter = new ArrayAdapter<>(this, R.layout.custom_list, R.id.textView, albums);
        listView.setAdapter(arrayAdapter);
    }

    public void rename(Context context) {
        if (chosenAlbum >= 0) {
            final EditText editTextTask = new EditText(context);
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Rename Album")
                    .setMessage("Enter an album name")
                    .setView(editTextTask)
                    .setPositiveButton("Rename", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(editTextTask.getText());

                            if (task.length() > 0) {
                                Albums.arrayList.get(Albums.chosenAlbum).setName(task);
                            }
                            try {
                                writeObject();
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }

                            setListAdapter();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }

        else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setMessage("No album selected.");
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

    private void readApp()
            throws IOException, ClassNotFoundException {
        File myFile = new File("/data/data/com.example.photolibrary/files/data.dat");

        if (myFile.length()>0) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(myFile));
            ArrayList<Album> users = (ArrayList<Album>)ois.readObject();
            arrayList = users;
            ois.close();
        }
    }


}
