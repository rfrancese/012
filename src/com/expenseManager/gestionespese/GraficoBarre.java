package com.expenseManager.gestionespese;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;

import com.expenseManager.gestionespese.Activity.Report;
import com.expenseManager.gestionespese.Database.DbAdapter;
import com.expenseManager.gestionespese.Utility.Calendario;
import com.expenseManager.gestionespese.Utility.SelezioneReport;

import Account.Categoria;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Build;

public class GraficoBarre extends Activity {
	public ArrayList<XYSeries> seriesClick=new ArrayList<XYSeries>();
	public boolean flag=false;
	private int SERIES_NR=1;
	XYMultipleSeriesRenderer renderer=new XYMultipleSeriesRenderer();
	ArrayList<Color> color=new ArrayList<Color>();
	private ArrayList<String> intervallo=null;
	private SelezioneReport report=null;
	ArrayList<Account.Operazione> operazione=new ArrayList<Account.Operazione>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grafico_barre);
		report=(SelezioneReport)getIntent().getSerializableExtra("selezione");
		Log.v("Selezione",report.toString());
        
		if(report.getSelezione().equalsIgnoreCase("entrambi")==true)
		{
			openChartDouble();
			
			
		}
		else if(report.getSelezione().equalsIgnoreCase("entrata")==true)
		{
			openChartSingle();
		}
			else if(report.getSelezione().equalsIgnoreCase("uscita")==true)
			{
			 openChartSingle();
			}
				
        //openChartSingle();
		//openChartDouble();
	}
	

	private void openChartSingle() {
		// TODO Auto-generated method stub
		SERIES_NR=1;
		final XYMultipleSeriesRenderer renderer=getDataRenderer1();
		myChartSettings1(renderer);
		RelativeLayout layout=(RelativeLayout)findViewById(R.id.container);
		final View mChartView=ChartFactory.getBarChartView(this, getDateRenderer(), renderer, Type.STACKED);
		mChartView.setBackgroundColor(getResources().getColor(R.color.blue));
	mChartView.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("Hai cliccato qualcosa!","Hai cliccato");
			SeriesSelection seriesSelection=((GraphicalView) mChartView).getCurrentSeriesAndPoint();
		    if(seriesSelection==null)
		    {
		    	Toast.makeText(getApplicationContext(), "No element", Toast.LENGTH_SHORT).show();
		    }
		    else
		    {
		    	Log.v("SeriesSelection %",""+seriesSelection.getValue());
		    	Log.v("SeriesSelection Index ",""+seriesSelection.getSeriesIndex());
		    	Log.v("SeriesSelection Point ",""+seriesSelection.getPointIndex());
		        
		        int position=seriesSelection.getSeriesIndex();
		        Log.v("Operazione "+position,operazione.get(position).toString());
		    }
		   
		}
	});
		layout.addView(mChartView);
		
	}
	
	

	private void myChartSettings1(XYMultipleSeriesRenderer renderer) {
		// TODO Auto-generated method stub
		renderer.setOrientation(Orientation.HORIZONTAL);
		//renderer.setClickEnabled(true);
		renderer.setXAxisMin(0);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(110);
		renderer.setXLabelsPadding(30);	
		renderer.setYLabelsAlign(Align.CENTER);
		renderer.setBarSpacing(100);
		renderer.setBarWidth(60);
		renderer.setClickEnabled(true);
		
		
		renderer.setYTitle("Importo");
		renderer.setShowGrid(false);
		renderer.setGridColor(Color.GRAY);
		renderer.setXLabels(0);
		renderer.setYLabels(0);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setShowLegend(false);
		renderer.setXLabelsAngle(30);
		/*if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
		{
			renderer.setXAxisMax(2);	
			renderer.addXTextLabel(1.3, report.getData());
		}
		else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
		{
			renderer.setXAxisMax(2);
			String data=report.getPeriodoSettimana().get(report.getPeriodoSettimana().size()-1)+" - "+report.getPeriodoSettimana().get(0);
		    renderer.addXTextLabel(1.3, data);
		}
			else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
			{
				renderer.setXAxisMax(2);
				renderer.addXTextLabel(1.3, report.getDataOfMese());
			}
				else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
				{
					
					renderer.setXAxisMax(2);
					
						renderer.addXTextLabel(1.3,report.getData());
						
				}*/
	}


	public void openChartDouble()
	{
		SERIES_NR=2;
		final XYMultipleSeriesRenderer renderer=getDataRenderer();
		myChartSettings(renderer);
		RelativeLayout layout=(RelativeLayout)findViewById(R.id.container);
		final View mChartView=ChartFactory.getBarChartView(this, getDateRendererDouble(), renderer, Type.DEFAULT);
		mChartView.setBackgroundColor(getResources().getColor(R.color.blue));
	
	    layout.addView(mChartView);
		
	}
	
	private XYMultipleSeriesDataset getDateRendererDouble() {
		// TODO Auto-generated method stub
		XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
		float importo_tot=0;
		float importo_tot_en=0;
		float importo_tot_us=0;
		double perc_en=0;
		double perc_us=0;
		ArrayList<String> legendTitles=new ArrayList<String>();
		legendTitles.add(getResources().getString(R.string.entrata));
		legendTitles.add(getResources().getString(R.string.uscita));
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		ArrayList<Double> value=new ArrayList<Double>();
		if(report.getConto()==-1)
		{
		if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
		{
			Cursor cursor=dbHelper.fetchOpEntrata(report.getData());	
			while(cursor.moveToNext())
			{
				importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
			   
			}
			cursor.close();
			cursor=dbHelper.fetchOpUscita(report.getData());
			while(cursor.moveToNext())
			{
				importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
			}
			cursor.close();
		}
		if(report.getPeriodo().equalsIgnoreCase("settimanale"))
		{
		Cursor cursor=dbHelper.fetchOpData(report.getPeriodoSett());	
		while(cursor.moveToNext())
		{
			importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
		   
		}
		cursor.close();
		cursor=dbHelper.fetchOpUsData1(report.getPeriodoSett());
		while(cursor.moveToNext())
		{
			importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
		}
		cursor.close();
		}
		if(report.getPeriodo().equalsIgnoreCase("mensile"))
		{
			Cursor cursor=dbHelper.fetchOpData(report.getPeriodoMens(report.getData()));	
			while(cursor.moveToNext())
			{
				importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
			   
			}
			cursor.close();
			cursor=dbHelper.fetchOpUsData1(report.getPeriodoMens(report.getData()));
			while(cursor.moveToNext())
			{
				importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
			}
			cursor.close();
		}
		if(report.getPeriodo().equalsIgnoreCase("annuale"))
		{
			Cursor cursor=dbHelper.fetchOpByYear(Integer.parseInt(report.getData()));	
			while(cursor.moveToNext())
			{
				importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
			   
			}
			cursor.close();
			cursor=dbHelper.fetchOpUsByYear(Integer.parseInt(report.getData()));
			while(cursor.moveToNext())
			{
				importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
			}
			cursor.close();
		}
			
			importo_tot=importo_tot_us+importo_tot_en;
			
		}
		else
		{
			if(report.getPeriodo().equalsIgnoreCase("giornaliero"))
			{
				Cursor cursor=dbHelper.fetchOpEntrata(report.getData(),report.getConto());	
				while(cursor.moveToNext())
				{
					importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
				   
				}
				cursor.close();
				cursor=dbHelper.fetchOpUscita(report.getData(),report.getConto());
				while(cursor.moveToNext())
				{
					importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
				}
				cursor.close();
			}
			if(report.getPeriodo().equalsIgnoreCase("settimanale"))
			{
			Cursor cursor=dbHelper.fetchOpData(report.getPeriodoSett(),report.getConto());	
			while(cursor.moveToNext())
			{
				importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
			   
			}
			cursor.close();
			cursor=dbHelper.fetchOpUsData1(report.getPeriodoSett(),report.getConto());
			while(cursor.moveToNext())
			{
				importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
			}
			cursor.close();
			}
			if(report.getPeriodo().equalsIgnoreCase("mensile"))
			{
				Cursor cursor=dbHelper.fetchOpData(report.getPeriodoMens(report.getData()),report.getConto());	
				while(cursor.moveToNext())
				{
					importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
				   
				}
				cursor.close();
				cursor=dbHelper.fetchOpUsData1(report.getPeriodoMens(report.getData()),report.getConto());
				while(cursor.moveToNext())
				{
					importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
				}
				cursor.close();
			}
			if(report.getPeriodo().equalsIgnoreCase("annuale"))
			{
				Cursor cursor=dbHelper.fetchOpByYear(Integer.parseInt(report.getData()),report.getConto());	
				while(cursor.moveToNext())
				{
					importo_tot_en+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
				   
				}
				cursor.close();
				cursor=dbHelper.fetchOpUsByYear(Integer.parseInt(report.getData()),report.getConto());
				while(cursor.moveToNext())
				{
					importo_tot_us+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_USCITAIM)));
				}
				cursor.close();
			}
				
				importo_tot=importo_tot_us+importo_tot_en;
				
			
		}
		
        for(int i=0;i<SERIES_NR;i++)
        {
        	XYSeries series=new XYSeries(legendTitles.get(i));
        	
        	if(i!=0)
        	{	
        		perc_us=Math.round(100*importo_tot_us/importo_tot);
        	
        		series.add(1.4,perc_us);
        		series.addAnnotation("€ "+importo_tot_us+" - % "+perc_us, series.getX(0), series.getY(0));
        		dataset.addSeries(series);
        		
        		
        	}
        	//seriesClick.add(series);
        	
        	
        	else
        	{
        		
        			
    			   	
    			    perc_en=Math.round(100*importo_tot_en/importo_tot);
    			    
        			series.add(1.2,perc_en);
            		series.addAnnotation("€ "+importo_tot_en+" - % "+perc_en, series.getX(0), series.getY(0));

        		
        		//seriesClick.add(series);
        		dataset.addSeries(series);
        	}
        	Log.v("PercEn - PercUs",""+perc_en+"-"+perc_us);
        	
        }
		return dataset;
	}


	private XYMultipleSeriesRenderer getDataRenderer1() {
		// TODO Auto-generated method stub
		renderer . setAxisTitleTextSize ( 16 ) ; 
		renderer .setXLabelsPadding(80);
        renderer . setChartTitleTextSize ( 20 ) ; 
        renderer.setMarginsColor(getResources().getColor(R.color.blue));
        renderer . setLabelsTextSize ( 30 ) ; 
        renderer . setLegendTextSize ( 30 ) ; 
        renderer . setBackgroundColor(getResources().getColor(R.color.blue));
        renderer . setApplyBackgroundColor(true);
        Log.e("Aggiustare ->","getDataRenderer(1) , sistemare la grafica ed organizzare la classe");
        renderer . setMargins ( new   int [ ]   {   0 ,   0 ,   80 ,   20   } ) ; 
		return renderer;
	}
	private XYMultipleSeriesRenderer getDataRenderer() {
		// TODO Auto-generated method stub
		XYMultipleSeriesRenderer renderer=new XYMultipleSeriesRenderer();
		renderer . setAxisTitleTextSize ( 16 ) ; 
		renderer .setXLabelsPadding(40);
        renderer . setChartTitleTextSize ( 20 ) ; 
        renderer.setMarginsColor(getResources().getColor(R.color.blue));
        renderer . setLabelsTextSize ( 30 ) ; 
        renderer . setLegendTextSize ( 30 ) ; 
        renderer . setBackgroundColor(getResources().getColor(R.color.blue));
        renderer . setApplyBackgroundColor(true);
        renderer . setMargins ( new   int [ ]   {   0 ,   0 ,   40 ,   20   } ) ;
        
        XYSeriesRenderer   r   =   new   XYSeriesRenderer () ; 
        r . setColor (Color.GREEN) ; 
        r.setAnnotationsTextAlign(Align.RIGHT);
        r.setAnnotationsTextSize(20);
        renderer . addSeriesRenderer ( r ) ;
        
        r   =   new   XYSeriesRenderer ( ) ; 
        r . setColor (Color.RED ) ; 
        r.setAnnotationsTextAlign(Align.LEFT);
        r.setAnnotationsTextSize(20);
        renderer . addSeriesRenderer ( r ) ; 
		return renderer;
	}

	private XYMultipleSeriesDataset getDateRenderer() {
		// TODO Auto-generated method stub
		XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
		float importo_tot=0;
		double importo_categ=0;
		double percent=0;
		SERIES_NR=1;
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		ArrayList<String> legendTitles=new ArrayList<String>();
		ArrayList<Account.Categoria> listaCategorie=new ArrayList<Account.Categoria>();
		if(report.getConto()==-1)
		{
		if(report.getSelezione().equalsIgnoreCase("entrata")==true)
		{
			if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
			{
				Cursor cursor=dbHelper.fetchOpEnCatAndData(report.getData());
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCat(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();
			}
			else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
			{
				Cursor cursor=dbHelper.fetchOpEnCatAndDate(report.getPeriodoSett());
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCat(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();
			}
			else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
			{
				Cursor cursor=dbHelper.fetchOpEnCatAndDate(report.getPeriodoMens(report.getData()));
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCat(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();	
			}
			else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
			{
				Cursor cursor=dbHelper.fetchOpEnCatByYear(report.getData());
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCat(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();		
			}
		}
		else
		{
			if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
			{
				Log.v("Provo ad eseguire","ok");
				Cursor cursor=dbHelper.fetchOpUsCatAndData(report.getData());
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCatUs(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
					Log.v("CategoriaSize Giorn!",""+listaCategorie.size());
				}
				cursor.close();
			}
			else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
			{
				Cursor cursor=dbHelper.fetchOpUsCatAndDate(report.getPeriodoSett());
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCatUs(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();
			}
			else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
			{
				Cursor cursor=dbHelper.fetchOpUsCatAndDate(report.getPeriodoMens(report.getData()));
				Log.v("report",report.getPeriodoMens(report.getData()));
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCatUs(catID);
					while(cursor2.moveToNext())
					{
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						Log.v("nome cat",text);
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();	
			}
			else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
			{
				Cursor cursor=dbHelper.fetchOpUsCatByYear(report.getData());
				while(cursor.moveToNext())
				{
					int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
					Cursor cursor2=dbHelper.fetchCatUs(catID);
					while(cursor2.moveToNext())
					{
						
						int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
						String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
						Log.v("nome cat",text);
						int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
						int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
						listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
					}
					cursor2.close();
				}
				cursor.close();		
			}
		}
		}
		else
		{
			if(report.getSelezione().equalsIgnoreCase("entrata")==true)
			{
				if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnCatAndData(report.getData(),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCat(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnCatAndDate(report.getPeriodoSett(),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCat(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnCatAndDate(report.getPeriodoMens(report.getData()),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCat(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();	
				}
				else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnCatByYear(report.getData(),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCat(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();		
				}
			}
			else
			{
				if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
				{
					Log.v("Provo ad eseguire","ok");
					Cursor cursor=dbHelper.fetchOpUsCatAndData(report.getData(),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCatUs(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
						Log.v("CategoriaSize Giorn!",""+listaCategorie.size());
					}
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsCatAndDate(report.getPeriodoSett(),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCatUs(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsCatAndDate(report.getPeriodoMens(report.getData()),report.getConto());
					Log.v("report",report.getPeriodoMens(report.getData()));
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCatUs(catID);
						while(cursor2.moveToNext())
						{
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							Log.v("nome cat",text);
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();	
				}
				else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsCatByYear(report.getData(),report.getConto());
					while(cursor.moveToNext())
					{
						int catID=Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATACAT)));
						Cursor cursor2=dbHelper.fetchCatUs(catID);
						while(cursor2.moveToNext())
						{
							
							int _id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATID)));
							String text=cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_CATNOME));
							Log.v("nome cat",text);
							int color_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_COLORID)));
							int icon_id=Integer.parseInt(cursor2.getString(cursor2.getColumnIndex(DbAdapter.KEY_ICONID)));
							listaCategorie.add(new Account.Categoria(_id,text,color_id,icon_id));
						}
						cursor2.close();
					}
					cursor.close();		
				}
			}	
		}
		Log.v("listaCategorie.size()",""+listaCategorie.size());
		for(int i=0;i<listaCategorie.size();i++)
		{
			
			legendTitles.add(listaCategorie.get(i).getNome());
			
			XYSeriesRenderer   r   =   new   XYSeriesRenderer () ; 
	        r . setColor (listaCategorie.get(i).getColor()) ; 
	        r.setAnnotationsTextAlign(Align.CENTER);
	        r.setAnnotationsTextSize(20);
	        renderer . addSeriesRenderer ( r ) ;
		}
		
		// Calcolo gli importi in base alla categoria e alla data
		ArrayList<Double> importoCate=new ArrayList<Double>();
		Account.Operazione opz=null;
		for(int i=0;i<listaCategorie.size();i++)
		{
			if(report.getConto()==-1)
			{
			if(report.getSelezione().equalsIgnoreCase("entrata")==true)
			{
				if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnByCatAndData(listaCategorie.get(i).getId(),report.getData());
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoSett());
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoMens(report.getData()));
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();	
				}
				else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
				{
					Cursor cursor=dbHelper.fetchOpEnByCatByYear(listaCategorie.get(i).getId(),report.getData());
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();		
				}
				operazione.add(opz);
			}
			else
			{
				if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsByCatAndData(listaCategorie.get(i).getId(),report.getData());
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoSett());
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();
				}
				else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoMens(report.getData()));
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();	
				}
				else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
				{
					Cursor cursor=dbHelper.fetchOpUsByCatByYear(listaCategorie.get(i).getId(),report.getData());
					importo_categ=0;
					while(cursor.moveToNext())
					{
						importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
					    
					    Log.v("Categoria.toString",listaCategorie.get(i).toString());
					    					    
					}
					importoCate.add(importo_categ);
					opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
					cursor.close();		
				}
				operazione.add(opz);
			}
			importo_tot+=importoCate.get(i);
			}
			else
			{
				if(report.getSelezione().equalsIgnoreCase("entrata")==true)
				{
					if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
					{
						Cursor cursor=dbHelper.fetchOpEnByCatAndData(listaCategorie.get(i).getId(),report.getData(),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();
					}
					else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
					{
						Cursor cursor=dbHelper.fetchOpEnByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoSett(),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();
					}
					else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
					{
						Cursor cursor=dbHelper.fetchOpEnByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoMens(report.getData()),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();	
					}
					else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
					{
						Cursor cursor=dbHelper.fetchOpEnByCatByYear(listaCategorie.get(i).getId(),report.getData(),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();		
					}
					operazione.add(opz);
				}
				else
				{
					if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
					{
						Cursor cursor=dbHelper.fetchOpUsByCatAndData(listaCategorie.get(i).getId(),report.getData(),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();
					}
					else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
					{
						Cursor cursor=dbHelper.fetchOpUsByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoSett(),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();
					}
					else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
					{
						Cursor cursor=dbHelper.fetchOpUsByCatAndDate(listaCategorie.get(i).getId(),report.getPeriodoMens(report.getData()),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();	
					}
					else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
					{
						Cursor cursor=dbHelper.fetchOpUsByCatByYear(listaCategorie.get(i).getId(),report.getData(),report.getConto());
						importo_categ=0;
						while(cursor.moveToNext())
						{
							importo_categ+=Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM)));
						    
						    Log.v("Categoria.toString",listaCategorie.get(i).toString());
						    					    
						}
						importoCate.add(importo_categ);
						opz=new Account.Operazione(importoCate.get(i), listaCategorie.get(i));
						cursor.close();		
					}
					operazione.add(opz);
				}
				importo_tot+=importoCate.get(i);	
			}
		}
		for(int i=0;i<operazione.size();i++)
		{
			Log.v("Operazione ("+i+")",operazione.get(i).toString());
		}
		final int nr=listaCategorie.size();
		renderer.setXAxisMax(nr+1);
		
		for (int i =0 ; i<nr ;i++)
		{
			
			XYSeries series=new XYSeries(legendTitles.get(i));
			Log.v("Importo errore",""+importoCate.get(i)*100/importo_tot);
			BigDecimal var=new BigDecimal(importoCate.get(i)*100/importo_tot);
			double var_mod=Math.round(var.doubleValue()*100.0)/100.0;
			Log.v("Var_MOD",""+var_mod);
			percent=Math.round(importoCate.get(i)*100.0/importo_tot);
			series.add(i+1,var_mod);
			
			Log.v("Series",""+series.getItemCount());
			
			series.addAnnotation("€ "+importoCate.get(i)+"- % "+var_mod, series.getX(0), series.getY(0));
			dataset.addSeries(i,series);
			String nome=operazione.get(i).categoria.getNome();
			if(nome.length()>15)
			{
				String txt=nome.substring(0, 15);
				Log.e("Nome categoria",txt);
				renderer.addXTextLabel(i+1, txt);
			}
			else
			{
			renderer.addXTextLabel(i+1, nome);
			}
		    
			Log.v("Nome - Importo ",""+operazione.get(i).categoria.getNome()+" - "+operazione.get(i).getImporto());
		}
		   
		
		for(int i=0;i<renderer.getSeriesRendererCount();i++)
		{
			Log.v("Series at "+i+" ",""+dataset.getSeriesAt(i).getX(0));
		}
		
		
			
		
       
        	
		return dataset;
	}
	
	public void myChartSettings(XYMultipleSeriesRenderer renderer)
	{
		renderer.setOrientation(Orientation.HORIZONTAL);
		//renderer.setClickEnabled(true);
		renderer.setXAxisMin(0.5);
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(110);
		renderer.setXLabelsPadding(10);	
		renderer.setYLabelsAlign(Align.CENTER);
		renderer.setBarSpacing(20);
		renderer.setBarWidth(60);
		renderer.setXLabels(15);
		renderer.setXTitle("Periodo");
		renderer.setYTitle("Importo");
		renderer.setShowGrid(false);
		renderer.setGridColor(Color.GRAY);
		renderer.setXLabels(1);
		renderer.setYLabels(0);
		renderer.setXLabelsAlign(Align.CENTER);
		
		if(report.getPeriodo().equalsIgnoreCase("giornaliero")==true)
		{
			renderer.setXAxisMax(2);	
			renderer.addXTextLabel(1.3, report.getData());
		}
		else if(report.getPeriodo().equalsIgnoreCase("settimanale")==true)
		{
			renderer.setXAxisMax(2);
			String data=report.getPeriodoSettimana().get(report.getPeriodoSettimana().size()-1)+" - "+report.getPeriodoSettimana().get(0);
		    renderer.addXTextLabel(1.3, data);
		}
			else if(report.getPeriodo().equalsIgnoreCase("mensile")==true)
			{
				renderer.setXAxisMax(2);
				renderer.addXTextLabel(1.3, report.getDataOfMese());
			}
				else if(report.getPeriodo().equalsIgnoreCase("annuale")==true)
				{
					
					renderer.setXAxisMax(2);
					
						renderer.addXTextLabel(1.3,report.getData());
						
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grafico_barre, menu);
		return true;
	}

	public void onResume()
	{
		super.onResume();
		if(flag==true)
			startActivity(new Intent(this,Report.class));
	}
	
	public void onPause()
	{
		flag=true;
		super.onPause();
	}
}
