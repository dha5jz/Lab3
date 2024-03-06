package com.example.lab3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {
    private ArrayList<Contact> data;
    private ArrayList<Contact> databackup;

    //ngữ cảnh
    private Activity context;

    //đối tượng phân tích layout
    private LayoutInflater inflater;
    public Adapter(){}
    public Adapter(ArrayList<Contact> data, Activity activity){
        this.data = data;
        this.context = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null)
            v = inflater.inflate(R.layout.contactitem,null);
        ImageView imageView = v.findViewById(R.id.item_img);
        TextView tvName = v.findViewById(R.id.item_tvName);
        TextView tvPhone = v.findViewById(R.id.item_tvPhone);

        tvName.setText(data.get(position).getName());
        tvPhone.setText(data.get(position).getPhone());
        CheckBox checkBox = v.findViewById(R.id.item_checkbox);
        checkBox.setChecked(data.get(position).getStatus());
        String pathImg = data.get(position).getImages();
        if(pathImg != null && !pathImg.isEmpty()){
            Glide.with(context)
                    .load(pathImg)
                    .into(imageView);
        } else{
            // Nếu đường dẫn ảnh rỗng, bạn có thể đặt ảnh mặc định cho ImageView ở đây
            // image.setImageResource(R.drawable.default_image);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setStatus(checkBox.isChecked());
            }
        });
        return  v;

    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
