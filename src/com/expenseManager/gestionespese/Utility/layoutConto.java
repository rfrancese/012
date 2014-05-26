package com.expenseManager.gestionespese.Utility;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;

import Account.Conto;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class layoutConto {
     
	private Activity act;
	public layoutConto(ArrayList<Conto> conto,RelativeLayout rel,Context context,Activity act,RelativeLayout below)
	{
		
        this.act=act;
        boolean flag=false;
        
        for(int cont=0;cont<conto.size();cont++)
        {
        	Log.v("Below_ID",""+below.getId());
        	if(flag==true)
        	{
    	RelativeLayout layout=new RelativeLayout(context);
    	layout.setId(cont);
    	ImageView checkbox=new ImageView(context);
    	TextView nome_c=new TextView(context);
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
    	RelativeLayout.LayoutParams nome=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	nome.addRule(RelativeLayout.RIGHT_OF,checkbox.getId());
    	nome_c.setId(conto.get(cont).getId()*4+100);
    	nome_c.setTextColor(Color.BLACK);
    	nome_c.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
    	nome_c.setText(conto.get(cont).getNome());
    	nome_c.setLayoutParams(nome);
    	RelativeLayout.LayoutParams saldo=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	saldo.addRule(RelativeLayout.BELOW,nome_c.getId());
    	saldo.addRule(RelativeLayout.ALIGN_LEFT,nome_c.getId());
    	saldo_c.setId(conto.get(cont).getId()*5+100);
    	saldo_c.setTextColor(Color.BLACK);
    	saldo_c.setTextSize(TypedValue.COMPLEX_UNIT_PT,6);
    	saldo_c.setText("Saldo iniziale €"+conto.get(cont).getImporto());
    	saldo_c.setLayoutParams(saldo);
    	RelativeLayout.LayoutParams saldo1=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	saldo1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	saldo1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	saldo1.setMargins(0, 0, 37,0);
    	saldo1_c.setId(conto.get(cont).getId()*6+100);
    	saldo1_c.setTextColor(Color.BLACK);
    	saldo1_c.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
    	saldo1_c.setText("€"+conto.get(cont).getBalance());
    	saldo1_c.setLayoutParams(saldo1);
    	saldo1_c.setPadding(0, 5, 60, 5);
    	RelativeLayout.LayoutParams img=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	img.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    	img.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	image.setId(conto.get(cont).getId()*3+100);
    	image.setBackgroundResource(R.color.grey);
    	image.setImageResource(R.drawable.account_option);
    	image.setLayoutParams(img);
    	image.setPadding(0, 0, 5, 0);
    	layout.addView(checkbox,check);
    	layout.addView(nome_c);
    	layout.addView(saldo_c);
    	layout.addView(saldo1_c);
    	layout.addView(image);
    	
    	}
    	else
    	{
        TextView nome=(TextView)act.findViewById(R.id.name_cont);
    	TextView saldo=(TextView)act.findViewById(R.id.saldo_cont);
    	TextView saldo1=(TextView)act.findViewById(R.id.saldo_conto1);
    	nome.setText(conto.get(cont).getNome());
    	saldo.setText("Saldo iniziale €"+conto.get(cont).getImporto());
        saldo1.setText("€"+conto.get(cont).getBalance());
        flag=true;
    	}
        }
	    
	}
}
