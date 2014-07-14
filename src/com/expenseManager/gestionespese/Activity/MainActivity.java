package com.expenseManager.gestionespese.Activity;

import java.util.Calendar;

import com.expenseManager.gestionespese.Altro;
import com.expenseManager.gestionespese.Categoria;
import com.expenseManager.gestionespese.GraficoBarre;
import com.expenseManager.gestionespese.GraficoTorta;
import com.expenseManager.gestionespese.Impostazioni;
import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.id;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public GridLayout display=null;
	DisplayMetrics metric=new DisplayMetrics();
	Button spesa=null;
	ImageButton addSpesa=null;
	ImageButton addEntrata=null;
	Button entrata=null;
	Button panoramica=null;
	Button report=null;
	Button conto=null;
	ImageButton trasferimento=null;
	Button altro=null;
	Button impostazioni=null;
	int width=0;
    int dimensWidthButt=0;
    int height=0;
    boolean displayChange=false;
    int cont=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Setta la data
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		Cursor cursor=dbHelper.fetchAllCont();
		float importo=0;
		

		
		while(cursor.moveToNext())
		{
			cont++;
			importo+=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
		}
		cursor.close();
		dbHelper.close();
		TextView bilancio=(TextView)findViewById(R.id.saldo_conto);
		bilancio.setText("€ "+importo);
		
		//Rendere il layout ottimizzato per tutti i dispositivi
		spesa=(Button)findViewById(R.id.spesa);
		addSpesa=(ImageButton)findViewById(R.id.spesa_icon);
		addEntrata=(ImageButton)findViewById(R.id.entrata_icon);
		entrata=(Button)findViewById(R.id.entrata);
		panoramica=(Button)findViewById(R.id.panoramica);
		report=(Button)findViewById(R.id.report);
		conto=(Button)findViewById(R.id.conto);
		trasferimento=(ImageButton)findViewById(R.id.trasferimento_icon);
		altro=(Button)findViewById(R.id.altro);
		impostazioni=(Button)findViewById(R.id.impostazioni);
		WindowManager windowManager=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(metric);
		RelativeLayout.LayoutParams paramGrid=new RelativeLayout.LayoutParams(metric.widthPixels*90/100, metric.heightPixels*80/100);
        Log.v("width height",""+metric.widthPixels+" "+metric.heightPixels);
        
        /*//Ottengo le informazioni relative alle dimensioni del display
		display=(GridLayout)findViewById(R.id.griglia_dati);
		display.setLayoutParams(paramGrid);
		int dispWidt=metric.widthPixels*90/100;
		int dispHeigh=metric.heightPixels*80/100;
        GridLayout.LayoutParams setSingle=new GridLayout.LayoutParams();
        GridLayout.LayoutParams setDouble=new GridLayout.LayoutParams();
        setSingle.width=dispWidt*50/100;
		setSingle.height=dispHeigh*25/100;
		setDouble.width=dispWidt*50/100;
		setDouble.height=dispHeigh*50/100;
		spesa.setLayoutParams(setSingle);
		entrata.setLayoutParams(setSingle);
		panoramica.setLayoutParams(setSingle);
		report.setLayoutParams(setSingle);
		impostazioni.setLayoutParams(setSingle);
		altro.setLayoutParams(setSingle);
		conto.setLayoutParams(setDouble);
		Log.v("Conto X-Y",""+conto.getWidth()+" "+conto.getHeight());
		Log.v("Spesa X-Y",""+spesa.getWidth()+" "+spesa.getHeight());
		Log.v("Entrata X-Y",""+entrata.getWidth()+" "+entrata.getHeight());*/
		Point P=null;
		
		
		
		
		Log.v("Spesa X-Y",""+spesa.getWidth()+" X "+spesa.getHeight());
		Calendar data=Calendar.getInstance();
		TextView var=(TextView) this.findViewById(R.id.date);
		final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("Attenzione");
		dialog.setMessage("Non hai conti attivi, per procedere devi aggiungere un conto, vuoi farlo adesso?");
		dialog.setPositiveButton("Si",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,AggiungiConto.class));	
			}
		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			dialog.dismiss();	
			}
		});
		dialog.create();
		Calendario calendario=new Calendario(var,data.get(Calendar.MONTH),data.get(Calendar.YEAR),data.get(Calendar.DAY_OF_MONTH));
		if(cont>0)
		{
		spesa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent listaSpese=new Intent(MainActivity.this,Spesa.class);
				startActivity(listaSpese);
			}
		});
		
		addSpesa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent addSpesa=new Intent(MainActivity.this,AggiungiSpesa.class);
					startActivity(addSpesa);
			}
		});
		
		addEntrata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent addEntrata=new Intent(MainActivity.this,AggiungiEntrata.class);
				startActivity(addEntrata);
			}
		});
		
		entrata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent entrata=new Intent(MainActivity.this,Entrata.class);
				startActivity(entrata);
			}
		});
		}
		else //se non ci sono conti
		{
			dialog.show();
			spesa.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.show();
				}
			});
			
			addSpesa.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.show();
				}
			});
			
			addEntrata.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.show();
				}
			});
			
			entrata.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.show();
				}
			});	
		}
		panoramica.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent panoramica=new Intent(MainActivity.this,Panoramica.class);
				startActivity(panoramica);
			}
		});
		report.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent report=new Intent(MainActivity.this,Report.class);
				startActivity(report);
			}
		});
		conto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent conto=new Intent(MainActivity.this,Conto.class);
				startActivity(conto);	
			}
		});
		trasferimento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(cont>1)
				{
				Intent trasferimento=new Intent(MainActivity.this,TrasferimentoConto.class);
				startActivity(trasferimento);	
				}
				else
				{
				 Toast.makeText(getApplicationContext(), "Devi avere più di un conto attivo", Toast.LENGTH_SHORT).show();
				}
				/*DbAdapter dbHelper=new DbAdapter(getApplicationContext());
				dbHelper.open();
				dbHelper.updateEntrata(1, 200, "2014-07-12", 1, 1, "PROVA", "AHAHHA");
				Log.v("Eseguito","Eseguito");*/
			}
		});
		
		altro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent altro=new Intent(MainActivity.this,Altro.class);
			startActivity(altro);
			}
		});
		
		impostazioni.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(MainActivity.this,Impostazioni.class));	
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onResume()
	{
		super.onResume();
		
 
	}
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		{
			AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
			dialog.setMessage("Vuoi davvero uscire dall'applicazione?");
			dialog.setTitle("Chiusura Applicazione");
			dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				System.exit(0);	
				}
			});
			dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					;
				}
			});
			AlertDialog alDialog=dialog.create();
			alDialog.show();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
