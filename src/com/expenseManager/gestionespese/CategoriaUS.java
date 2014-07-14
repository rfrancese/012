package com.expenseManager.gestionespese;

import java.util.ArrayList;

import com.expenseManager.gestionespese.Database.DbAdapter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.Cursor;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

public class CategoriaUS extends Activity {
	private DbAdapter dbHelper; 
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria_us);
		RelativeLayout principale=(RelativeLayout)findViewById(R.id.principale);
		RelativeLayout intestaCate=(RelativeLayout)findViewById(R.id.intestaCate);
		ImageButton image=(ImageButton)findViewById(R.id.addCategoria1);
		
		intestaCate.removeView(image);
		TextView text=(TextView)findViewById(R.id.intesta_cate);
		text.setText("Lista Categorie di Spesa");
		principale.setBackgroundColor(getResources().getColor(R.color.blue));
		dbHelper = new DbAdapter(this);
        dbHelper.open();
        cursor=dbHelper.fetchAllCatUs();
        ArrayList<Account.Categoria> categorie=new ArrayList<Account.Categoria>();
        
    RelativeLayout layout=(RelativeLayout)findViewById(R.id.Categorie);
        while(cursor.moveToNext())
    {
      	int id=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CATID)));
        String nome=cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CATNOME));
        int color=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_COLORID)));
        int icon=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ICONID)));
        
        categorie.add(new Account.Categoria(id, icon, color, nome, null));
    }
		cursor.close();
		if(categorie.size()>0)
		{
			for(int i=0;i<categorie.size();i++)
			{
				LinearLayout linear=new LinearLayout(this);
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
				ImageView icon=new ImageView(this);
				icon.setLayoutParams(iconpar);
				icon.setImageDrawable(categorie.get(i).getDrawableIcon(this));
				icon.setBackgroundColor(categorie.get(i).getColor());
				RelativeLayout.LayoutParams nomepar=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
				nomepar.addRule(RelativeLayout.ALIGN_RIGHT,icon.getId());
				TextView nome=new TextView(this);
				nome.setGravity(Gravity.CENTER|Gravity.LEFT);
				nome.setLayoutParams(nomepar);
				nome.setText(categorie.get(i).getNome());
				nome.setTextSize(TypedValue.COMPLEX_UNIT_PT,8);
				nome.setTextColor(this.getApplicationContext().getResources().getColor(R.color.black));
				linear.addView(icon);
				linear.addView(nome);
				final Account.Categoria categoria=categorie.get(i);
				linear.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
					Log.v("Categoria",categoria.toString());	
					}
				});
				layout.addView(linear);
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categoria_u, menu);
		return true;
	}

	
}
