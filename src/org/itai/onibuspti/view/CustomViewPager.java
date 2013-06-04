package org.itai.onibuspti.view;

import java.util.ArrayList;
import java.util.List;

import org.itai.onibuspti.adapter.BusTimePagerAdapter;
import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CustomViewPager extends RelativeLayout 
		implements ViewPager.OnPageChangeListener, View.OnClickListener {
	
	private ViewPager pager;
	private ImageView leftArrow;
	private ImageView rightArrow;
	
	public CustomViewPager(Context context) {
		super(context);
		init();
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		LayoutInflater inflater = 
				(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_view_pager, this, true);
		
		pager = (ViewPager)v.findViewById(R.id.pager);
		pager.setOffscreenPageLimit(3);
		pager.setOnPageChangeListener(this);
		pager.setAdapter(new BusTimePagerAdapter(getContext()));
		
		leftArrow = (ImageView)v.findViewById(R.id.leftArrow);
		rightArrow = (ImageView)v.findViewById(R.id.rightArrow);
		
		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);
		
		showArrows(0);
	}
	
	public void updateData(List<BusTime> data) {
		BusTimePagerAdapter adapter = new BusTimePagerAdapter(getContext());
		adapter.setContent(data);
		
		pager.setCurrentItem(0);
		pager.setAdapter(adapter);
		showArrows(0);
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		List<BusTime> content = ((BusTimePagerAdapter)pager.getAdapter()).getContent();
		
		Bundle bundle = new Bundle();
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		bundle.putParcelableArrayList("data", (ArrayList<BusTime>)content);
		bundle.putInt("currentPage", pager.getCurrentItem());
		
		return bundle;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle)state;
			List<BusTime> data = bundle.getParcelableArrayList("data");
			((BusTimePagerAdapter)pager.getAdapter()).setContent(data);
			
			int currentPosition = bundle.getInt("currentPage");
			pager.setCurrentItem(currentPosition);
			showArrows(currentPosition);
			
			super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
		} 
		else {
			super.onRestoreInstanceState(state);
		}
	}
	
	@Override
	protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
	    super.dispatchFreezeSelfOnly(container);
	}

	@Override
	protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
	    super.dispatchThawSelfOnly(container);
	}
	
	private void showArrows(int newPosition) {
		if (newPosition < pager.getAdapter().getCount()-1) {
			rightArrow.setVisibility(View.VISIBLE);
		} else {
			rightArrow.setVisibility(View.INVISIBLE);
		}
		
		if (newPosition > 0) {
			leftArrow.setVisibility(View.VISIBLE);
		} else {
			leftArrow.setVisibility(View.INVISIBLE);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int newPosition) {
		showArrows(newPosition);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftArrow:
			moveLeft();
			break;
			
		case R.id.rightArrow:
			moveRight();
			break;
		}
	}
	
	private void moveLeft() {
		int currentPosition = pager.getCurrentItem();
		int newPosition = currentPosition;
		
		if (currentPosition > 0) {
			pager.setCurrentItem(--newPosition, true);
			showArrows(newPosition);
		}
	}
	
	private void moveRight() {
		int currentPosition = pager.getCurrentItem();
		int newPosition = currentPosition;
		
		if (currentPosition < pager.getAdapter().getCount()-1) {
			pager.setCurrentItem(++newPosition, true);
			showArrows(newPosition);
		}
	}

}