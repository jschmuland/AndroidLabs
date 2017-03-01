package com.example.user.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import static android.R.id.list;

public class ChatWindow extends AppCompatActivity {
    final ArrayList<String> chatText = new ArrayList<>();
    protected ChatDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        dbHelper = new ChatDatabaseHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        ListView theList = (ListView) findViewById(R.id.chatListView);
        final EditText chatTextHndl = (EditText) findViewById(R.id.chatEditText);

        final Cursor results = db.query(false, ChatDatabaseHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.ID_COLUMN, ChatDatabaseHelper.MESSAGE_COLUMN},
                null,null,null,null,null,null);
        int rows = results.getCount();//number of rows
        results.moveToFirst();

            Log.i("ChatDatabaseHelper", "onCreate: Cursor row count ="+results.getCount());
            while(!results.isAfterLast()){
                chatText.add(results.getString(results.getColumnIndex(ChatDatabaseHelper.MESSAGE_COLUMN)));
                Log.i("ChatDatabaseHelper","SQL MESSAGE: "+ results.getString(
                results.getColumnIndex(ChatDatabaseHelper.MESSAGE_COLUMN)));
                results.moveToNext();
            }

            Log.i("ChatDatabaseHelper","Cusor's column count =" + results.getColumnCount());
            Log.i("ChatDatabaseHelper", "onCreate: Cursor row count ="+results.getCount());

            results.moveToFirst();
            for (int i = 0;i<results.getColumnCount();i++){
                Log.i(ACTIVITY_SERVICE,"SQL Column Rows: "+ results.getColumnName(i));
            }


        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        theList.setAdapter (messageAdapter);

        Button chatBtnHndl = (Button) findViewById(R.id.chatBtn);
        chatBtnHndl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String tempS = chatTextHndl.getText().toString();

                ContentValues newValues = new ContentValues();
                newValues.put(ChatDatabaseHelper.MESSAGE_COLUMN,tempS);
                db.insert(ChatDatabaseHelper.TABLE_NAME,"",newValues);
                chatText.add(tempS);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView();

                chatTextHndl.setText("");

            }
        });




    }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy(){
        dbHelper.close();
        super.onDestroy();
    }

    private class ChatAdapter extends ArrayAdapter<String>{

        private ChatAdapter(Context context) {
            super(context, 0);
        }
        @Override
        public int getCount(){
            return chatText.size();
        }

        @Override
        public String getItem(int position){
            return chatText.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result;
            if(position%2==0){
                result = inflater.inflate(R.layout.chat_row_incoming,null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing,null);
            }
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position)); //get the string at position

            return result;
        }
    }
}
