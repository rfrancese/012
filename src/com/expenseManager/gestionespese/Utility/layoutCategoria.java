package com.expenseManager.gestionespese.Utility;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.Activity.CategoriaEntrata;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class layoutCategoria {
    private View context;
    private ExpandableListView opt;
	public layoutCategoria(final View context,final ImageView image, ExpandableListView opt1)
	{
		this.context=context;
		this.opt=(ExpandableListView)opt1;
        int p=0,id;
		
		final RelativeLayout tab_cat=(RelativeLayout)context.findViewById(R.id.tab_entrata);
		for(int i=0;i<18;i++)
		{
			LinearLayout row=new LinearLayout(context.getContext());
			RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			row.setPadding(20, 20, 20, 20);
			row.setId(i+1000);
			if(i==0)
				row.setLayoutParams(params);
			else
			{
				params.addRule(RelativeLayout.BELOW,row.getId()-1);
				row.setLayoutParams(params);
			}
			for(int j=0;j<3;j++)
			{
				ImageButton icon=new ImageButton(context.getContext());
				LinearLayout.LayoutParams parameter=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				icon.setLayoutParams(parameter);
				//icon.setPadding(105, 10, 105, 10);
				parameter.setMargins(85, 10, 85, 10);
				final String res="caticon"+p;
				Log.v("Resource",res);
				p+=1;
				Resources resource=context.getResources();
				Drawable img=resource.getDrawable(context.getResources().getIdentifier(res, "drawable", "com.expenseManager.gestionespese"));
				Bitmap bitmap=((BitmapDrawable)img).getBitmap();
				Drawable img1=new BitmapDrawable(context.getResources(),Bitmap.createScaledBitmap(bitmap, 70, 70, true));
				icon.setBackground(img1);
				icon.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int id=context.getResources().getIdentifier(res, "drawable", "com.expenseManager.gestionespese");
						Log.v("ID",""+id);
						image.setImageResource(context.getResources().getIdentifier(res, "drawable", "com.expenseManager.gestionespese"));
						opt.collapseGroup(0);
						opt.expandGroup(1);
						
						//scroll.removeView(tab_cat);
					}
				});
				Log.v("icon",""+icon.getBackground().toString());
				row.addView(icon);
			}
			tab_cat.addView(row);
		}
	}
}
