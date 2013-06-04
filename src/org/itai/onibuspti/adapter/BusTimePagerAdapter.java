package org.itai.onibuspti.adapter;

import java.util.ArrayList;
import java.util.List;

import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BusTimePagerAdapter extends PagerAdapter {

	private Context context;
	private List<BusTime> content;

	public BusTimePagerAdapter(Context context) {
		super();
		this.context = context;
		content = new ArrayList<BusTime>();
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position) {
		LayoutInflater inflater = 
				(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.page_layout, null);
		
		String text;
		if (content.get(position) != null && content.get(position).getDepartureTime() != null)
			text = BusTime.dateFormat.format(content.get(position).getDepartureTime());
		else
			text = "-";
		
		TextView textView = (TextView)v.findViewById(R.id.textView1);
		textView.setText(text);

		collection.addView(v, 0);

		return v;
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object view) {
		container.removeView((View) view);
	}
	
	public void setContent(List<BusTime> content) {
		this.content = content;
		notifyDataSetChanged();
	}
	
	public List<BusTime> getContent() {
		return content;
	}

	public void addItem(BusTime item) {
		content.add(item);
		notifyDataSetChanged();
	}
	
	public void removeItem(int position) {
		content.remove(position);
		notifyDataSetChanged();
	}
	
}
