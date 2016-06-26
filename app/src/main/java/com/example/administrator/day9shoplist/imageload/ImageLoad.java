package com.example.administrator.day9shoplist.imageload;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.administrator.day9shoplist.httpUtils.HttpUtils;

public class ImageLoad {

	public static void dispayImage(final Context context, final ImageView iv, final String imageUrl) {
		new AsyncTask<Void, Void, Bitmap>() {

			protected void onPostExecute(Bitmap result) {
				iv.setImageBitmap(result);
			};

			@Override
			protected Bitmap doInBackground(Void... params) {
				byte[] loadByte = HttpUtils.loadBytes(context, imageUrl, null);
				// ��ʼ����ͼƬ������ʾ
				return BitmapFactory.decodeByteArray(loadByte, 0, loadByte.length);
			}
		}.execute();
	}

}
