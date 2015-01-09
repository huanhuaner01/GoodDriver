package com.huishen_app.zh.sortlistTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.huishen_app.zc.ui.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SectionIndexer;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> list = null;
	private Context mContext;
	private ArrayList<HashMap<String, String>> precities;
	private String gpscity;
    private OnItemClickListener gridviewlistener = null; //���ų����б����
    private OnClickListener gpsButtonlitener = null; //��λ���м���
//    private boolean hide = false ; //��һ�����ڶ�������
    private final int TYPE_COUNT=3;  
    private final int GPS_TYPE=0;  
    private final int GRIDVIEW_TYPE=1; 
    private final int DEFAULT_TYPE = 2 ;
    private LayoutInflater inflater; 
    LocationClient mLocationClient = null;
  
	public SortAdapter(Context mContext, List<SortModel> list,
			ArrayList<HashMap<String, String>> precities) {
		this.mContext = mContext;
		this.list = list;
		if(list == null){
		this.list = new ArrayList<SortModel>();
	     }
		mLocationClient = new LocationClient(this.mContext.getApplicationContext());     //����LocationClient��
		this.precities = precities;
		inflater = LayoutInflater.from(mContext) ;
	}
    
	//���ö�λ���а�ť����
	public void setGpsItemListener(OnClickListener gpsbuttonlistener){
		this.gpsButtonlitener = gpsbuttonlistener ;
	}
	/**
	 * �������ų��м���
	 * @param gpsbuttonlistener ������
	 */
	public void setGridViewItemListener(OnItemClickListener gridViewlistener){
		this.gridviewlistener = gridViewlistener ;
	}
	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
		this.list = list;
		if(list == null){
		this.list = new ArrayList<SortModel>();
	}
//		this.hide = hide ;
		notifyDataSetChanged();
	}

	public void stopGPS(){
		if(mLocationClient!=null&&mLocationClient.isStarted()){
			mLocationClient.stop();
		}
	}
	
	@Override
	public int getCount() {
			return this.list.size() + 2;

	}
   
	@Override
	public Object getItem(int position) {
			if (position == 0) {
				return gpscity;
			}
			if (position == 1) {
				return precities;
			}
			return list.get(position - 2);
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder_default holder1 = null;  
		ViewHolder_gisView holder2 = null;  
		ViewHolder_gridView holder3 = null; 
		Log.i("��", "total:"+list.size()+" position:"+position);
	        int type = getItemViewType(position);  
	        if (convertView == null) {  
	            // ����ǰ�������ʽ��ȷ��new�Ĳ���  
	            switch (type) {  
	            case GPS_TYPE:  
	  
	                convertView = inflater.inflate(R.layout.addr_item,
	        				null); 
	                holder2 = new ViewHolder_gisView();  
	                holder2.des= (TextView) convertView  
	                        .findViewById(R.id.des_tv);  
	                holder2.city = (TextView)convertView.findViewById(R.id.gps_city);  
	                convertView.setTag(holder2);  
	                break;  
	            case GRIDVIEW_TYPE:  
	                convertView = inflater.inflate(R.layout.gps_gridview,  
	                		arg2, false);  
	                holder3 = new ViewHolder_gridView();  
	                holder3.gridview = (GridView) convertView  
	                        .findViewById(R.id.gridview);    
	                convertView.setTag(holder3);  
	                break;  
	            case DEFAULT_TYPE:  
	                convertView = inflater.inflate(R.layout.location_listitem,  
	                        arg2, false);  
	                holder1 = new ViewHolder_default();  
	                holder1.tvTitle = (TextView) convertView
							.findViewById(R.id.title);
	                holder1.tvLetter = (TextView) convertView
							.findViewById(R.id.catalog);  
	                convertView.setTag(holder1);  
	                break;  
	            default:  
	                break;  
	            }  
	  
	        } else {  
	            switch (type) {  
	            case DEFAULT_TYPE:  
	                holder1 = (ViewHolder_default) convertView.getTag();  
	                break;  
	            case GPS_TYPE:  
	                holder2 = (ViewHolder_gisView) convertView.getTag();  
	                break;  
	            case GRIDVIEW_TYPE:  
	                holder3 = (ViewHolder_gridView) convertView.getTag();  
	                break;  
	            }  
	        }  
	        // ������Դ  
	        switch (type) {  
	        case DEFAULT_TYPE:  
	        	initDefaultView(holder1,position);
	            
	            break;  
	        case GPS_TYPE:
	        	initgpsView(holder2);
	            break;  
	        case GRIDVIEW_TYPE:  
	        	initgridView(holder3);
	            break;  
	        }  
	  
	        return convertView; 
	}
	/**
	 * ��ʼ����һ��gps��ť����ʾ����λ
	 * @param view
	 */
	private void initgpsView(final ViewHolder_gisView gisholder){
		gisholder.city.setText("����");
		gisholder.des.setText("���ڶ�λ...");
		if(this.gpsButtonlitener != null){
			gisholder.city.setOnClickListener(gpsButtonlitener);
		}
		Log.i("BaiduLocationApiDem", "���ڶ�λ");
		BDLocationListener myListener = new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				//Receive Location 
				StringBuffer sb = new StringBuffer(256);
				if (location.getLocType() == BDLocation.TypeNetWorkLocation){
					sb.append(location.getCity());
				}
				gisholder.city.setText(sb.toString());
				gisholder.des.setText("��ǰ��λ����");
				Log.i("BaiduLocationApiDem", sb.toString());
			}

			@Override
			public void onReceivePoi(BDLocation arg0) {
			
			}
		};
