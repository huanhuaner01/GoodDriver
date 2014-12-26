package com.huishen_app.all.address;

import java.util.List;

import com.huishen_app.zc.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {

	private Context context;
	private List<AddressListItem> addrList;

	public void setAddrList(List<AddressListItem> addrList) {
        this.addrList = addrList;
    }

    public AddressAdapter(Context context, List<AddressListItem> myList) {
		this.context = context;
		this.addrList = myList;
	}

	public int getCount() {
		return addrList.size();
	}

	public Object getItem(int position) {
		return addrList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
	    View currentView = convertView;
	    TextView addrTxt;
	    if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentView = vi.inflate(R.layout.address_item, null);
        }
	    addrTxt = (TextView) currentView.findViewById(R.id.addr_item_txt);
	    AddressListItem myListItem = addrList.get(position);
	    /*if(myListItem.isSelected){
	    	lastOk.setVisibility(View.VISIBLE);
	    }else{
	    	lastOk.setVisibility(View.INVISIBLE);
	    }*/
	    addrTxt.setText(myListItem.getName());
		return currentView;
	}

}