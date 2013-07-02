package org.itai.onibuspti.fragment;

import org.itai.onibuspti.R;
import org.itai.onibuspti.activity.TimeActivity;
import org.itai.onibuspti.dao.BusTimeDao;
import org.itai.onibuspti.loader.DatabaseTaskLoader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

public class LoaderTaskFragment extends Fragment implements LoaderCallbacks<String>{
	
	private ProgressDialog progressDialog = null;
	private AlertDialog errorDialog = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showProgressDialog();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getActivity().getSupportLoaderManager().initLoader(0, null, this);
	}
	
	@Override
	public void onDetach() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		
		if (errorDialog != null && errorDialog.isShowing()) {
			errorDialog.dismiss();
		}
		super.onStop();
	}

	@Override
	public Loader<String> onCreateLoader(int arg0, Bundle arg1) {
		DatabaseTaskLoader loader = new DatabaseTaskLoader(getActivity());
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<String> loader, String message) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		
		// Se não houver dados no banco, apresenta mensagem de erro. 
		BusTimeDao dao = new BusTimeDao(getActivity());
		if (dao.queryNumEntries() == 0) {
			showErrorDialog(message);

		} else {
			Intent intent = new Intent(getActivity(), TimeActivity.class);
			getActivity().startActivity(intent);
			getActivity().finish();
		}
	}

	@Override
	public void onLoaderReset(Loader<String> loader) {}
	
	private void showProgressDialog() {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("Carregando...");
		progressDialog.setMessage("Carregando lista de horários...");
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				progressDialog.cancel();
				if (getActivity() != null)
					getActivity().finish();
			}
		});
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}
	
	private void showErrorDialog(String message) {
		errorDialog = new AlertDialog.Builder(getActivity())
			.setTitle("Erro")
			.setMessage(message)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setCancelable(false)
			.setNeutralButton(R.string.close_btn, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					getActivity().finish();
				}
			}).create();
		errorDialog.show();
	}

}
