package com.example.user.androidlabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    private static final String TAG = "TestToolbar";
    private String message = "I am a new message";
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        v = this.getCurrentFocus();
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mytoolbar);
        Log.i(TAG, "onCreate: toolbar object"+myToolbar);
        setSupportActionBar(myToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        Log.i(TAG, "onOptionsItemSelected: "+mi.getItemId());
        switch (mi.getItemId()) {
            case R.id.action_one:

                Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                showDialog();
                break;
            case R.id.action_three:
//                onCreateDialog();


                // Get the layout inflater
                LayoutInflater inflater = LayoutInflater.from(this);
                final View inflater2 = inflater.inflate(R.layout.dialog_new_message,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(inflater2);
                builder.setTitle("Is this a tug boat, curling rock, or bumper boat?");
                final EditText et1 = (EditText) inflater2.findViewById(R.id.new_message);

                // Add action buttons
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Log.i(TAG, "onCreateDialog: "+findViewById(R.id.new_message));
                        message = et1.getText().toString();
                        Log.i(TAG, "onClick: "+message);
                    }
                })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                Dialog d = builder.create();
                d.show();


                break;


            case R.id.action_about:
                Context ct = getApplicationContext();
                CharSequence text = "Version 1.0, by Joel Schmuland";
                int d2 = Toast.LENGTH_LONG;
                Toast t = Toast.makeText(ct,text,d2);
                t.show();
                break;

        }


        return true;
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to go back?");
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


//    private void onCreateDialog(){
//
//
//    }

}
