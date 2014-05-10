package com.expenseManager.gestionespese.Activity;

import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.id;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setta la data
		Calendar data=Calendar.getInstance();
		TextView var=(TextView) this.findViewById(R.id.date);
		Calendario calendario=new Calendario(var,data.get(Calendar.MONTH),data.get(Calendar.YEAR),data.get(Calendar.DAY_OF_MONTH));
		Button spesa=(Button)findViewById(R.id.spesa);
		spesa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent listaSpese=new Intent(MainActivity.this,Spesa.class);
				startActivity(listaSpese);
			}
		});
		ImageButton addSpesa=(ImageButton)findViewById(R.id.spesa_icon);
		addSpesa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent addSpesa=new Intent(MainActivity.this,AggiungiSpesa.class);
					startActivity(addSpesa);
			}
		});
		ImageButton addEntrata=(ImageButton)findViewById(R.id.entrata_icon);
		addEntrata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent addEntrata=new Intent(MainActivity.this,AggiungiEntrata.class);
				startActivity(addEntrata);
			}
		});
		Button entrata=(Button)findViewById(R.id.entrata);
		entrata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent entrata=new Intent(MainActivity.this,Entrata.class);
				startActivity(entrata);
			}
		});
		Button panoramica=(Button)findViewById(R.id.panoramica);
		panoramica.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent panoramica=new Intent(MainActivity.this,Panoramica.class);
				startActivity(panoramica);
			}
		});
		Button report=(Button)findViewById(R.id.report);
		report.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent report=new Intent(MainActivity.this,Report.class);
				startActivity(report);
			}
		});
		Button conto=(Button)findViewById(R.id.conto);
		conto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent conto=new Intent(MainActivity.this,Conto.class);
				startActivity(conto);	
			}
		});
		ImageButton trasferimento=(ImageButton)findViewById(R.id.trasferimento_icon);
		trasferimento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent trasferimento=new Intent(MainActivity.this,TrasferimentoConto.class);
				startActivity(trasferimento);	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
