package com.example.user.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by user on 3/22/2017.
 */

public class MessageFragment extends Fragment {
    private final static String TAG= "MessageFragment";
    long id;
    String message;
    Button deleteBtn;
    Context parent;
//    protected ChatDatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    Bundle bun = getArguments();

        id = bun.getLong("ID");
        message = bun.getString("MESSAGE");
        Log.i(TAG, "onCreate: "+bun+id+message);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "onCreateView: ");
        View gui = inflater.inflate(R.layout.content_message_details, null);

        TextView msgText = (TextView) gui.findViewById(R.id.fragment_message);
        msgText.setText(message);

        TextView idText = (TextView)gui.findViewById(R.id.idNumber);
        idText.setText("Message ID = "+id);


        deleteBtn = (Button) gui.findViewById(R.id.deleteMsgBtn);
        deleteBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "onClick: Delete button"+id);
                        Intent data = new Intent();
                        data.putExtra("ID",id);


                        getActivity().setResult(Activity.RESULT_OK,data);
//                        getActivity().onBackPressed();
//                        getActivity().getFragmentManager().beginTransaction().detach(getParentFragment());
//                        getActivity().onActivityReenter(15,data);

                    }
                }
        );
//        deleteBtn.setOnClickListener();
//        TextView tv = (TextView)gui.findViewById(R.id.tv);
//        tv.setText("You clicked on ID:" + id);
        return gui;
    }
}
