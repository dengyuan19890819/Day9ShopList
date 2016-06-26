package com.example.administrator.day9shoplist.adapter;


import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.day9shoplist.R;
import com.example.administrator.day9shoplist.imageload.LoadPic;
import com.example.administrator.day9shoplist.shop.Shop;

public class MyAdapter extends BaseAdapter {
    private List<Shop> list;

    private Context context;

    public List<Shop> getList() {
        return list;
    }

    public void setList(List<Shop> list) {
        this.list = list;
    }

    public MyAdapter(List<Shop> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {

        return list == null ? 0 : list.size();
    }

    class HoldeConvert {
        // findViewById(R.id.tv_pic);
        ImageView tv_pic;
        TextView tv_title;
        TextView tv_content;
        TextView tv_time;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HoldeConvert holder = null;

        if (convertView == null) {
            Log.d("holder","viewGroup"+parent.toString());
            convertView = LayoutInflater.from(context).inflate(R.layout.view_list_item, parent, false);

            holder = new HoldeConvert();

            Log.d("holder", holder + "");

            holder.tv_pic = (ImageView) convertView.findViewById(R.id.tv_pic);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (HoldeConvert) convertView.getTag();
        }

        Shop shop = getItem(position);
        holder.tv_title.setText(shop.getEventLocation());
        holder.tv_content.setText(shop.getEventDescription());
        holder.tv_time.setText(shop.getActionDate());
        holder.tv_pic.setImageResource(R.mipmap.ic_launcher);

        //���������ܷ������߳���ִ��
    /*		byte[] byimg = HttpUtils.loadBytes(context,shop.getCaterSPhotoUrl(), null);
        Bitmap bm = BitmapFactory.decodeByteArray(byimg, 0, byimg.length);
		holder.tv_pic.setImageBitmap(bm);*/
        //ImageLoad.dispayImage(context,holder.tv_pic, shop.getCaterSPhotoUrl());
        LoadPic.downloadImag(context, shop.getCaterSPhotoUrl(), holder.tv_pic);
        return convertView;
    }

    @Override
    public Shop getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
}
