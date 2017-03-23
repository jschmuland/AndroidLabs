package com.example.user.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ListItemsActivity", "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        ImageButton iButton = (ImageButton) findViewById(R.id.imageButton);
        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            dispatchTakePictureIntent();

            }
        });

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text = isChecked? getString(R.string.switchon):getString(R.string.switchoff);
                int duration = isChecked?Toast.LENGTH_SHORT: Toast.LENGTH_LONG;

                Context context = getApplicationContext();
                Toast toast =  Toast.makeText(context , text, duration); //this is the ListActivity
                toast.show(); //display your message box

            }
        });

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
// 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message)
                        .setCancelable(false)

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response", "My information to share");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();

            }
        });
    }

    final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageButton iButton = (ImageButton) findViewById(R.id.imageButton);
            iButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onResume() {
        Log.i("ListItemsActivity", "In onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("ListItemsActivity", "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("ListItemsActivity", "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("ListItemsActivity", "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("ListItemsActivity", "In onDestroy()");
        super.onDestroy();
    }



}