//		    mLocationClient = new LocationClient(this.mContext.getApplicationContext());     //����LocationClient��
		    mLocationClient.registerLocationListener( myListener );    //ע���������
		    
		    LocationClientOption option = new LocationClientOption();
//		    option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ
		    option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		    option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		    option.setOpenGps(true);
		    option.disableCache(true);//��ֹ���û��涨λ
		    option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
		    mLocationClient.setLocOption(option); 
		    if(!mLocationClient.isStarted()){
		    mLocationClient.start();
		    }
		    Log.i("BaiduLocationApiDem", "Ӧ���ڶ�λ");
//		return view ;
	}
	/**
	 * ��ʼ������б�
	 * @param view
	 */
	private void initgridView(ViewHolder_gridView gridviewholder){
		if(precities == null){
			precities = new ArrayList<HashMap<String,String>>();
		}
//		ArrayList<HashMap<String,String>> listdata = new ArrayList<HashMap<String,String>>();
//		for(int i= 0 ; i<10 ; i++){
//			HashMap<String ,String> map = new HashMap<String,String>();
//			map.put("gridview_tv", "�ɶ�");
//			listdata.add(map);
//		}
		SimpleAdapter adapter = new SimpleAdapter(mContext,precities,R.layout.gridview_item,new String[]{"gridview_tv"},new int[]{R.id.gridview_tv});
		gridviewholder.gridview.setAdapter(adapter);
		if(this.gridviewlistener != null){
			gridviewholder.gridview.setOnItemClickListener(this.gridviewlistener);
		}
//		return view;
	}
	/** ��ʼ��Ĭ�ϵ�item */
	private void initDefaultView(ViewHolder_default holder,int position){
		if(position<2){
			return ;
		}
		SortModel mContent = list.get(position-2);
		
		// ����position��ȡ���������ĸ��Char asciiֵ
		int section = getSectionForPosition(position-2);
        Log.i("��һ��","name:"+ mContent.getName()+" letter:"+mContent.getSortLetters());
		// �����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
		if (position-2 == getPositionForSection(section)) {
			holder.tvLetter.setVisibility(View.VISIBLE);
			holder.tvLetter.setText(mContent.getSortLetters());
		} else {
			holder.tvLetter.setVisibility(View.GONE);
		}
        
		holder.tvTitle.setText(mContent.getName());

//		return view;
	}
	
	final static class ViewHolder_default {
		TextView tvLetter;
		TextView tvTitle;
	}
    final static class ViewHolder_gisView{
		TextView des;
		TextView city;
    }
    final static class ViewHolder_gridView{
    	GridView gridview ;
    }
    ///////////////////////////////////////////////////////  
    @Override 
    public int getViewTypeCount() {  
        return TYPE_COUNT;  
    }  
       
    @Override 
    public int getItemViewType(int position) {  
        if (position==0) {  
            return GPS_TYPE;  
        } if(position == 1){
        	return GRIDVIEW_TYPE ;
        }
        else {  
            return DEFAULT_TYPE;  
        }  
    }  
   /////////////////////////////////////////////////////// 
	
	
	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < list.size(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

}