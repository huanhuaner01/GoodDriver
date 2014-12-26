package com.huishen_app.all.address;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.huishen_app.zc.ui.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class Address extends Activity {
	private List<AddressListItem> allList = new ArrayList<AddressListItem>();
	private List<AddressListItem> provinceList = new ArrayList<AddressListItem>();
	private List<AddressListItem> cityList = new ArrayList<AddressListItem>();
	private List<AddressListItem> districtList = new ArrayList<AddressListItem>();

	private int state;// 1---省，2---市，3---县

	private TextView proText;
	private TextView cityText;

	private String pname = null;
	private String cname = null;
	private String dname = null;
	private String pcode = null;
	private String ccode = null;
	private String dcode = null;
	private ListView addrList;
	private AddressAdapter addrAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.address_lay);

		proText = (TextView) findViewById(R.id.addr_province);
		cityText = (TextView) findViewById(R.id.addr_city);
		addrList = (ListView) findViewById(R.id.addr_list);

		// 读取数据
		try {
			InputStream is = new ByteArrayInputStream(getArea().getBytes());
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(is));
			String line = "";
			String name = null, code = null;

			line = bufReader.readLine();// pass the first line

			while ((line = bufReader.readLine()) != null) {

				int len = line.length();
				if (len >= 6) {
					code = line.substring(0, 6);
					name = line.substring(6, len);

					AddressListItem addrListItem = new AddressListItem();
					addrListItem.setName(name);
					addrListItem.setCode(code);
					allList.add(addrListItem);

					if (code.regionMatches(2, "0000", 0, 4)) {
						provinceList.add(addrListItem);
					}

					if (code.regionMatches(0, "51", 0, 2)
							&& !code.regionMatches(2, "00", 0, 2)
							&& code.regionMatches(4, "00", 0, 2)) {
						cityList.add(addrListItem);
					}
				}
			}

		} catch (IOException e) {
		}

		// addrAdapter = new AddressAdapter(this, provinceList);
		// state = 1;

		addrAdapter = new AddressAdapter(this, cityList);
		state = 2;
		pname = "四川省";
		pcode = "51";

		proText.setText(pname);
		addrList.setAdapter(addrAdapter);

		addrList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				String code;
				switch (state) {
				case 1:// 目前为省
					pname = ((AddressListItem) adapterView
							.getItemAtPosition(position)).getName();
					pcode = ((AddressListItem) adapterView
							.getItemAtPosition(position)).getCode();
					proText.setText(pname);
					cityList.clear();
					boolean city0,
					city1,
					city2;
					for (AddressListItem item : allList) {
						code = item.getCode();
						city0 = code.regionMatches(0, pcode, 0, 2);
						city1 = code.regionMatches(2, "00", 0, 2);
						city2 = code.regionMatches(4, "00", 0, 2);
						if (city0 && !city1 && city2) {
							item.isSelected = false;
							cityList.add(item);
						}
					}
					;
					setState(2);
					break;
				case 2:// 目前为市
					cname = ((AddressListItem) adapterView
							.getItemAtPosition(position)).getName();
					ccode = ((AddressListItem) adapterView
							.getItemAtPosition(position)).getCode();
					cityText.setText(cname);
					districtList.clear();
					boolean district0,
					district1;
					for (AddressListItem item : allList) {
						code = item.getCode();
						district0 = code.regionMatches(0, ccode, 0, 4);
						district1 = code.regionMatches(4, "00", 0, 2);
						if (district0 && !district1) {
							item.isSelected = false;
							districtList.add(item);
						}
					}
					;
					setState(3);
					break;
				case 3:// 目前为县
						// okBtn.setVisibility(View.VISIBLE);
					for (AddressListItem item : districtList) {
						item.isSelected = false;
					}
					AddressListItem selectedItem = ((AddressListItem) adapterView
							.getItemAtPosition(position));
					dname = selectedItem.getName();
					dcode = selectedItem.getCode();
					selectedItem.isSelected = true;
					addrAdapter.notifyDataSetChanged();
					if (dname != null) {
						// Intent intent = new
						// Intent(Address.this,MainActivity.class);
						Intent intent = new Intent();
						intent.putExtra("pname", pname);
						intent.putExtra("cname", cname);
						intent.putExtra("dname", dname);
						intent.putExtra("code", dcode);
						Address.this.setResult(RESULT_OK, intent);
						// 当调用finsh()方法时，这个intent就会传递回调用这个Activity的源Activity的onActivityResult()方法里
						Address.this.finish();
					}
					break;
				default:
					break;
				}
			}
		});

		proText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setState(1);
			}
		});

		cityText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setState(2);
			}
		});
	}

	private void setState(int state) {
		this.state = state;
		refreshState();
	}

	private void refreshState() {
		switch (state) {
		case 1:// 显示省
			proText.setVisibility(View.GONE);
			cityText.setVisibility(View.GONE);
			addrAdapter.setAddrList(provinceList);
			/*
			 * addrList.setSelection(0); addrList.smoothScrollToPosition(0);
			 * addrList.setSelectionAfterHeaderView();
			 * addrList.setSelectionFromTop(0, 0);
			 */
			addrAdapter.notifyDataSetChanged();
			break;
		case 2:// 显示市
			proText.setVisibility(View.VISIBLE);
			cityText.setVisibility(View.GONE);
			addrAdapter.setAddrList(cityList);
			/*
			 * addrList.setSelection(0); addrList.smoothScrollToPosition(0);
			 * addrList.setSelectionAfterHeaderView();
			 * addrList.setSelectionFromTop(0, 0);
			 */
			addrAdapter.notifyDataSetChanged();
			break;
		case 3:// 显示县
			proText.setVisibility(View.VISIBLE);
			cityText.setVisibility(View.VISIBLE);
			addrAdapter.setAddrList(districtList);
			/*
			 * addrList.setSelection(0); addrList.smoothScrollToPosition(0);
			 * addrList.setSelectionAfterHeaderView();
			 * addrList.setSelectionFromTop(0, 0);
			 */
			addrAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	// 返回按钮
	public void backIntent(View source) {
		finish();
	}

	/**
	 * 获取地址信息
	 * 
	 * @return
	 */
	private String getArea() {
		String info = "";
		try {
			StringBuffer Result = new StringBuffer();
			InputStreamReader inputReader = new InputStreamReader(
					getResources().getAssets().open("addr.txt"));
			BufferedReader bufReader = new BufferedReader(inputReader);

			// 读取字符
			char[] buffer = new char[1002];
			while (bufReader.read(buffer, 0, 1000) > 0)
				Result.append(buffer, 0, 1000);
			info = Result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("", info.replaceAll("=", ""));
		return info.replaceAll("=", "");
	}

}
