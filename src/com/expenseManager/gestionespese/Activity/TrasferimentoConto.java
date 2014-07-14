package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Database.DbAdapter;

import Account.Conto;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TrasferimentoConto extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trasferimento_conto);
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		Cursor cursor=dbHelper.fetchAllCont();
		ArrayList<Account.Conto> conti=new ArrayList<Account.Conto>();
		while(cursor.moveToNext())
		{
			int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOID)));
			String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTONOM));
			String tipo=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOTIP));
			float importo=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM)));
			float importo_att=Float.parseFloat(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CONTOIM_AT)));
			conti.add(new Account.Conto(id,nome,tipo,importo,importo_att));
		}
		ImageButton da=(ImageButton)findViewById(R.id.img_DA);
		ImageButton a=(ImageButton)findViewById(R.id.img_A);
		 LayoutInflater inflate=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View child=inflate.inflate(R.layout.popup_conti, null);
		 View childA=inflate.inflate(R.layout.popup_conti, null);
		 RelativeLayout below=(RelativeLayout)child.findViewById(R.id.conti12);
	     RelativeLayout scroll=(RelativeLayout)child.findViewById(R.id.conti2);
	     RelativeLayout below1=(RelativeLayout)childA.findViewById(R.id.conti12);
	     RelativeLayout scroll1=(RelativeLayout)childA.findViewById(R.id.conti2);
		TextView txtDA=(TextView)findViewById(R.id.cate_addtrasferimento);
		TextView txtA=(TextView)findViewById(R.id.cate_addtrasferimento1);
		final PopupWindow popupDA=new PopupWindow(child,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		final PopupWindow popupA=new PopupWindow(childA,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

		Account.Conto conto_sceltoDA=null;
        final popupConto ll=new popupConto(conti, scroll, child.getContext(),child,below,txtDA,popupDA,conto_sceltoDA);
        Account.Conto conto_sceltoA=null;
        final popupConto ll1=new popupConto(conti, scroll1, childA.getContext(),childA,below1,txtA,popupA,conto_sceltoA);

        da.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!popupDA.isShowing())
				{
					popupDA.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
				else
				popupDA.dismiss();
			}});
        
        a.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!popupA.isShowing())
				{
					popupA.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
				else
				popupA.dismiss();
			}
		});
        final EditText importo=(EditText)findViewById(R.id.importo);
        
        Button salva=(Button)findViewById(R.id.btn_salva);
        salva.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Account.Conto contoDA=new Account.Conto();
			Account.Conto contoA=new Account.Conto();
			
			Log.v("ContoDA",""+contoDA.getId());
			Log.v("ContoA",""+contoA.getId());
			
			if(importo.getText().toString().equalsIgnoreCase("")==false)
			{
				if(ll.getConto().getId()==-1 || ll1.getConto().getId()==-1)
				{
					Toast.makeText(getApplicationContext(), "Selezionare conti", Toast.LENGTH_SHORT).show();
				}
				else
				{
					contoDA.clone(ll.getConto());
					contoA.clone(ll1.getConto());
				
			if(contoDA.getId()==contoA.getId())
				Toast.makeText(getApplicationContext(), "Scegliere conti differenti", Toast.LENGTH_SHORT).show();
			else
			{
				float importo_ = Float.parseFloat(importo.getText().toString());
				if(importo_<=contoDA.getBalance())
				{
					DbAdapter dbHelper1=new DbAdapter(getApplicationContext());
					dbHelper1.open();
					dbHelper1.updateConto(contoDA.getId(), contoDA.getNome(), contoDA.getTipo(), contoDA.getImporto(),importo_,0);
					dbHelper1.updateConto(contoA.getId(), contoA.getNome(), contoA.getTipo(), contoA.getImporto(),importo_,1);
					dbHelper1.close();
					Toast.makeText(getApplicationContext(), "Trasferimento Effettuato", Toast.LENGTH_SHORT).show();
				}
				else
					Toast.makeText(getApplicationContext(), "Importo Insufficiente", Toast.LENGTH_SHORT).show();

			}
			}
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Inserire importo valido", Toast.LENGTH_SHORT).show();
			}
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trasferimento_conto, menu);
		return true;
	}

}
