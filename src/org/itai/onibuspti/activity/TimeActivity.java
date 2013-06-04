package org.itai.onibuspti.activity;

import org.itai.onibuspti.R;
import org.itai.onibuspti.fragment.TimeListFragment;
import org.itai.onibuspti.fragment.TimeNextFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class TimeActivity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		
		if (savedInstanceState != null) {
			return;
		}

		// Tela pequena
		if (findViewById(R.id.fragment_container) != null) {
			TimeNextFragment firstFragment = new TimeNextFragment();
			
			firstFragment.setArguments(getIntent().getExtras());
	
			getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, firstFragment).commit();
		}

	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.activity_main, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
			
		case R.id.menu_list_all:
			TimeListFragment fragment = new TimeListFragment();
			
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.fragment_container, fragment);
			transaction.addToBackStack(null);
			transaction.commit();

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
