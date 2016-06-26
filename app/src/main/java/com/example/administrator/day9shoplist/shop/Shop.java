package com.example.administrator.day9shoplist.shop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Shop {
	private String caterSPhotoUrl;// Сͼ��
	private String eventDescription;// ������Ϣ
	private String eventLocation;// ����
	private long actionTime;// ʱ��
	
	

	public Shop() {
		super();
	}

	public Shop(String caterSPhotoUrl, String eventDescription, String eventLocation, long actionTime) {
		super();
		this.caterSPhotoUrl = caterSPhotoUrl;
		this.eventDescription = eventDescription;
		this.eventLocation = eventLocation;
		this.actionTime = actionTime;

	}

	public String getCaterSPhotoUrl() {
		return caterSPhotoUrl;
	}

	public void setCaterSPhotoUrl(String caterSPhotoUrl) {
		this.caterSPhotoUrl = caterSPhotoUrl;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public long getActionTime() {
		return actionTime;
	}
	
	public String getActionDate() {
		return new SimpleDateFormat("HH:mm").format(new Date(actionTime));
	}

	public void setActionTime(long actionTime) {
		this.actionTime = actionTime;
	}

	@Override
	public String toString() {
		return "Shop [caterSPhotoUrl=" + caterSPhotoUrl + ", eventDescription=" + eventDescription + ", eventLocation="
				+ eventLocation + ", actionTime=" + actionTime + "]";
	}

}
