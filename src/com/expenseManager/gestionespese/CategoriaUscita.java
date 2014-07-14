package com.expenseManager.gestionespese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.ExpandableListAdapter;
import com.expenseManager.gestionespese.Utility.color_id;

import Account.Categoria;
import android.app.Activity;
import android.graphics.Color;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.os.Build;

public class CategoriaUscita extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categoria_uscita);
		DisplayMetrics P = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(P);
		Log.v("P.x - P.Y",""+P.widthPixels+" - "+P.heightPixels);
		int p=0,id;
		
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
		final Categoria categoria=new Categoria();
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
				dbAdapter.createCatUs(categoria.getNome(), categoria.getColor(), categoria.getIcon());
				Toast.makeText(getApplicationContext(), "Categoria Aggiunta", Toast.LENGTH_SHORT).show();
				}
				Log.v("Categoria selezionata",categoria.toString());
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categoria_uscita, menu);
		return true;
	}

	

}
