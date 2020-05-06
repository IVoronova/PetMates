package com.example.login;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class friendlist_adapter extends ArrayAdapter<String> {

    ArrayList<String>email = new ArrayList<>();
    ArrayList<String>name = new ArrayList<>();
    ArrayList<Bitmap>image = new ArrayList<>();
    Context mContext;

    public friendlist_adapter(@NonNull Context context, ArrayList<String> friendemail,ArrayList<String> friendname,
                              ArrayList<Bitmap> friendimage ) {
        super(context, R.layout.activity_friend_list);
        this.email = friendemail;
        this.name = friendname;
        this.image = friendimage;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return email.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewHolder = new Viewholder();
        if(convertView ==null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.activity_friend_list, parent, false);
            viewHolder.mFlag = (ImageView) convertView.findViewById(R.id.friendicon);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.friendName);
            viewHolder.mEmail = (TextView) convertView.findViewById(R.id.friendEmail);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (Viewholder)convertView.getTag();
        }

            viewHolder.mFlag.setImageBitmap(image.get(position));
            viewHolder.mName.setText(name.get(position));
            viewHolder.mEmail.setText(email.get(position));

        return convertView;
    }
    static class Viewholder{
        ImageView mFlag;
        TextView mName;
        TextView mEmail;
    }
}
