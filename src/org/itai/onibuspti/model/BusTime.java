package org.itai.onibuspti.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BusTime implements Parcelable {

	private long id;
	private String departureTime;
	private String bus;
	private String type;
	private String local;

	public BusTime() {}

	public BusTime(Parcel in) {
		readFromParcel(in);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getBus() {
		return bus;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(departureTime);
		dest.writeString(bus);
		dest.writeString(type);
		dest.writeString(local);
	}

	private void readFromParcel(Parcel in) {
		id = in.readLong();
		departureTime = in.readString();
		bus = in.readString();
		type = in.readString();
		local = in.readString();
	}

	public static final Parcelable.Creator<BusTime> CREATOR = 
			new Parcelable.Creator<BusTime>() {

		@Override
		public BusTime createFromParcel(Parcel source) {
			return new BusTime(source);
		}

		@Override
		public BusTime[] newArray(int size) {
			return new BusTime[size];
		}
	};

}