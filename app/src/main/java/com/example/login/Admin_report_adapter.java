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

public class Admin_report_adapter extends ArrayAdapter<String> {
    ArrayList<String> id = new ArrayList<>();
    ArrayList<Bitmap>images = new ArrayList<>();
    ArrayList<Bitmap>imager = new ArrayList<>();
    ArrayList<String>emails = new ArrayList<>();
    ArrayList<String>emailr = new ArrayList<>();
    ArrayList<String>reason = new ArrayList<>();
    Context mContext;

    public Admin_report_adapter(@NonNull Context context, ArrayList<String> ID, ArrayList<Bitmap> imageS,ArrayList<Bitmap> imageR, ArrayList<String> emails,
                                ArrayList<String> emailr, ArrayList<String> Reason ) {
        super(context, R.layout.report_item);
        this.id = ID;
        this.images = imageS;
        this.imager = imageR;
        this.emails = emails;
        this.emailr = emailr;
        this.reason = Reason;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return emails.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Admin_report_adapter.Viewholder viewHolder = new Admin_report_adapter.Viewholder();
        if(convertView ==null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.report_item, parent, false);
            viewHolder.mID = (TextView) convertView.findViewById(R.id.reportnumber);
            viewHolder.mImages = (ImageView) convertView.findViewById(R.id.reportemail);
            viewHolder.mIngesr = (ImageView) convertView.findViewById(R.id.beenreport);
            viewHolder.mEmails = (TextView) convertView.findViewById(R.id.senderemail);
            viewHolder.mEmailr = (TextView) convertView.findViewById(R.id.receivedemail);
            viewHolder.mReason = (TextView) convertView.findViewById(R.id.reportreason);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (Viewholder)convertView.getTag();
        }
        viewHolder.mID.setText(id.get(position));
        viewHolder.mImages.setImageBitmap(images.get(position));
        viewHolder.mIngesr.setImageBitmap(imager.get(position));
        viewHolder.mEmails.setText(emails.get(position));
        viewHolder.mEmailr.setText(emailr.get(position));
        viewHolder.mReason.setText(reason.get(position));

        return convertView;
    }
    static class Viewholder{
        TextView mID;
        ImageView mImages;
        ImageView mIngesr;
        TextView mEmails;
        TextView mEmailr;
        TextView mReason;
    }
}


