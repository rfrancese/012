
package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.layoutConto;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Conto extends Activity {

	 private DbAdapter dbHelper; 
	 private Cursor cursor;
	 private double value;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conto);
		Button addConto=(Button)findViewById(R.id.addConto);
		addConto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent intent=new Intent(Conto.this,AggiungiConto.class);
			startActivity(intent);
			}
		});
		dbHelper = new DbAdapter(this);
        dbHelper.open();
        cursor=dbHelper.fetchAllCont();
        startManagingCursor(cursor);
        boolean flag=false;
        RelativeLayout below=(RelativeLayout)findViewById(R.id.conti12);
        RelativeLayout scroll=(RelativeLayout)findViewById(R.id.conti2);
        ScrollView veroscroll=(ScrollView)findViewById(R.id.conti);
        ArrayList<Account.Conto> conticino=new ArrayList<Account.Conto>();
        while(cursor.moveToNext())
        {
        	int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID)));
        	String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
        	String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
        	float importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
        	String value=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT));
        	float importo_att=Float.parseFloat(value);
        	Log.v("Importo",""+importo_att);
        	conticino.add(new Account.Conto(id,nome,tipo,importo,importo_att));
        }
        layoutConto ll=new layoutConto(conticino, scroll, this, Conto.this,below);
        stopManagingCursor(cursor);
        cursor.close();
        TextView txt=(TextView)findViewById(R.id.saldo_conto);
        txt.setText("€"+value);
        Log.v("Width",""+veroscroll.getWidth());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conto, menu);
		return true;
	}
	
	public void onResume()
	{
		super.onResume();
		dbHelper = new DbAdapter(this);
        dbHelper.open();
        cursor=dbHelper.fetchAllCont();
	}
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		startActivity(new Intent(this,MainActivity.class));
		return super.onKeyDown(keyCode, event);
	}
}
