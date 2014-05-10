package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.expenseManager.gestionespese.Utility.CustomBaseAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Report extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		final Spinner categoria=(Spinner)findViewById(R.id.spinner_categoria);
		//categoria.inflate(this, R.id.categorized, categoria);
		CustomBaseAdapter adapter=new CustomBaseAdapter(this,R.layout.list_item,getResources().getStringArray(R.array.conto),getResources().getStringArray(R.array.conto));
		categoria.setAdapter(adapter);
		categoria.setOnItemSelectedListener(new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}});
	   /*categoria.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String selected=(String)arg0.getItemAtPosition(arg2);
			Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
		}});*/
		final Button dataDA=(Button)findViewById(R.id.sel_dataDA);
		final Button dataA=(Button)findViewById(R.id.sel_dataA);
		dataDA.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar data=Calendar.getInstance();
				final DatePickerDialog setData=new DatePickerDialog(Report.this,null,data.get(Calendar.YEAR),data.get(Calendar.MONTH),data.get(Calendar.DAY_OF_MONTH));
			    setData.setButton(DialogInterface.BUTTON_POSITIVE,"Imposta", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						DatePicker data=setData.getDatePicker();	
					    Calendario calendario=new Calendario(dataDA,data.getMonth(),data.getYear(),data.getDayOfMonth());	
					}});
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
		dataA.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar data=Calendar.getInstance();
				final DatePickerDialog setData=new DatePickerDialog(Report.this,null,data.get(Calendar.YEAR),data.get(Calendar.MONTH),data.get(Calendar.DAY_OF_MONTH));
			    setData.setButton(DialogInterface.BUTTON_POSITIVE,"Imposta", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						DatePicker data=setData.getDatePicker();	
					    Calendario calendario=new Calendario(dataA,data.getMonth(),data.getYear(),data.getDayOfMonth());	
					}});
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
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}

}
