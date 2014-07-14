package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;

import Account.Conto;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class popupConto {

	private View act;
	private RelativeLayout below,rel,layout;
	private int cont=0,p=0;
	private Context context;
	private TextView txt,nome_c;
	private PopupWindow popup;
	private ArrayList<Conto> conto;
	private Account.Conto conto_scelto=new Account.Conto(-1,"init","init",0,0);
	Account.Conto conticino=new Account.Conto();
	
	public popupConto(ArrayList<Conto> conto,RelativeLayout rel,Context context,View act,RelativeLayout below, TextView txt,final PopupWindow popup,Conto conto_scelto1)
	{
		this.act=act;
		this.below=below;
		this.context=context;
		this.txt=txt;
		this.popup=popup;
		this.rel=rel;
		this.conto=conto;
		conto_scelto1=conto.get(0);
		populaConto(conto_scelto1);
        
	}
	
	public void populaConto(final Conto conto_scelto1)
	{
boolean flag=false;
        
        for(cont=0;cont<conto.size();cont++)
        {
        	Log.v("Below_ID",""+below.getId());
        	Log.v("Cont",""+cont);
        	if(cont>0)
        	{
    	layout=new RelativeLayout(context);
    	layout.setId(cont);
    	ImageView checkbox=new ImageView(context);
    	nome_c=new TextView(context);
    	TextView saldo_c=new TextView(context);
    	TextView saldo1_c=new TextView(context);
    	ImageButton image=new ImageButton(context);
    	RelativeLayout.LayoutParams rparams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
    	Log.v("Below prima di addRule",""+rel.getId());
    	rparams.addRule(RelativeLayout.BELOW,below.getId());
    	layout.setPadding(5, 10, 5, 5);
    	rparams.setMargins(0, 20, 0, 5);
    	
    	
    	Log.v("Below dopo addRule",""+rel.getId());
    	layout.setLayoutParams(rparams);
    	RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	params.addRule(RelativeLayout.BELOW,below.getId());
    	params.bottomMargin=5;
    	rel.addView(layout,rparams);
    	below=(RelativeLayout)act.findViewById(cont);
    	
    	layout.setBackgroundResource(R.color.grey);
    	
        RelativeLayout.LayoutParams check=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	checkbox.setId(conto.get(cont).getId()+100);
    	checkbox.setImageResource(R.drawable.icon_bank);
    	checkbox.setBackgroundResource(R.color.grey);
    	checkbox.setLayoutParams(check);
    	RelativeLayout.LayoutParams nome=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
    	nome.addRule(RelativeLayout.RIGHT_OF,checkbox.getId());
    	nome.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	nome.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	nome_c.setGravity(Gravity.CENTER);
    	//nome_c.setBackgroundResource(R.color.white);
    	nome_c.setId(conto.get(cont).getId()*4+100);
    	nome_c.setTextColor(Color.BLACK);
    	nome_c.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
    	nome_c.setText(conto.get(cont).getNome());
    	nome_c.setLayoutParams(nome);
        final Account.Conto conticino=conto.get(cont);
    	layout.addView(checkbox,check);
    	layout.addView(nome_c);
    	p=cont;
    	Log.v("Conto ["+cont+"]",""+conto.get(cont).getNome());
    	
    	layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.v("P",""+p);
				// TODO Auto-generated method stub
				txt.setText(nome_c.getText().toString());
				popup.dismiss();
				
				conto_scelto.clone(conticino);
				Log.v("Conto layout",""+conto_scelto.getId());
			}
		});
    		
    	}
    	else
    	{
        final TextView nome=(TextView)act.findViewById(R.id.name_cont);
    	p=cont;
    	nome.setText(conto.get(p).getNome());
        Log.v("Conto ["+cont+"]",""+conto.get(p).getNome());
        conticino.clone(conto.get(p));
    	flag=true;
        Log.v("Below id",""+below.getId());
        below.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("P",""+p);
				
				txt.setText(nome.getText().toString());
				popup.dismiss();	
				conto_scelto.clone(conticino);
				conto_scelto1.clone(conto_scelto);
				Log.v("Conto below",""+conto_scelto.getId());
			}
		});
        	
        
    	}
        }
        
	}
	
	public Conto getConto()
	{
		return conto_scelto;
	}
	
}
