package org.itai.onibuspti.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itai.onibuspti.R;
import org.itai.onibuspti.dao.BusTimeDao;
import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.view.CustomViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockFragment;

public class TimeNextFragment extends SherlockFragment {
	
	private CustomViewPager pager1;
	private CustomViewPager pager2;
	
	private OnClickListener listener = new OnClickListener() {	
		@Override
		public void onClick(View view) {
			updateTime();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_time_next, container, false);
		
		v.findViewById(R.id.update_btn).setOnClickListener(listener);
		
		pager1 = (CustomViewPager)v.findViewById(R.id.pager1);
		pager2 = (CustomViewPager)v.findViewById(R.id.pager2);
		
		if (savedInstanceState == null) {
			updateTime();
		}

		return v;
	}

	
	private void updateTime() {
		pager1.updateData(updateTime2("barreira"));
		pager2.updateData(updateTime2("pti"));
	}
	
	private List<BusTime> updateTime2(String local) {
		List<BusTime> times = new ArrayList<BusTime>();
		
		Date currentTime = new Date(System.currentTimeMillis());
		
		BusTimeDao dao = new BusTimeDao(getActivity());
		times = dao.queryNext(3, currentTime, local);
		
		if (times.size() < 3) {
			times.add(null);
		}
		
		return times;
	}

}
