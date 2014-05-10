package com.expenseManager.gestionespese.Activity;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AggiungiSpesa extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggiungi_spesa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_spesa, menu);
		return true;
	}

}
