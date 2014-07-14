package com.expenseManager.gestionespese;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.expenseManager.gestionespese.Activity.MainActivity;
import com.expenseManager.gestionespese.Activity.Report;
import com.expenseManager.gestionespese.Database.DbAdapter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class GraficoTorta extends Activity {
	boolean flag=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grafico_torta);
        if(flag==false)
		openChart();
        else
        	startActivity(new Intent(this,Report.class));
	}

	private void openChart() {
		// TODO Auto-generated method stub
		DbAdapter dbHelper=new DbAdapter(this);
		dbHelper.open();
		String data="'2014-01-01' and '2014-12-31'";
		ArrayList<String> value=new ArrayList<String>();
		ArrayList<Double> value_im=new ArrayList<Double>();

		Cursor cursor=dbHelper.fetchAllOpEntrata();
		while(cursor.moveToNext())
		{
			value.add(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATADESC)));
			value_im.add(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_ENTRATAIM))));
		}
		cursor.close();
		int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                Color.YELLOW ,Color.BLACK,Color.GRAY,Color.LTGRAY,Color.BLUE,Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                Color.YELLOW ,Color.BLACK,Color.GRAY,Color.LTGRAY,Color.BLUE,Color.BLUE};
Log.v("Valori trovati : ",""+value_im.size());
// Instantiating CategorySeries to plot Pie Chart
CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
for(int i=0 ;i < value_im.size();i++){
    // Adding a slice with its values and name to the Pie Chart
	
    distributionSeries.add(value.get(i), value_im.get(i));
}

// Instantiating a renderer for the Pie Chart
DefaultRenderer defaultRenderer  = new DefaultRenderer();
Log.v("I : ",""+value_im.size());
for(int i = 0 ;i<value_im.size();i++){
    SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
    seriesRenderer.setColor(colors[i]);
    // Adding a renderer for a slice
    defaultRenderer.addSeriesRenderer(seriesRenderer);
}

defaultRenderer.setBackgroundColor(getResources().getColor(R.color.blue));
defaultRenderer.setApplyBackgroundColor(true);
defaultRenderer.setLegendTextSize(25);
defaultRenderer.setLabelsTextSize(25);
defaultRenderer.setZoomButtonsVisible(false);

// Creating an intent to plot bar chart using dataset and multipleRenderer
Intent intent=ChartFactory.getPieChartIntent(this, distributionSeries , defaultRenderer, "Report Grafico Torta");

startActivity(intent);
//flag=true;
}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grafico_torta, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_grafico_torta,
					container, false);
			return rootView;
		}
	}
	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if((keyCode==KeyEvent.KEYCODE_BACK))
		startActivity(new Intent(this,Report.class));
		return super.onKeyDown(keyCode, event);
	}
	
	public void onResume()
	{
		super.onResume();
		Log.v("Class flat",""+flag);
		if(flag==true)
			startActivity(new Intent(this,Report.class));
	}
	
	public void onPause()
	{
		flag=true;
		super.onPause();
		Log.v("onpause","called");
	}

}
