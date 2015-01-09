package com.huishen_app.zc.ui;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class ShowMapActivity extends Activity implements
OnGetGeoCoderResultListener{
	   private MapView mapview ;
	   private BaiduMap baidumap ;
	   private GeoCoder mSearch ; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);
	      mapview = (MapView)this.findViewById(R.id.bmapView) ;
	        baidumap = mapview.getMap() ;
	        
			// ��ʼ������ģ�飬ע���¼�����
			mSearch = GeoCoder.newInstance();
			mSearch.setOnGetGeoCodeResultListener(this);
			
	      //����Maker�����  
	        LatLng point = new LatLng(30.575505, 104.06584); 
	        
	     // ��Geo����
	        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
	     					.location(point));
	    }
	    
	    @Override
		protected void onPause() {
			// MapView������������Activityͬ������activity����ʱ�����MapView.onPause()
	    	mapview.onPause();
			super.onPause();
		}

		@Override
		protected void onResume() {
			// MapView������������Activityͬ������activity�ָ�ʱ�����MapView.onResume()
			mapview.onResume();
			super.onResume();
		}

		@Override
		protected void onDestroy() {
			// MapView������������Activityͬ������activity����ʱ�����MapView.destroy()
			mapview.onDestroy();
			mSearch.destroy();
			super.onDestroy();
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(ShowMapActivity.this, "��Ǹ��δ���ҵ����", Toast.LENGTH_LONG)
						.show();
				return;
			}
			baidumap.clear();
			baidumap.addOverlay(new MarkerOptions().position(result.getLocation())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_gcoding)));
			baidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
					.getLocation()));
			Toast.makeText(ShowMapActivity.this, result.getAddress(),
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
			
		}
		/**
		 * ͨ�÷��ذ�ť
		 * 
		 * @param v
		 */
		public void backIntent(View v) {
			finish();
		}
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			 
	        if (keyCode == KeyEvent.KEYCODE_BACK
	                 && event.getRepeatCount() == 0) {
	             finish();
	             return true;
	         }
	         return super.onKeyDown(keyCode, event);
	     }
		
}
