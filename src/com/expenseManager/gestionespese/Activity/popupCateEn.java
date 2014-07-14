package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;

import com.expenseManager.gestionespese.CategoriaUscita;
import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Database.DbAdapter;

import Account.Categoria;
import Account.Conto;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class popupCateEn {
View act;
ArrayList<Categoria> categorie=new ArrayList<Categoria>();
private Categoria categoria=new Categoria();
private String operazione;
/**
 * Organizzare la classe
 * @param categorie
 * @param rel
 * @param context
 * @param act
 * @param below
 * @param txt
 * @param popup
 */

public popupCateEn(Cursor cursor1,RelativeLayout rel,final Context context,View act,RelativeLayout below, final TextView txt,final PopupWindow popup,final Categoria cat,Activity act1,final String operazione)
{
	this.operazione=operazione;
	act1.startManagingCursor(cursor1);
	
	Log.v("Text",txt.getText().toString());
    while(cursor1.moveToNext())
    {
      	int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATID)));
        String nome=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATNOME));
        int color=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_COLORID)));
        int icon=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ICONID)));
        
        categorie.add(new Account.Categoria(id, icon, color, nome, null));
    }
    Log.v("Categorie size in popup",""+categorie.size());
	act1.stopManagingCursor(cursor1);	
    cursor1.close();
	RelativeLayout layout=rel;
	ImageButton addCategoria=(ImageButton)act.findViewById(R.id.addCategoria1);
	if(addCategoria!=null)
	{
	addCategoria.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("Operazione",operazione);
			if(operazione.equalsIgnoreCase("Entrata"))
			{
		    Intent addCategoria=new Intent(context,CategoriaEntrata.class);
			context.startActivity(addCategoria);
			}
			else
			{
				Intent addCategoria=new Intent(context,CategoriaUscita.class);
				context.startActivity(addCategoria);
			}	
		}
	});
	}
	if(categorie.size()>0)
	{
		for(int i=0;i<categorie.size();i++)
		{
			LinearLayout linear=new LinearLayout(context);
			linear.setId((i+1)*2000+1);
			Log.v("ID linear",""+linear.getId());
			RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,70);
			if(linear.getId()==2001)
			param.addRule(RelativeLayout.BELOW,layout.getId());
			else
			param.addRule(RelativeLayout.BELOW,(i)*2000+1);
			param.setMargins(0, 10, 0, 0);
			linear.setLayoutParams(param);
			linear.setBackgroundResource(R.color.grey);
			RelativeLayout.LayoutParams iconpar=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			iconpar.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			ImageView icon=new ImageView(context);
			icon.setLayoutParams(iconpar);
			icon.setImageDrawable(categorie.get(i).getDrawableIcon(context));
			icon.setBackgroundColor(categorie.get(i).getColor());
			RelativeLayout.LayoutParams nomepar=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			nomepar.addRule(RelativeLayout.ALIGN_RIGHT,icon.getId());
			final TextView nome=new TextView(context);
			nome.setGravity(Gravity.CENTER|Gravity.LEFT);
			nome.setLayoutParams(nomepar);
			nome.setText(categorie.get(i).getNome());
			nome.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
			nome.setTextColor(context.getApplicationContext().getResources().getColor(R.color.black));
			linear.addView(icon);
			linear.addView(nome);
			final int p=i;
			linear.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				int width=txt.getWidth();
				txt.setText(nome.getText().toString());
				txt.setWidth(width);
				Log.v("widht",""+width);
				popup.dismiss();
				
				categoria.clona(categorie.get(p));
				cat.clona(categoria);
				}
			});
			layout.addView(linear);
		}
	}
}

public void update(Cursor cursor1,RelativeLayout rel,final Context context,View act,RelativeLayout below, final TextView txt,final PopupWindow popup,final Categoria cat,Activity act1)
{
act1.startManagingCursor(cursor1);
	rel.removeAllViews();
	categorie=new ArrayList<Categoria>();
	TextView intesta_cate=(TextView)act.findViewById(R.id.intesta_cate);
	intesta_cate.setText("Categorie UPDATE");
    while(cursor1.moveToNext())
    {
      	int id=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATID)));
        String nome=cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_CATNOME));
        int color=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_COLORID)));
        int icon=Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(DbAdapter.KEY_ICONID)));
        
        categorie.add(new Account.Categoria(id, icon, color, nome, null));
    }
	act1.stopManagingCursor(cursor1);	
    cursor1.close();
	RelativeLayout layout=rel;
	layout.removeAllViews();
	ImageButton addCategoria=(ImageButton)act.findViewById(R.id.addCategoria1);
	addCategoria.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("Operazione",operazione);
			if(operazione.equalsIgnoreCase("Entrata"))
			{
		    Intent addCategoria=new Intent(context,CategoriaEntrata.class);
			context.startActivity(addCategoria);
			}
			else
			{
				Intent addCategoria=new Intent(context,CategoriaUscita.class);
				context.startActivity(addCategoria);
			}
		}
	});
	if(categorie.size()>0)
	{    Log.v("Categorie.size()",""+categorie.size());
		for(int i=0;i<categorie.size();i++)
		{
			LinearLayout linear=new LinearLayout(context);
			linear.setId((i+1)*2000+1);
			Log.v("ID linear",""+linear.getId());
			RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,70);
			if(linear.getId()==2001)
			param.addRule(RelativeLayout.BELOW,layout.getId());
			else
			param.addRule(RelativeLayout.BELOW,(i)*2000+1);
			param.setMargins(0, 10, 0, 0);
			linear.setLayoutParams(param);
			linear.setBackgroundResource(R.color.grey);
			RelativeLayout.LayoutParams iconpar=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			iconpar.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			ImageView icon=new ImageView(context);
			icon.setLayoutParams(iconpar);
			icon.setImageDrawable(categorie.get(i).getDrawableIcon(context));
			icon.setBackgroundColor(categorie.get(i).getColor());
			RelativeLayout.LayoutParams nomepar=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			nomepar.addRule(RelativeLayout.ALIGN_RIGHT,icon.getId());
			final TextView nome=new TextView(context);
			nome.setGravity(Gravity.CENTER|Gravity.LEFT);
			nome.setLayoutParams(nomepar);
			nome.setText(categorie.get(i).getNome());
			nome.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
			nome.setTextColor(context.getApplicationContext().getResources().getColor(R.color.black));
			linear.addView(icon);
			linear.addView(nome);
			final int p=i;
			linear.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				int width=txt.getWidth();
				txt.setText(nome.getText().toString());
				txt.setWidth(width);
				Log.v("widht",""+width);
				popup.dismiss();
				
				categoria.clona(categorie.get(p));
				cat.clona(categoria);
				Log.v("In listener",cat.toString());
				}
			});
			layout.addView(linear);
		}
	}
}
public Categoria getCategoria()
{
	return categoria;
}

public ArrayList<Categoria> getArrayList()
{
	return categorie;
}
}
