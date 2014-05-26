package com.expenseManager.gestionespese.Utility;

import java.util.HashMap;
import java.util.List;

import com.expenseManager.gestionespese.R;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;



public class ExpandableListAdapter extends BaseExpandableListAdapter {
	
	private Context _context;
	private List<String> _listDataHeader;
	private HashMap<String,List<Integer>> _childImage,_childColor;
	private HashMap<String,List<String>> _childCat;
    private TextView categoria;
    private ImageView image;
    private ExpandableListView opt;
	public ExpandableListAdapter(Context context,List<String> listDataHeader,HashMap<String,List<Integer>>childImage,HashMap<String,List<String>>childCateg,HashMap<String,List<Integer>>childColor,TextView categoria,ImageView imagine,ExpandableListView list)
	{
		this._context=context;
		this._listDataHeader=listDataHeader;
		this._childImage=childImage;
		this._childCat=childCateg;
		this._childColor=childColor;
		this.categoria=categoria;
		this.image=imagine;
		this.opt=list;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		switch(groupPosition)
		{
		case 0: return this._childImage.get(this._listDataHeader.get(groupPosition)).get(childPosition);
		case 1: return this._childCat.get(this._listDataHeader.get(groupPosition)).get(childPosition);
		case 2: return this._childColor.get(this._listDataHeader.get(groupPosition)).get(childPosition);
		}
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.v("Group Position id",""+getChildType(groupPosition, childPosition));
            switch(groupPosition)
            {
            case 0: convertView = infalInflater.inflate(R.layout.list_icon, null);
                    layoutCategoria cat=new layoutCategoria(convertView,image,opt);
                    
                    break;
            case 1: convertView = infalInflater.inflate(R.layout.nome_categoria, null);
                    final EditText text=(EditText)convertView.findViewById(R.id.edit_nome_cat);/*TextView text1=(TextView)convertView.findViewById(R.id.cattext)*/;
                    text.setText(categoria.getText().toString());
                    text.requestFocus();
                    //text.setFocusable(true);
                    Button button=(Button)convertView.findViewById(R.id.conferma);
                    button.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						categoria.setText(text.getText().toString());
						opt.collapseGroup(1);
						opt.expandGroup(2);
						}
					});
                    break;
            case 2: convertView = infalInflater.inflate(R.layout.list_color, null);/*ScrollView scroll=new ScrollView(convertView.getContext());scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));scroll.setFillViewport(true);*/TextView text_color=(TextView)convertView.findViewById(R.id.color);text_color.setBackgroundColor((Integer)getChild(groupPosition,childPosition));break;
            }
        }
		else
		{
			LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			switch(groupPosition)
            {
            case 0: convertView = infalInflater.inflate(R.layout.list_icon, null);new layoutCategoria(convertView,image,opt);break;
            case 1: convertView = infalInflater.inflate(R.layout.nome_categoria, null);
            final EditText text=(EditText)convertView.findViewById(R.id.edit_nome_cat);/*TextView text1=(TextView)convertView.findViewById(R.id.cattext)*/;
            text.setText(categoria.getText().toString());
            text.setSelection(text.getText().length());
            Log.v("Request Focus",""+text.requestFocus(EditText.FOCUS_FORWARD));
            Button button=(Button)convertView.findViewById(R.id.conferma);
            button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				categoria.setText(text.getText().toString());
				text.setFocusable(true);
				InputMethodManager imm=(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
				opt.collapseGroup(1);
				opt.expandGroup(2);
				}
			});
            break;
            case 2: convertView = infalInflater.inflate(R.layout.list_color, null);/*ScrollView scroll=new ScrollView(convertView.getContext());scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));scroll.setFillViewport(true);*/TextView text_color=(TextView)convertView.findViewById(R.id.color);text_color.setBackgroundColor((Integer)getChild(groupPosition,childPosition));break;
            }
		}
	
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		switch(groupPosition)
		{
		case 0: return this._childImage.get(this._listDataHeader.get(groupPosition)).size();
		case 1: return this._childCat.get(this._listDataHeader.get(groupPosition)).size();
		case 2: return this._childColor.get(this._listDataHeader.get(groupPosition)).size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String headerTitle=(String)getGroup(groupPosition);
		if(convertView == null)
		{
			LayoutInflater infalInflater=(LayoutInflater)this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=infalInflater.inflate(R.layout.list_group, null);
		}
		TextView lblListHeader=(TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
