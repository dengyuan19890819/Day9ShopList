package com.example.administrator.day9shoplist.httpUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.day9shoplist.shop.Shop;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtils {

	public interface onProgressCallBack {

		void onLoading(int progress);

		boolean isDisconnect();
	}

	public static byte[] loadBytes(Context context, String urlsr,
			onProgressCallBack mOnProgressCallBack) {
		
		// �ж�����
		if (!isNetwork(context)) {

			return null;
		}

		InputStream is;
		ByteArrayOutputStream baos = null;

		try {
			URL url = new URL(urlsr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			int ctime = con.getReadTimeout();
			int code = con.getResponseCode();
			if (ctime == 5000) {
				System.out.println("���ӳ�ʱ");
			}
			if (code == HttpURLConnection.HTTP_OK) {
				int max = con.getContentLength();
				int sum = 0;
				is = con.getInputStream();
				baos = new ByteArrayOutputStream();
				int r;
				byte[] b = new byte[1024];

				while ((r = is.read(b)) != -1) {
					Thread.sleep(50);
					baos.write(b, 0, r);
					sum += r;
					if (mOnProgressCallBack != null
							&& mOnProgressCallBack.isDisconnect()) {
						return null;
					}

					// publishProgress((int) (sum * 100.0 / max));
					if (mOnProgressCallBack != null) {
						mOnProgressCallBack
								.onLoading((int) (sum * 100.0 / max));
					}

				}

				baos.flush();
				baos.close();
				is.close();
				con.disconnect();
			}

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		return baos.toByteArray();
	}

	public static List<Shop> jsonPaser(String jsonStr) {
		List<Shop> list = new ArrayList<>();

		try {
			JSONObject root = new JSONObject(jsonStr);
			int code = root.optInt("code");
			if (code == 99999) {
				JSONObject data = root.optJSONObject("data");
				JSONArray resultsArray = data.optJSONArray("results");

				for (int i = 0; i < resultsArray.length(); i++) {
					JSONObject resultObj = resultsArray.optJSONObject(i);

					String caterSPhotoUrl = resultObj
							.optString("caterSPhotoUrl");
					String eventDescription = resultObj
							.optString("eventDescription");
					String eventLocation = resultObj.optString("eventLocation");
					long actionTime = resultObj.optLong("actionTime");

					list.add(new Shop(caterSPhotoUrl, eventDescription,
							eventLocation, actionTime));
				}
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		return list;
	}

	public static boolean isNetwork(Context context) {
		// �õ�ϵͳ�ķ������������
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// �õ���ǰ�ֻ��������Ϣ
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null) {
			return info.isConnected();
		}
		return false;
	}

}
