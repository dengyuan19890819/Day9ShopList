package com.example.administrator.day9shoplist.imageload;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class LoadPic {
	public static void downloadImag(final Context context,final String imageUrl,final ImageView iv_pic){
		new AsyncTask<Void, String, Drawable>() {

			@Override
			protected Drawable doInBackground(Void... params) {
				// TODO Auto-generated method stub
				Log.d("print", imageUrl);
			/*	
				byte[] byteImage=HttpUtils.loadBytes(context, imageUrl, null);*/
				Drawable drawable = null;
				try {
					drawable=Drawable.createFromStream(new URL(imageUrl).openStream(),"src");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				return drawable;
			}
			
			protected void onPostExecute(Drawable result) {
				
				iv_pic.setImageDrawable(result);
			};
			
			
			
		}.execute();
	}
}
