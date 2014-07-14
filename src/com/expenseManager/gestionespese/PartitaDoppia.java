package com.expenseManager.gestionespese;

import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.PartitaDoppiaRow;
import com.expenseManager.gestionespese.Utility.SelezioneReport;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Build;

public class PartitaDoppia extends Activity {

	private SelezioneReport report=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_partita_doppia);
        Activity act=this.getParent();
		report=(SelezioneReport)getIntent().getSerializableExtra("selezione");
		Log.v("Report",report.toString());
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		
		int cont=0;
		TableLayout tabella=(TableLayout)findViewById(R.id.intestTabella);
		TextView data1=(TextView)findViewById(R.id.data);
		TextView categoria1=(TextView)findViewById(R.id.categoria);
		TextView descrizione1=(TextView)findViewById(R.id.descrizione);
		TextView conto1=(TextView)findViewById(R.id.conto);
		TextView entrata1=(TextView)findViewById(R.id.entrata);
		TextView uscita1=(TextView)findViewById(R.id.uscita);
		
        if(report.getConto()==-1)
        {
        	if(report.getSelezione().equalsIgnoreCase("entrambi"))
        	{
        	if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
    		{
    			Cursor cursor=dbHelper.fetchOpEntrata(report.getData());	
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);
    			cursor.close();
    			cursor=dbHelper.fetchOpUscita(report.getData());
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    		}
    		if(report.getPeriodo().equalsIgnoreCase("settimanale"))
    		{
    		Cursor cursor=dbHelper.fetchOpData(report.getPeriodoSett());	
			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    		cursor.close();
    		cursor=dbHelper.fetchOpUsData1(report.getPeriodoSett());
			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    		cursor.close();
    		}
    		if(report.getPeriodo().equalsIgnoreCase("mensile"))
    		{
    			Cursor cursor=dbHelper.fetchOpData(report.getPeriodoMens(report.getData()));	
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    			cursor=dbHelper.fetchOpUsData1(report.getPeriodoMens(report.getData()));
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    		}
    		if(report.getPeriodo().equalsIgnoreCase("annuale"))
    		{
    			Cursor cursor=dbHelper.fetchOpByYear(Integer.parseInt(report.getData()));	
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    			cursor=dbHelper.fetchOpUsByYear(Integer.parseInt(report.getData()));
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    		}
        	}
        	else if(report.getSelezione().equalsIgnoreCase("uscita"))
        	{
        		if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
        		{
        			
        			Cursor cursor=dbHelper.fetchOpUscita(report.getData());
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        			cursor.close();
        		}
        		if(report.getPeriodo().equalsIgnoreCase("settimanale"))
        		{
        		
        		Cursor cursor=dbHelper.fetchOpUsData1(report.getPeriodoSett());
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        		cursor.close();
        		}
        		if(report.getPeriodo().equalsIgnoreCase("mensile"))
        		{
        			
        			Cursor cursor=dbHelper.fetchOpUsData1(report.getPeriodoMens(report.getData()));
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        			cursor.close();
        		}
        		if(report.getPeriodo().equalsIgnoreCase("annuale"))
        		{
        			
        			Cursor cursor=dbHelper.fetchOpUsByYear(Integer.parseInt(report.getData()));
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        			cursor.close();
        		}	
        		else
        		{
        			if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
            		{
            			Cursor cursor=dbHelper.fetchOpEntrata(report.getData());	
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);
            			cursor.close();
            			
            		}
            		if(report.getPeriodo().equalsIgnoreCase("settimanale"))
            		{
            		Cursor cursor=dbHelper.fetchOpData(report.getPeriodoSett());	
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

            		cursor.close();
            		
            		}
            		if(report.getPeriodo().equalsIgnoreCase("mensile"))
            		{
            			Cursor cursor=dbHelper.fetchOpData(report.getPeriodoMens(report.getData()));	
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

            			cursor.close();
            			
            		}
            		if(report.getPeriodo().equalsIgnoreCase("annuale"))
            		{
            			Cursor cursor=dbHelper.fetchOpByYear(Integer.parseInt(report.getData()));	
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

            			cursor.close();
            			
            		}
        		}
        	}
    			
    			
    		}
    		else
    		{
    			if(report.getSelezione().equalsIgnoreCase("entrambi"))
    			{
    			if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
    			{
    				Cursor cursor=dbHelper.fetchOpEntrata(report.getData(),report.getConto());	
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    				cursor.close();
    				cursor=dbHelper.fetchOpUscita(report.getData(),report.getConto());
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    				cursor.close();
    			}
    			if(report.getPeriodo().equalsIgnoreCase("settimanale"))
    			{
    			Cursor cursor=dbHelper.fetchOpData(report.getPeriodoSett(),report.getConto());	
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    			cursor=dbHelper.fetchOpUsData1(report.getPeriodoSett(),report.getConto());
    			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    			cursor.close();
    			}
    			if(report.getPeriodo().equalsIgnoreCase("mensile"))
    			{
    				Cursor cursor=dbHelper.fetchOpData(report.getPeriodoMens(report.getData()),report.getConto());	
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    				cursor.close();
    				cursor=dbHelper.fetchOpUsData1(report.getPeriodoMens(report.getData()),report.getConto());
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    				cursor.close();
    			}
    			if(report.getPeriodo().equalsIgnoreCase("annuale"))
    			{
    				Cursor cursor=dbHelper.fetchOpByYear(Integer.parseInt(report.getData()),report.getConto());	
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    				cursor.close();
    				cursor=dbHelper.fetchOpUsByYear(Integer.parseInt(report.getData()),report.getConto());
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

    				cursor.close();
    			}
    			}
    			else if(report.getSelezione().equalsIgnoreCase("uscita"))
    			{
    				if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
        			{
        				Cursor cursor=dbHelper.fetchOpUscita(report.getData(),report.getConto());
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        				cursor.close();
        			}
        			if(report.getPeriodo().equalsIgnoreCase("settimanale"))
        			{
        			Cursor cursor=dbHelper.fetchOpUsData1(report.getPeriodoSett(),report.getConto());
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        			cursor.close();
        			}
        			if(report.getPeriodo().equalsIgnoreCase("mensile"))
        			{
        				Cursor cursor=dbHelper.fetchOpUsData1(report.getPeriodoMens(report.getData()),report.getConto());
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        				cursor.close();
        			}
        			if(report.getPeriodo().equalsIgnoreCase("annuale"))
        			{
        				Cursor cursor=dbHelper.fetchOpUsByYear(Integer.parseInt(report.getData()),report.getConto());
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        				cursor.close();
        			}	
    			}
    			else
    			{
    				if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
        			{
        				Cursor cursor=dbHelper.fetchOpEntrata(report.getData(),report.getConto());	
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        				cursor.close();
        				
        			}
        			if(report.getPeriodo().equalsIgnoreCase("settimanale"))
        			{
        			Cursor cursor=dbHelper.fetchOpData(report.getPeriodoSett(),report.getConto());	
        			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        			cursor.close();
        			
        		
        			}
        			if(report.getPeriodo().equalsIgnoreCase("mensile"))
        			{
        				Cursor cursor=dbHelper.fetchOpData(report.getPeriodoMens(report.getData()),report.getConto());	
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        				cursor.close();
        				
        			}
        			if(report.getPeriodo().equalsIgnoreCase("annuale"))
        			{
        				Cursor cursor=dbHelper.fetchOpByYear(Integer.parseInt(report.getData()),report.getConto());	
            			new PartitaDoppiaRow( this, dbHelper, cursor, tabella, cont, data1, categoria1, descrizione1, conto1, entrata1, uscita1);

        				cursor.close();
        				
        			}
    			}
    				
    				
    			
    		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.partita_doppia, menu);
		return true;
	}

	
}
