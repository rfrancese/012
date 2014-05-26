package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;

import com.expenseManager.gestionespese.R;

import Account.Categoria;
import Account.Conto;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class popupCateEn {
View act;

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

public popupCateEn(ArrayList<Categoria> categorie,RelativeLayout rel,Context context,View act,RelativeLayout below, final TextView txt,final PopupWindow popup)
{
	RelativeLayout layout=rel;
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
			linear.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				txt.setText(nome.getText().toString());
				popup.dismiss();
				}
			});
			layout.addView(linear);
		}
	}
}
}
