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

import java.util.ArrayList;

public class friendrequest_adapter extends ArrayAdapter<String> {
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String>name = new ArrayList<>();
    ArrayList<Bitmap>image = new ArrayList<>();
    Context mContext;

    public friendrequest_adapter(@NonNull Context context, ArrayList<String> friendemail, ArrayList<String> friendname,
                                 ArrayList<Bitmap> friendimage ) {
        super(context, R.layout.friend_request_list);
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
        friendrequest_adapter.Viewholder viewHolder = new friendrequest_adapter.Viewholder();
        if(convertView ==null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.friend_request_list, parent, false);
            viewHolder.mFlag = (ImageView) convertView.findViewById(R.id.friendicon1);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.friendName1);
            viewHolder.mEmail = (TextView) convertView.findViewById(R.id.friendEmail1);
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


