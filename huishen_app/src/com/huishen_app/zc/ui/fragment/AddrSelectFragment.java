package com.huishen_app.zc.ui.fragment;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.huishen_app.all.address.AddressAdapter;
import com.huishen_app.all.address.AddressListItem;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;

@SuppressLint({ "ValidFragment", "Instantiatable" })
public class AddrSelectFragment extends BaseFragment {

	private List<AddressListItem> allList = new ArrayList<AddressListItem>();
	private List<AddressListItem> provinceList = new ArrayList<AddressListItem>();
	private List<AddressListItem> cityList = new ArrayList<AddressListItem>();
	private List<AddressListItem> districtList = new ArrayList<AddressListItem>();

	private int state;// 1---ʡ��2---�У�3---��

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

	// �Ժ�����ʲô����
	private int type;

	public AddrSelectFragment(BaseActivity father, int type) {
		super(father);
		this.type = type;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View address = inflater.inflate(R.layout.address_lay, null);

		proText = (TextView) address.findViewById(R.id.addr_province);
		cityText = (TextView) address.findViewById(R.id.addr_city);
		addrList = (ListView) address.findViewById(R.id.addr_list);

		// ��ȡ����
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

		addrAdapter = new AddressAdapter(getActivity(), cityList);
		state = 2;
		pname = "�Ĵ�ʡ";
		pcode = "51";
		proText.setText(pname);

		addrList.setAdapter(addrAdapter);

		addrList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				String code;
				switch (state) {
				case 1:// ĿǰΪʡ
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
				case 2:// ĿǰΪ��
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
				case 3:// ĿǰΪ��
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
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("pname", pname);
						map.put("cname", cname);
						map.put("dname", dname);
						map.put("code", dcode);
						// TODO ѡ����к�� ����
						father.switchcenter(type, map);
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
		return address;
	}

	private void setState(int state) {
		this.state = state;
		refreshState();
	}

	private void refreshState() {
		switch (state) {
		case 1:// ��ʾʡ
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
		case 2:// ��ʾ��
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
		case 3:// ��ʾ��
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

	/**
	 * ��ȡ��ַ��Ϣ
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

			// ��ȡ�ַ�
			char[] buffer = new char[1002];
			while (bufReader.read(buffer, 0, 1000) > 0)
				Result.append(buffer, 0, 1000);
			info = Result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info.replaceAll("=", "");
	}

}
