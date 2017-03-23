package com.example.user.androidlabs;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    final ArrayList<String> chatText = new ArrayList<>();
    protected ChatDatabaseHelper dbHelper;
    private boolean inTabletLayout;
    private static final String TAG = "ChatWindow";
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        if (findViewById(R.id.chatFrameLayout)==null){
            inTabletLayout = false;
            Log.i(TAG, "onCreate: phone layout");
        }else{
            inTabletLayout = true;
            Log.i(TAG, "onCreate: Tablet layout");
        }


                dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

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
                Log.i("ChatDatabaseHelper","Row ID: "+results.getString(
                        results.getColumnIndex(ChatDatabaseHelper.ID_COLUMN)
                )+" SQL MESSAGE: "+ results.getString(
                results.getColumnIndex(ChatDatabaseHelper.MESSAGE_COLUMN)));
                results.moveToNext();
            }


            results.moveToFirst();
            for (int i = 0;i<results.getColumnCount();i++){
                Log.i(TAG,"SQL Column Rows: "+ results.getColumnName(i));
            }


        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        theList.setAdapter (messageAdapter);

        //creating a on item listener for list view
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long ll) {
                final long l = ll+1;
                Log.d(TAG, "onItemClick: " + i + " " + l);

                 Cursor result = db.query(false,ChatDatabaseHelper.TABLE_NAME,
                         new String[]{ChatDatabaseHelper.ID_COLUMN,ChatDatabaseHelper.MESSAGE_COLUMN},
                         ChatDatabaseHelper.ID_COLUMN+"=?",new String[]{String.valueOf(l)},
                         null,null,null,null);
                result.moveToFirst();
                String message = result.getString(results.getColumnIndex(ChatDatabaseHelper.MESSAGE_COLUMN));
                Log.i(TAG, "onItemClick: "+message);
                Bundle bun = new Bundle();
                bun.putLong("ID", l );//l is the database ID of selected item
                bun.putString("MESSAGE", message);


                //step 2, if a tablet, insert fragment into FrameLayout, pass data
                if(inTabletLayout) {
                    MessageFragment frag = new MessageFragment();

                    frag.setArguments(bun);

                    getFragmentManager().beginTransaction().replace(R.id.chatFrameLayout, frag).commit();

                }
                //step 3 if a phone, transition to empty Activity that has FrameLayout
                else //isPhone
                {
                    Intent intnt = new Intent(ChatWindow.this, MessageDetails.class);
                    intnt.putExtra("ID" , l); //pass the Database ID to next activity
                    intnt.putExtra("MESSAGE",message);
                    startActivityForResult(intnt,10); //go to view fragment details
                }
            }
        });


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 10 && resultCode == RESULT_OK){
            long id = data.getLongExtra("ID",0);
            Log.i(TAG, "onActivityResult: id = "+id);
            db.delete(ChatDatabaseHelper.TABLE_NAME,ChatDatabaseHelper.ID_COLUMN+"=?",
                    new String[]{String.valueOf(id)});

            Intent refresh = new Intent(this, ChatWindow.class);
            startActivity(refresh);
            this.finish();
        }


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

        public long getItemID(int position){return position;}

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
