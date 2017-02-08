package com.example.user.androidlabs;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import static android.R.id.list;

public class ChatWindow extends AppCompatActivity {
    final ArrayList<String> chatText = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ListView chatListHndl = (ListView) findViewById(R.id.chatListView);

        final EditText chatTextHndl = (EditText) findViewById(R.id.chatEditText);

        //in this case, “this” is the ChatWindow, which is-A Context object
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        chatListHndl.setAdapter (messageAdapter);

        Button chatBtnHndl = (Button) findViewById(R.id.chatBtn);
        chatBtnHndl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempS = chatTextHndl.getText().toString();
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
