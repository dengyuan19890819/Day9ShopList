package com.example.administrator.day9shoplist.imageload;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.administrator.day9shoplist.httpUtils.HttpUtils;

public class DownLoadPic {
	public static void downloadImag(final Context context,final String imageUrl,final ImageView iv_pic){
		new AsyncTask<Void, String, Bitmap>() {

			@Override
			protected Bitmap doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				byte[] byteImage= HttpUtils.loadBytes(context, imageUrl, null);
				
				
				return BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
			}
			
			protected void onPostExecute(Bitmap result) {
				
				iv_pic.setImageBitmap(result);
			};
			
			
			
		}.execute();
	}
}
