package org.itai.onibuspti.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class BusTime implements Parcelable {
	
	public static final DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);

	private long id;
	private Date departureTime;
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

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
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
		if (departureTime != null)
			dest.writeLong(departureTime.getTime());
		dest.writeString(bus);
		dest.writeString(type);
		dest.writeString(local);
	}

	private void readFromParcel(Parcel in) {
		id = in.readLong();
		departureTime = new Date(in.readLong());
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
