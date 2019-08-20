package com.example.rkg09.domesticservicesfreelancing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rkg09 on 3/7/2019.
 */
    public class ChatListAdapter1 extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mArrSchoolData, id;
        private JobsActivity jobs;
        String customer_id, users;
        Button btnAction;

        public ChatListAdapter1(Context context, ArrayList arrSchoolData, ArrayList key, String user) {
            super();
            mContext = context;
            mArrSchoolData = arrSchoolData;
            id = key;
            users = user;
        }

        public int getCount() {
            // return the number of records
            return mArrSchoolData.size();
        }

        // getView method is called for each item of ListView
        public View getView(final int position, View view, ViewGroup parent) {
            // inflate the layout for each item of listView
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_chat, parent, false);


            // get the reference of textView and button
             TextView txtSchoolTitle = (TextView) view.findViewById(R.id.title);
            ImageView imageView = (ImageView) view.findViewById(R.id.img);

            txtSchoolTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = (int) getItem(position);
                    send_message(i);
                }
            });

            // Set the title and button name
            txtSchoolTitle.setText(mArrSchoolData.get(position));


            return view;
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        private  void  send_message(final  int position){
            Intent intent = new Intent(mContext,MessageActivity1.class);
            //Toast.makeText(mContext,""+id.get(position),Toast.LENGTH_LONG).show();
            intent.putExtra("Id",""+id.get(position));
            intent.putExtra("User",""+users);
            mContext.startActivity(intent);
        }

    }

