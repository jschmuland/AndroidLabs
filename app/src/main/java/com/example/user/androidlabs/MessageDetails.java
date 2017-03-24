package com.example.user.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MessageDetails extends AppCompatActivity {
    private static final String TAG = "MessageDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        MessageFragment frag = new MessageFragment();
        Bundle bun = getIntent().getExtras();
        frag.setArguments(bun);
        final long id = bun.getInt("ID");
        getFragmentManager().beginTransaction().add(R.id.activity_message_details, frag).commit();

    }
}
