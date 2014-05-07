package com.expenseManager.gestionespese.Activity;

import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.id;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setta la data
		Calendar data=Calendar.getInstance();
		TextView var=(TextView) this.findViewById(R.id.date);
		switch(data.get(Calendar.MONTH))
		{
		case 0: var.setText("Gennaio,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 1: var.setText("Febbraio,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 2: var.setText("Marzo,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 3: var.setText("Aprile,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 4: var.setText("Maggio,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 5: var.setText("Giugno,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 6: var.setText("Luglio,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 7: var.setText("Agosto,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 8: var.setText("Settembre,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 9: var.setText("Ottobre,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 10: var.setText("Novembre,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		case 11: var.setText("Dicembre,"+data.get(Calendar.DAY_OF_MONTH)+","+data.get(Calendar.YEAR));break;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
