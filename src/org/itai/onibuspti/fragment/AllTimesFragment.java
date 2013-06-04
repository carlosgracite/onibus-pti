package org.itai.onibuspti.fragment;

import java.util.List;

import org.itai.onibuspti.adapter.BusTimeExpandableListAdapter;
import org.itai.onibuspti.model.BusTime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;

public class AllTimesFragment extends SherlockFragment {
	
	private List<BusTime> values;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		values = getArguments().getParcelableArrayList("values");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(android.R.layout.expandable_list_content, 
				container, false);
		
		ExpandableListView listView = (ExpandableListView)view.findViewById(android.R.id.list);
		listView.setAdapter(new BusTimeExpandableListAdapter(getActivity(), values));
		
		return view;
	}

}
