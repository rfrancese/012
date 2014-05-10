package com.expenseManager.gestionespese.Utility;

import java.util.Calendar;

import android.widget.TextView;

public class Calendario {
public Calendario(TextView var,int Month,int Year,int Day)
{
	switch(Month)
	{
	case 0: var.setText("Gennaio,"+Day+","+Year);break;
	case 1: var.setText("Febbraio,"+Day+","+Year);break;
	case 2: var.setText("Marzo,"+Day+","+Year);break;
	case 3: var.setText("Aprile,"+Day+","+Year);break;
	case 4: var.setText("Maggio,"+Day+","+Year);break;
	case 5: var.setText("Giugno,"+Day+","+Year);break;
	case 6: var.setText("Luglio,"+Day+","+Year);break;
	case 7: var.setText("Agosto,"+Day+","+Year);break;
	case 8: var.setText("Settembre,"+Day+","+Year);break;
	case 9: var.setText("Ottobre,"+Day+","+Year);break;
	case 10: var.setText("Novembre,"+Day+","+Year);break;
	case 11: var.setText("Dicembre,"+Day+","+Year);break;
	}
}
}
