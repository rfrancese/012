package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.GraficoBarre;
import com.expenseManager.gestionespese.PartitaDoppia;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.expenseManager.gestionespese.Utility.CustomBaseAdapter;
import com.expenseManager.gestionespese.Utility.SelezioneReport;
import com.expenseManager.gestionespese.Utility.layoutConto;

import Account.Conto;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Report extends Activity {
	
	 private DbAdapter dbHelper; 
	 private Cursor cursor;
	 private double value;
	 int selFlag=-1;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		final Spinner categoria=(Spinner)findViewById(R.id.spinner_categoria);
		
		final Button sel_entrate=(Button)findViewById(R.id.sel_entrate);
		final Button sel_uscite=(Button)findViewById(R.id.sel_uscite);
		final Button sel_entrambi=(Button)findViewById(R.id.sel_entrambi);
		sel_entrate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sel_entrate.setTextColor(getResources().getColor(R.color.entrata));
				sel_uscite.setTextColor(getResources().getColor(R.color.white));
				sel_entrambi.setTextColor(getResources().getColor(R.color.white));
				sel_entrambi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				sel_entrate.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.checkbox_on_background, 0, 0, 0);
				sel_uscite.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				selFlag=0;
			}
		});
		sel_uscite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sel_entrate.setTextColor(getResources().getColor(R.color.white));
				sel_uscite.setTextColor(getResources().getColor(R.color.spesa));
				sel_entrambi.setTextColor(getResources().getColor(R.color.white));
				sel_entrambi.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				sel_entrate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				sel_uscite.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.checkbox_on_background, 0, 0, 0);
				selFlag=1;
			}
		});
		sel_entrambi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sel_entrate.setTextColor(getResources().getColor(R.color.white));
				sel_uscite.setTextColor(getResources().getColor(R.color.white));
				sel_entrambi.setTextColor(getResources().getColor(R.color.report));
				sel_entrambi.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.checkbox_on_background, 0, 0, 0);
				sel_entrate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				sel_uscite.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				selFlag=2;	
			}
		});
		final Button dataA=(Button)findViewById(R.id.sel_dataA);
		Calendar cal=Calendar.getInstance();
		//Calendario calendario=new Calendario(dataDA,cal.get(Calendar.MONTH),cal.get(Calendar.YEAR),cal.get(Calendar.DAY_OF_MONTH));
		final Calendario calendario2=new Calendario(dataA,cal.get(Calendar.MONTH),cal.get(Calendar.YEAR),cal.get(Calendar.DAY_OF_MONTH));
		/**
		 * Seleziona conto da passare all'adattatore
		 */
		
		final ArrayList<Account.Conto> conti=new ArrayList<Account.Conto>();
		dbHelper = new DbAdapter(this);
        dbHelper.open();
        cursor=dbHelper.fetchAllCont();
        conti.add(new Account.Conto(-1,"Tutti i conti",null,0,0));
        startManagingCursor(cursor);
        while(cursor.moveToNext())
        {
        	int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID)));
        	String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
        	String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
        	float importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
        	float importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
        	conti.add(new Account.Conto(id,nome,tipo,importo,importo_att));
        }
        stopManagingCursor(cursor);
        cursor.close();
        
		
		//categoria.inflate(this, R.id.categorized, categoria);
		CustomBaseAdapter adapter=new CustomBaseAdapter(this,R.layout.list_item,conti,conti);
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
		final Spinner dataDA=(Spinner)findViewById(R.id.sel_dataDA);
		ArrayAdapter<String> adapterPeriodoo=new ArrayAdapter<String>(this,R.layout.spinner_item,getResources().getStringArray(R.array.report));
		dataDA.setAdapter(adapterPeriodoo);
		final Button sel_dataA=(Button)findViewById(R.id.sel_dataA);
		final View index=sel_dataA;
		final LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,1);
		final Spinner mese=new Spinner(this);
		final EditText sel_anno=new EditText(this);
		Button stampa=(Button)findViewById(R.id.stampa);
		stampa.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String periodo=null;
				String data=null;
				String scelta=null;
				ArrayList<String> periodoSettimana=null;
				SelezioneReport report=null;
				boolean flagGrafico=false;
				boolean flagBilancio=false;
				switch(selFlag)
				{
				case 0: scelta="entrata";break;
				case 1: scelta="uscita";break;
				case 2: scelta="entrambi";break;
				case -1: Toast.makeText(getApplicationContext(), "Scegliere il flusso economico da analizzare", Toast.LENGTH_SHORT).show();
				}
				int selConto=categoria.getSelectedItemPosition();
				Log.v("Conto scelto",conti.get(selConto).getNome()+"-"+conti.get(selConto).getId());
				int mese_sel=-1;
				
				switch(dataDA.getSelectedItemPosition())
				{
				case 0: periodo="giornaliero";data=calendario2.getData();break;
				case 1: periodo="settimanale";
				Calendario cal=new Calendario(calendario2.getMonth(),calendario2.getYear(),calendario2.getDay(),12);
				periodoSettimana=cal.getIntervallo();
				for(int i=0;i<periodoSettimana.size();i++)
				{
					Log.v("Giorno",periodoSettimana.get(i));
				}
						data=calendario2.getData();break;
				case 2: periodo="mensile";mese_sel=mese.getSelectedItemPosition();break;
				case 3: periodo="annuale";data=sel_anno.getText().toString();break;
				}
				if(mese_sel!=-1)
				{
					String array_mesi[]=getResources().getStringArray(R.array.mese);
					data=array_mesi[mese_sel];
				}
				if(data.equalsIgnoreCase("")==false)
				{
					if(scelta!=null)
					{
				if(mese_sel!=-1)
				{
					
					 report=new SelezioneReport(scelta,conti.get(selConto).getId(),periodo,data+"-"+mese_sel);
					Log.v("selezione",report.toString());
					
				}
				else if(dataDA.getSelectedItemPosition()==1)
				{
					 report=new SelezioneReport(scelta,conti.get(selConto).getId(),periodo,data,periodoSettimana);
				Log.v("selezione",report.toString());
				}
				else
				{
					report=new SelezioneReport(scelta,conti.get(selConto).getId(),periodo,data);
					Log.v("selezione",report.toString());
					
				}
				RadioButton radioGrafico=(RadioButton)findViewById(R.id.sel_grafico);
				RadioButton radioBilancio=(RadioButton)findViewById(R.id.sel_bilancio);
				boolean flagBilancio1=false;
				boolean flagGrafico1=false;
				if(radioBilancio.isChecked()==true)
				{
					Log.v("Bilancio",""+radioBilancio.isChecked());
					Log.v("Grafico",""+radioGrafico.isChecked());
					flagBilancio1=true;
					
				 
				}
				else if(radioGrafico.isChecked()==true);
				{
					Log.v("Bilancio",""+radioBilancio.isChecked());
					Log.v("Grafico",""+radioGrafico.isChecked());
					flagGrafico1=true;
					
					
				}
				if(flagBilancio1==true)
				{
					Intent partitaDoppia=new Intent(Report.this,PartitaDoppia.class);
					partitaDoppia.putExtra("selezione", report);
					startActivity(partitaDoppia);	
				}
				else if(flagGrafico1==true)
				{
					Intent grafico=new Intent(Report.this,GraficoBarre.class);	
					 grafico.putExtra("selezione",report);
					 startActivity(grafico);
				}
					
				}
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Inserire anno", Toast.LENGTH_SHORT).show();
				}
				
			}});
		
		dataDA.setOnItemSelectedListener(new OnItemSelectedListener(){

			

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LinearLayout sel_data=(LinearLayout)findViewById(R.id.sel_data);
				
				Log.v("Totale child",""+sel_data.getChildCount());
				Log.v("Child",""+sel_data.getChildAt(sel_data.getChildCount()-1));
				sel_data.removeViewAt(1);
				int cont=0;
				mese.setLayoutParams(params);
				switch(position)
				{
				case 0: sel_data.addView(index);break;
				case 1: sel_data.addView(index);break;
				case 2: 
				        mese.setId(1234);
				        String mesi[]=getResources().getStringArray(R.array.mese);
				        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,mesi );mese.setAdapter(adapter);
				        //dataDA.setAdapter(adapter);
				        mese.setLayoutParams(params);
				        sel_data.addView(mese);break;
				case 3: 
				        sel_anno.setTextColor(getResources().getColor(R.color.white));
				        sel_anno.setInputType(InputType.TYPE_CLASS_NUMBER);
				        sel_anno.setLayoutParams(params);
				        sel_data.addView(sel_anno);
				        break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
		
		/*dataDA.setOnClickListener(new View.OnClickListener() {
			
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
		});*/
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
					    calendario2.updateData(dataA,data.getMonth(),data.getYear(),data.getDayOfMonth());	
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
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		startActivity(new Intent(this,MainActivity.class));
		return super.onKeyDown(keyCode, event);
	}
}
