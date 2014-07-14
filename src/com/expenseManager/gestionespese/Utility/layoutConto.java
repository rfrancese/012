package com.expenseManager.gestionespese.Utility;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Activity.AggiungiConto;
import com.expenseManager.gestionespese.Activity.TrasferimentoConto;
import com.expenseManager.gestionespese.Database.DbAdapter;

import Account.Conto;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class layoutConto {
     
	private Activity act;
	private Dialog dialog1=null;
	public layoutConto(final ArrayList<Conto> conto,RelativeLayout rel,final Context context,final Activity act,RelativeLayout below)
	{
		
        this.act=act;
        boolean flag=false;
        
        for(int cont=0;cont<conto.size();cont++)
        {
        	Log.v("Below_ID",""+below.getId());
        	final Conto conto_sel=conto.get(cont);
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
    	image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			final Dialog dialog=new Dialog(context);
			dialog.setContentView(R.layout.optconti_dialog);
			dialog.setTitle("Seleziona opzioni conto");
			dialog.show();
			Button annulla=(Button)dialog.findViewById(R.id.annulla);
			annulla.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			Button esegui=(Button)dialog.findViewById(R.id.esegui);
			esegui.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				RadioButton modifica=(RadioButton)dialog.findViewById(R.id.modifica_conto);
				RadioButton trasferisci=(RadioButton)dialog.findViewById(R.id.trasferimento_conto);
				RadioButton cancella=(RadioButton)dialog.findViewById(R.id.elimina_conto);
				int opt=-1;
				if(modifica.isChecked()==true)
					opt=1;
				if(trasferisci.isChecked()==true)
					opt=2;
				if(cancella.isChecked()==true)
					opt=3;
				switch(opt)
				{
				case -1: Toast.makeText(context, "Selezionare Opzione", Toast.LENGTH_SHORT).show();break;
				case 1:Intent intent=new Intent(act,AggiungiConto.class);
				       intent.putExtra("conto", conto_sel);
				       act.startActivity(intent);
				       break;
				case 2:if(conto.size()>1){act.startActivity(new Intent(act,TrasferimentoConto.class));break;}else{Toast.makeText(context, "Devi avere più di un conto registrato", Toast.LENGTH_SHORT).show();break;}
				case 3:
						AlertDialog.Builder dialog=new AlertDialog.Builder(context);
						dialog.setTitle("Eliminare conto?");
						dialog.setMessage("Sei sicuro di voler eliminare il conto "+conto_sel.getNome()+" ? Tutte le operazioni ad esso associate, verranno cancellate");
						dialog.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							DbAdapter dbHelper=new DbAdapter(context);
							dbHelper.open();
							int n=dbHelper.deleteConto(conto_sel.getId());
							dbHelper.close();
							if(n==1)
								{
								Toast.makeText(context,"Conto eliminato con successo",Toast.LENGTH_SHORT).show();
								
								act.startActivity(new Intent(act,com.expenseManager.gestionespese.Activity.Conto.class));
								}
							else
								Toast.makeText(context,"C'è stato un errore",Toast.LENGTH_SHORT).show();

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
						dialog.show();
						break;
				}
				}
			});
			}
		});
    	}
    	else
    	{
        TextView nome=(TextView)act.findViewById(R.id.name_cont);
    	TextView saldo=(TextView)act.findViewById(R.id.saldo_cont);
    	TextView saldo1=(TextView)act.findViewById(R.id.saldo_conto1);
    	ImageButton image=(ImageButton)act.findViewById(R.id.opt_cont);
    	image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog=new Dialog(context);
				dialog.setContentView(R.layout.optconti_dialog);
				dialog.setTitle("Seleziona opzioni conto");
				dialog.show();
				Button annulla=(Button)dialog.findViewById(R.id.annulla);
				annulla.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						dialog1=dialog;
					}
				});
				Button esegui=(Button)dialog.findViewById(R.id.esegui);
				esegui.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					RadioButton modifica=(RadioButton)dialog.findViewById(R.id.modifica_conto);
					RadioButton trasferisci=(RadioButton)dialog.findViewById(R.id.trasferimento_conto);
					RadioButton cancella=(RadioButton)dialog.findViewById(R.id.elimina_conto);
					int opt=-1;
					if(modifica.isChecked()==true)
						opt=1;
					if(trasferisci.isChecked()==true)
						opt=2;
					if(cancella.isChecked()==true)
						opt=3;
					switch(opt)
					{
					case -1: Toast.makeText(context, "Selezionare Opzione", Toast.LENGTH_SHORT).show();break;
					case 1:Intent intent=new Intent(act,AggiungiConto.class);
					       intent.putExtra("conto", conto_sel);
					       act.startActivity(intent);
					       break;
					case 2:if(conto.size()>1){act.startActivity(new Intent(act,TrasferimentoConto.class));break;}else{Toast.makeText(context, "Devi avere più di un conto registrato", Toast.LENGTH_SHORT).show();break;}
					case 3:
							AlertDialog.Builder dialog=new AlertDialog.Builder(context);
							dialog.setTitle("Eliminare conto?");
							dialog.setMessage("Sei sicuro di voler eliminare il conto "+conto_sel.getNome()+" ? Tutte le operazioni ad esso associate, verranno cancellate");
							dialog.setPositiveButton("Continua", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
								DbAdapter dbHelper=new DbAdapter(context);
								dbHelper.open();
								int n=dbHelper.deleteConto(conto_sel.getId());
								dbHelper.close();
								if(n==1)
									{
									Toast.makeText(context,"Conto eliminato con successo",Toast.LENGTH_SHORT).show();
									
									act.startActivity(new Intent(act,com.expenseManager.gestionespese.Activity.Conto.class));
									}
								else
									Toast.makeText(context,"C'è stato un errore",Toast.LENGTH_SHORT).show();

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
							dialog.show();
							break;
					}
					}
				});
				}
			
		});
    	nome.setText(conto.get(cont).getNome());
    	saldo.setText("Saldo iniziale €"+conto.get(cont).getImporto());
        saldo1.setText("€"+conto.get(cont).getBalance());
        flag=true;
    	}
        }
	    
	}
}
