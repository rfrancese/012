package com.expenseManager.gestionespese.Utility;

import java.util.ArrayList;

import Account.Conto;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.expenseManager.gestionespese.Database.DbAdapter;

public class ListaConti {

	private DbAdapter dbHelper; 
	private Cursor cursor;
	private ArrayList<Conto> conto=new ArrayList<Conto>();
	private Context _context;
	public ListaConti(Context context)
	{
     _context=context;	 
	 conto=riempiLista();	
	}
	private ArrayList<Conto> riempiLista() {
		// TODO Auto-generated method stub
		dbHelper = new DbAdapter(_context);
        dbHelper.open();
        cursor=dbHelper.fetchAllCont();
        
        while(cursor.moveToNext())
        {
        	int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID)));
        	String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
        	String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
        	float importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
        	Log.v("nome conto",nome);
        	float importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
        	conto.add(new Account.Conto(id,nome,tipo,importo,importo_att));
        }
        
        cursor.close();
		return conto;
	}
	
	public ArrayList<Conto> getConti()
	{
		return conto;
	}
}
