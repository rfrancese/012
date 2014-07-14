package com.expenseManager.gestionespese.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.expenseManager.gestionespese.R;
import com.expenseManager.gestionespese.R.layout;
import com.expenseManager.gestionespese.R.menu;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.ExpandableListAdapter;
import com.expenseManager.gestionespese.Utility.color_id;

import Account.Categoria;
import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriaEntrata extends Activity {

	/**
	 * Organizzare la classe
	 */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria_entrata);
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		Cursor cursor=dbHelper.fetchAllCatEn();
		int i=0;
		while(cursor.moveToNext())
		{
			Log.v("Categoria",cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_CATID)));
			i++;
		}
		cursor.close();
		Log.v("I",""+i);
		DisplayMetrics P = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(P);
		Log.v("P.x - P.Y",""+P.widthPixels+" - "+P.heightPixels);
		int p=0,id;
		final Categoria categoria=new Categoria();
		final color_id color_id=new color_id();
		final RelativeLayout tab_cat=(RelativeLayout)findViewById(R.id.tab_entrata);
		final ExpandableListView list=(ExpandableListView)findViewById(R.id.listStep);
		final ArrayList<String> listDataHeader=new ArrayList<String>();
		listDataHeader.add("Step 1: Seleziona Icona");
		listDataHeader.add("Step 2: Inserisci categoria");
		listDataHeader.add("Step 3: Seleziona colore");
		HashMap<String,List<Integer>> listChildImage=new HashMap<String,List<Integer>>();
		final HashMap<String,List<String>>  nome_cat=new HashMap<String,List<String>>();
		final HashMap<String,List<Integer>> color=new HashMap<String,List<Integer>>();
		final List<String> nome=new ArrayList<String>();
		List<Integer> img=new ArrayList<Integer>();
		img.add(1);
		nome_cat.put(listDataHeader.get(1), nome);
		nome.add("Ciao");
		List<Integer> col=new ArrayList<Integer>();
		col.add(Color.BLACK);
		col.add(Color.BLUE);
		col.add(Color.CYAN);
		col.add(Color.DKGRAY);
		col.add(Color.GREEN);
		col.add(Color.LTGRAY);
		col.add(Color.WHITE);
		col.add(Color.YELLOW);
		col.add(Color.CYAN);
		col.add(Color.BLACK);
		color.put(listDataHeader.get(2), col);
		listChildImage.put(listDataHeader.get(0), img);
		TextView textview=(TextView)findViewById(R.id.cattext);
		ImageView setIcon=(ImageView)findViewById(R.id.seticon);
		ExpandableListAdapter listAdapt=new ExpandableListAdapter(this,listDataHeader,listChildImage,nome_cat,color,textview,setIcon,list,categoria);
		//prepareListData();
		list.setAdapter(listAdapt);
		list.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				/*Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition)
                        + " : "
                        + nome_cat.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition)+" group "+groupPosition, Toast.LENGTH_SHORT).show();*/
				if(groupPosition==2)
				{
					
					ImageView seticon=(ImageView)findViewById(R.id.seticon);
					seticon.setBackgroundColor(Integer.parseInt(color.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition).toString()));
					color_id.setId(Integer.parseInt(color.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition).toString()));
					categoria.setColor(color_id.getId());
					Log.v("Color id",""+Integer.parseInt(color.get(listDataHeader.get(groupPosition)).get(childPosition).toString()));
				}
				
				if(groupPosition==1)
				{	
					//final TextView text1= (TextView)list.findViewById(R.id.edit_nome_cat);
					
					//Toast.makeText(getApplicationContext(), text1.getText().toString(), Toast.LENGTH_SHORT).show();
				}
				return false;
			}
		});
		list.setOnGroupClickListener(new OnGroupClickListener(){

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int arg2, long arg3) {
				
				
				//Toast.makeText(getApplicationContext(), ""+arg1.getId(), Toast.LENGTH_SHORT).show();
				
					//TextView text1= (TextView)list.findViewById(R.id.edit_nome_cat);
					//Toast.makeText(getApplicationContext(), text1.getText().toString(), Toast.LENGTH_SHORT).show();
				
				return false;
			}});
		Button salva=(Button)findViewById(R.id.btn_salva);
		salva.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			/*int color=color_id.getId();
			int icon=0;
			String nome_cat="";*/
				DbAdapter dbAdapter=new DbAdapter(getBaseContext());
				dbAdapter.open();
				if(categoria.getColor()==0 || categoria.getIcon()==0)
				{
					Toast.makeText(getApplicationContext(), "Scegliere icona e/o colore per aggiungere una nuova categoria", Toast.LENGTH_SHORT).show();

				}
				else
				{
				dbAdapter.createCatEn(categoria.getNome(), categoria.getColor(), categoria.getIcon());
				Toast.makeText(getApplicationContext(), "Categoria Aggiunta", Toast.LENGTH_SHORT).show();
				Log.v("Categoria selezionata",categoria.toString());
				}
			}
		});
		//TableLayout.LayoutParams params=new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		//tab_cat.setLayoutParams(params);
		//tab_cat.setBackgroundResource(R.color.entrata1);
		
		/*for(int i=0;i<18;i++)
		{
			LinearLayout row=new LinearLayout(this);
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
				ImageButton icon=new ImageButton(this);
				LinearLayout.LayoutParams parameter=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
				icon.setLayoutParams(parameter);
				//icon.setPadding(105, 10, 105, 10);
				parameter.setMargins(85, 10, 85, 10);
				final String res="caticon"+p;
				Log.v("Resource",res);
				p+=1;
				Resources resource=getResources();
				Drawable img=resource.getDrawable(getResources().getIdentifier(res, "drawable", "com.expenseManager.gestionespese"));
				Bitmap bitmap=((BitmapDrawable)img).getBitmap();
				Drawable img1=new BitmapDrawable(getResources(),Bitmap.createScaledBitmap(bitmap, 70, 70, true));
				icon.setBackground(img1);
				icon.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(CategoriaEntrata.this, res, Toast.LENGTH_SHORT).show();
						scroll.removeView(tab_cat);
					}
				});
				Log.v("icon",""+icon.getBackground().toString());
				row.addView(icon);
			}
			tab_cat.addView(row);
		}
		//scroll.addView(tab_cat);*/
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categoria_entrata, menu);
		return true;
	}

}
