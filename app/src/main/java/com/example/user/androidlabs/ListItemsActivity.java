package com.example.user.androidlabs;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ListItemsActivity", "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
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
