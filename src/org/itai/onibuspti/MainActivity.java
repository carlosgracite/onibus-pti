package org.itai.onibuspti;

import org.itai.onibuspti.R;
import org.itai.onibuspti.fragment.LoaderTaskFragment;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;

public class MainActivity extends SherlockFragmentActivity {
	
	private static final String LOADER_FRAGMENT_TAG = "loader";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LoaderTaskFragment fragment = (LoaderTaskFragment)
				getSupportFragmentManager().findFragmentByTag(LOADER_FRAGMENT_TAG);
		
		if (fragment == null) {
			getSupportFragmentManager().beginTransaction()
					.add(new LoaderTaskFragment(), LOADER_FRAGMENT_TAG).commit();
		}
		
	}

}