package com.expenseManager.gestionespese.Activity;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class Entrata extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entrata);
		ImageButton addEntrata=(ImageButton)findViewById(R.id.addEntrata);
		addEntrata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent addEntrata=new Intent(Entrata.this,AggiungiEntrata.class);
			startActivity(addEntrata);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entrata, menu);
		return true;
	}

}
