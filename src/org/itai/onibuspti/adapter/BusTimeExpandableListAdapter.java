package org.itai.onibuspti.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itai.onibuspti.R;
import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.util.DateUtils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class BusTimeExpandableListAdapter extends BaseExpandableListAdapter {
	
	private List<String> groups;
	private Map<String, List<BusTime>> map;
	private Activity context;
	
	private static class ViewHolder {
		public TextView text;
	}
	
	public BusTimeExpandableListAdapter(Activity context, List<BusTime> values) {
		this.context = context;
		groups = new ArrayList<String>();
		map = new HashMap<String, List<BusTime>>();
		
		for (BusTime busTime: values) {
			List<BusTime> list = map.get(busTime.getType());
			if (list == null) {
				list = new ArrayList<BusTime>();
				groups.add(busTime.getType());
				map.put(busTime.getType(), list);
			}
			list.add(busTime);
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return map.get(groups.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return map.get(groups.get(groupPosition)).get(childPosition).getId();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, 
			View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.time_row, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) convertView.findViewById(R.id.list_item);
			convertView.setTag(viewHolder);
		}
		
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		BusTime busTime = map.get(groups.get(groupPosition)).get(childPosition);
		
		viewHolder.text.setText(DateUtils.formatTime(busTime.getDepartureTime()));
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return map.get(groups.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, 
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
				.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView)convertView.findViewById(android.R.id.text1);
			convertView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder)convertView.getTag();
		
		String text;
		if (groups.get(groupPosition).equals("sabado"))
			text = "Sábado";
		else text = "Segunda a sexta";
			
		holder.text.setText(text);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

}