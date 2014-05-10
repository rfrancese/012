package com.expenseManager.gestionespese.Activity;

import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

public class AggiungiEntrata extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggiungi_entrata);
		final Calendar data=Calendar.getInstance();
		TextView var=(TextView)findViewById(R.id.textData_addentrata);
		Calendario calendario=new Calendario(var,data.get(Calendar.MONTH),data.get(Calendar.YEAR),data.get(Calendar.DAY_OF_MONTH));
		ImageButton setData=(ImageButton)findViewById(R.id.entrata_data);
		setData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    final DatePickerDialog setData=new DatePickerDialog(AggiungiEntrata.this,null,data.get(Calendar.YEAR),data.get(Calendar.MONTH),data.get(Calendar.DAY_OF_MONTH));
			    setData.setButton(DialogInterface.BUTTON_POSITIVE,"Imposta", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					DatePicker data=setData.getDatePicker();
					TextView txt_data=(TextView)findViewById(R.id.textData_addentrata);	
				    Calendario calendario=new Calendario(txt_data,data.getMonth(),data.getYear(),data.getDayOfMonth());
					}
				});
			    setData.setButton(DialogInterface.BUTTON_NEGATIVE,"Annulla", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					setData.dismiss();	
					}
				});
			    setData.show();
			    
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_entrata, menu);
		return true;
	}

}
