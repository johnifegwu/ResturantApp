package com.mickleentityltdnigeria.resturantapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SampleFolderActivity extends AppCompatActivity implements IFolderItemListener {

    FolderLayout localFolders;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        localFolders = (FolderLayout)findViewById(R.id.localfolders);
        localFolders.setIFolderItemListener(this);
        localFolders.setDir("./sys");//change directory if u want,default is root

    }

    //Your stuff here for Cannot open Folder
    public void OnCannotFileRead(File file) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_folder_24)
                .setTitle(
                        "[" + file.getName()
                                + "] folder can't be read!")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {


                            }
                        }).show();

    }


    //Your stuff here for file Click
    public void OnFileClicked(File file) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_folder_24)
                .setTitle("[" + file.getName() + "]")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {


                            }

                        }).show();
    }

}