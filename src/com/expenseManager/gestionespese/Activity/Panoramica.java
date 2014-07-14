package com.expenseManager.gestionespese.Activity;

import java.util.Calendar;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.Calendario;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class Panoramica extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_panoramica);
		
		Calendar calendario = Calendar.getInstance();
		String[] array_month=getResources().getStringArray(R.array.mese);
		DbAdapter dbAdapter= new DbAdapter(this);
		dbAdapter.open();
		
		//Seleziono data odierna
		TextView oggi=(TextView)findViewById(R.id.txtPgio);
		TextView oggi_entrata=(TextView)findViewById(R.id.txtVred);
		TextView oggi_uscita=(TextView)findViewById(R.id.txtVspe);
		Calendario cal_oggi=new Calendario(oggi, calendario.get(Calendar.MONTH), calendario.get(Calendar.YEAR), calendario.get(Calendar.DAY_OF_MONTH));
		oggi.setText("Oggi : "+oggi.getText().toString());
		Cursor cursor=dbAdapter.fetchOpEntrata(cal_oggi.getData());
		
		
		   // Aggiungo i valori
		float value_giorn_spesa=0,value_giorn_entrata=0;
		while(cursor.moveToNext())
		{
		 value_giorn_entrata+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));	
		}
		cursor.close();
		cursor=dbAdapter.fetchOpUscita(cal_oggi.getData());
		while(cursor.moveToNext())
		{
			value_giorn_spesa+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
		}
		cursor.close();
		oggi_uscita.setText("€ "+value_giorn_spesa);
		oggi_entrata.setText("€ "+value_giorn_entrata);
		//Seleziono periodo settimanale
		TextView settimana=(TextView)findViewById(R.id.txtPsett);
		TextView sett_entrata=(TextView)findViewById(R.id.txtV_S_red);
		TextView sett_uscita=(TextView)findViewById(R.id.txtV_S_spe);
		Calendario cal_settimana=new Calendario(settimana,array_month);
		cursor=dbAdapter.fetchOpData(cal_settimana.getData());
		
		float value_settim_entrata=0,value_settim_spesa=0;
		
		while(cursor.moveToNext())
		{
			value_settim_entrata+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));	
		}
		
		cursor.close();
		cursor=dbAdapter.fetchOpUsData1(cal_settimana.getData());
		while(cursor.moveToNext())
		{
			value_settim_spesa+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));	
	
		}
		cursor.close();
		sett_entrata.setText("€ "+value_settim_entrata);
		sett_uscita.setText("€ "+value_settim_spesa);
		//Seleziono periodo mensile
		
		TextView mese=(TextView)findViewById(R.id.txtPmens);
		TextView mese_entrata=(TextView)findViewById(R.id.txtV_M_red);
		TextView mese_spesa=(TextView)findViewById(R.id.txtV_M_spe);
		Calendario cal_mese=new Calendario(mese,calendario.get(Calendar.MONTH),calendario.get(Calendar.YEAR),array_month);
		mese.setText("Ultimo Mese : "+mese.getText().toString());
	    cursor=dbAdapter.fetchOpData(cal_mese.getData());
		float value_mese_entrata=0,value_mese_spesa=0;
		while(cursor.moveToNext())
		{
			value_mese_entrata+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));	
		}
		
		cursor.close();
		cursor=dbAdapter.fetchOpUsData1(cal_mese.getData());
		while(cursor.moveToNext())
		{
			value_mese_spesa+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));	
	
		}
		cursor.close();
		mese_spesa.setText("€ "+value_mese_spesa);
		mese_entrata.setText("€ "+value_mese_entrata);
		//Seleziono periodo annuale
		
		TextView anno= (TextView)findViewById(R.id.txtPann);
		TextView anno_entrata=(TextView)findViewById(R.id.txtV_A_red);
		TextView anno_uscita=(TextView)findViewById(R.id.txtV_A_spe);
		Calendario cal_annuale=new Calendario(calendario.get(Calendar.YEAR));
		cursor=dbAdapter.fetchOpData(cal_annuale.getData());
		float value_anno_entrata=0,value_anno_uscita=0;
		while(cursor.moveToNext())
		{
			value_anno_entrata+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		}
		cursor.close();
		cursor=dbAdapter.fetchOpUsData1(cal_annuale.getData());
		while(cursor.moveToNext())
		{
			value_anno_uscita+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
	
		}
		cursor.close();
		anno_uscita.setText("€ "+value_anno_uscita);
		anno.setText("Ultimo Anno : "+calendario.get(Calendar.YEAR));
		anno_entrata.setText("€ "+value_anno_entrata);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.panoramica, menu);
		return true;
	}

}
