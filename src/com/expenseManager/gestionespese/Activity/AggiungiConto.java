package com.expenseManager.gestionespese.Activity;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Utility.SelezioneReport;
import com.expenseManager.gestionespese.Database.DbAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AggiungiConto extends Activity {

	 private DbAdapter dbHelper; 
	 private Cursor cursor;
	 private Account.Conto getconto=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggiungi_conto);
		getconto=(Account.Conto)getIntent().getSerializableExtra("conto");
		dbHelper = new DbAdapter(this);
        dbHelper.open();
        
        /*
         * Recupero la referenza al bottone salva per poter aggiungere il conto al database
         */
        
        Button inserisci=(Button)findViewById(R.id.btn_salva);
        if(getconto==null)
        {
        inserisci.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView nome=(TextView)findViewById(R.id.edit_nome);
				TextView tipo=(TextView)findViewById(R.id.edit_tipo);
				EditText importo=(EditText)findViewById(R.id.edit_value);
				if(checkedValue(nome,tipo,importo,AggiungiConto.this)==true)
				{
				if(dbHelper.createConto(Float.parseFloat(importo.getText().toString()),Float.parseFloat(importo.getText().toString()),nome.getText().toString(), tipo.getText().toString())==-1)
					Toast.makeText(AggiungiConto.this, "Errore", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(AggiungiConto.this, "Conto aggiunto", Toast.LENGTH_SHORT).show();
				Intent myintent=new Intent(AggiungiConto.this,Conto.class);
				startActivity(myintent);
				}
				else
				;
			}
		});
        }
        else
        {
        	TextView nome=(TextView)findViewById(R.id.edit_nome);
			TextView tipo=(TextView)findViewById(R.id.edit_tipo);
			EditText importo=(EditText)findViewById(R.id.edit_value);
			
			nome.setText(getconto.getNome());
			tipo.setText(getconto.getTipo());
			importo.setText(""+getconto.getImporto());
        	inserisci.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				TextView nome=(TextView)findViewById(R.id.edit_nome);
    				TextView tipo=(TextView)findViewById(R.id.edit_tipo);
    				EditText importo=(EditText)findViewById(R.id.edit_value);
    				if(checkedValue(nome,tipo,importo,AggiungiConto.this)==true)
    				{
    				if(dbHelper.updateConto(getconto.getId(),nome.getText().toString(), tipo.getText().toString(),Float.parseFloat(importo.getText().toString()),Float.parseFloat(importo.getText().toString()))==-1)
    					Toast.makeText(AggiungiConto.this, "Errore", Toast.LENGTH_SHORT).show();
    				else
    					Toast.makeText(AggiungiConto.this, "Conto Modificato", Toast.LENGTH_SHORT).show();
    				Intent myintent=new Intent(AggiungiConto.this,Conto.class);
    				startActivity(myintent);
    				}
    				else
    				;
    			}
    		});	
        }
        Button annulla=(Button)findViewById(R.id.btn_annulla);
        annulla.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myintent=new Intent(AggiungiConto.this,Conto.class);
				startActivity(myintent);
			}
		});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aggiungi_conto, menu);
		return true;
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		startActivity(new Intent(this,Conto.class));
		return super.onKeyDown(keyCode, event);
	}
    
	public boolean checkedValue(TextView nome,TextView tipo,EditText value,Context context) throws NumberFormatException
	{

	    boolean flag=true;
		try{
			if(nome.getText().toString().equalsIgnoreCase("")==true || tipo.getText().toString().equalsIgnoreCase("")==true)
			{
				AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
				alert1.setTitle("Errore");
				alert1.setMessage("I campi Nome e/o Tipo non possono essere vuoti ");
				alert1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					 dialog.dismiss();
					 
					}
				});
				AlertDialog dialog=alert1.create();
				dialog.show();
				return false;
			}
		Float.parseFloat(value.getText().toString());
		return flag;
	     }catch(NumberFormatException ex)
	     {
	    	 AlertDialog.Builder alert1 = new AlertDialog.Builder(context);
				alert1.setTitle("Errore");
				alert1.setMessage("Hai inserito un valore errato "+value.getText().toString());
				alert1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					 dialog.dismiss();
					 
					}
				});
				AlertDialog dialog=alert1.create();
				dialog.show();
				flag=false;
	     }
		return flag;
}
}
