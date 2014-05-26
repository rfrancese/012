package com.expenseManager.gestionespese;

import com.expenseManager.gestionespese.Activity.CategoriaEntrata;
import com.expenseManager.gestionespese.Activity.Mappe;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
	public HomeFragment(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        final View rootView = inflater.inflate(R.layout.fragment_altro, container, false);
        Button altro=(Button)rootView.findViewById(R.id.btn_addCaten);
        altro.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent altro=new Intent(rootView.getContext(),CategoriaEntrata.class);
				startActivity(altro);
			}
		});
        Button altro1=(Button)rootView.findViewById(R.id.btn_addMappa);
        altro1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
              Intent altro=new Intent(rootView.getContext(),Mappe.class);
              startActivity(altro);
			}
		});
        return rootView;
    }
}
