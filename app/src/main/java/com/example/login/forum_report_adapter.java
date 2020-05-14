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

public class forum_report_adapter extends ArrayAdapter<String> {

    ArrayList<String>id = new ArrayList<>();
    ArrayList<String>section = new ArrayList<>();
    ArrayList<String>title = new ArrayList<>();
    ArrayList<String>content = new ArrayList<>();
    ArrayList<Bitmap>image = new ArrayList<>();
    ArrayList<String>reason = new ArrayList<>();
    Context mContext;

    public forum_report_adapter(@NonNull Context context, ArrayList<String> ID,ArrayList<String> SECTION,ArrayList<String> TITLE,
                                ArrayList<String> CONTENT,ArrayList<Bitmap> IMAGE, ArrayList<String> REASON) {
        super(context, R.layout.reportpost_item);
        this.id = ID;
        this.section = SECTION;
        this.title = TITLE;
        this.content = CONTENT;
        this.image = IMAGE;
        this.reason = REASON;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Viewholder viewHolder = new Viewholder();
        if(convertView ==null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.reportpost_item, parent, false);
            viewHolder.mId = (TextView) convertView.findViewById(R.id.postid);
            viewHolder.mSection = (TextView) convertView.findViewById(R.id.forumsection);
            viewHolder.mTitle = (TextView) convertView.findViewById(R.id.forumstitle1);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.forumcontent);
            viewHolder.mImage = (ImageView) convertView.findViewById(R.id.forumimage);
            viewHolder.mReason = (TextView) convertView.findViewById(R.id.reportreason1);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (Viewholder)convertView.getTag();
        }

        viewHolder.mId.setText(id.get(position));
        viewHolder.mSection.setText(section.get(position));
        viewHolder.mTitle.setText(title.get(position));
        viewHolder.mContent.setText(content.get(position));
        viewHolder.mImage.setImageBitmap(image.get(position));
        viewHolder.mReason.setText(reason.get(position));

        return convertView;
    }
    static class Viewholder{
        TextView mId;
        TextView mSection;
        TextView mTitle;
        TextView mContent;
        ImageView mImage;
        TextView mReason;
    }
}
