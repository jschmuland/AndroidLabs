package com.example.user.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("StartActivity", "In onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent,5);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5){
            Log.i("ActivityResult","Returned to StartActivity.onActivity");
        }
        if (resultCode == Activity.RESULT_OK){
            String messagePassed = "ListItemsAvtivity passed: "+data.getStringExtra("Response");
            int duration = Toast.LENGTH_LONG;

            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context,messagePassed,duration);
            toast.show();
        }
    }

    @Override
    protected void onResume() {
        Log.i("StartActivity", "In onResume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i("StartActivity", "In onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.i("StartActivity", "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("StartActivity", "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("StartActivity", "In onDestroy()");
        super.onDestroy();
    }
}
