package com.expenseManager.gestionespese;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class GraficoBarre1 extends AbstractDemoChart {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intent execute(Context context) {
		// TODO Auto-generated method stub
		String[] titles = new String[] { "2007", "2008" };
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(new double[] { 5230, 7300, 9240, 10540, 7900, 9200, 12030, 11200, 9500, 10500,
	        11600, 13500 });
	    values.add(new double[] { 14230, 12300, 14240, 15244, 15900, 19200, 22030, 21200, 19500, 15500,
	        12600, 14000 });
	    int[] colors = new int[] { Color.CYAN, Color.BLUE };
	    XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
	    renderer.setOrientation(Orientation.VERTICAL);
	    setChartSettings(renderer, "Monthly sales in the last 2 years", "Month", "Units sold", 0.5,
	        12.5, 0, 24000, Color.GRAY, Color.LTGRAY);
	    renderer.setXLabels(1);
	    renderer.setYLabels(10);
	    renderer.addXTextLabel(1, "Jan");
	    renderer.addXTextLabel(3, "Mar");
	    renderer.addXTextLabel(5, "May");
	    renderer.addXTextLabel(7, "Jul");
	    renderer.addXTextLabel(10, "Oct");
	    renderer.addXTextLabel(12, "Dec");
	    int length = renderer.getSeriesRendererCount();
	    for (int i = 0; i < length; i++) {
	      SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
	      seriesRenderer.setDisplayBoundingPoints(true);
	    }
	    return ChartFactory.getBarChartIntent(context, buildBarDataset(titles, values), renderer,
	        Type.DEFAULT);
	  }
	}


