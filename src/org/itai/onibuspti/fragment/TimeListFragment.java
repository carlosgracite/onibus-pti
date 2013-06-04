package org.itai.onibuspti.fragment;

import java.util.ArrayList;
import java.util.List;

import org.itai.onibuspti.R;
import org.itai.onibuspti.dao.BusTimeDao;
import org.itai.onibuspti.model.BusTime;
import org.itai.onibuspti.view.FragmentTabHost;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class TimeListFragment extends SherlockFragment {

	private static final String TAB_BARREIRA_ID = "1";
	private static final String TAB_PTI_ID = "2";

	private FragmentTabHost tabHost;
	
	private List<BusTime> valuesPTI;
	private List<BusTime> valuesBarreira;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BusTimeDao dao = new BusTimeDao(getActivity());
		valuesPTI = dao.queryByLocal(BusTimeDao.LOCAL_PTI);
		valuesBarreira = dao.queryByLocal(BusTimeDao.LOCAL_BARREIRA);
	}

	@Override	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);

		final View view = inflater.inflate(R.layout.fragment_time_list, container, false);
		configureTabs(view);

		return tabHost;
	}

	private void configureTabs(final View view) {
		tabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
		tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
		
		// Constrói a tab Barreira-PTI
		Bundle bundle1 = new Bundle();
		bundle1.putParcelableArrayList("values", (ArrayList<BusTime>)valuesBarreira);

		View tabView1 = createTabView(getActivity(), "Barreira - PTI");
		tabHost.addTab(tabHost.newTabSpec(TAB_BARREIRA_ID).setIndicator(tabView1),
				AllTimesFragment.class, bundle1);

		// Constrói a tab PTI-Barreira
		Bundle bundle2 = new Bundle();
		bundle2.putParcelableArrayList("values", (ArrayList<BusTime>)valuesPTI);
		
		View tabView2 = createTabView(getActivity(), "PTI - Barreira");
		tabHost.addTab(tabHost.newTabSpec(TAB_PTI_ID).setIndicator(tabView2), 
				AllTimesFragment.class, bundle2);

		tabHost.setCurrentTab(0);
	}
	
	private View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_indicator_holo, null);
		TextView tv = (TextView) view.findViewById(android.R.id.title);
		tv.setText(text);
		return view;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		tabHost = null;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		if (menu.findItem(R.id.menu_list_all) != null) {
			menu.clear();
		}
	}

}
