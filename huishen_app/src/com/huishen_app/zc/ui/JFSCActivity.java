package com.huishen_app.zc.ui;

import java.util.Map;

import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.fragment.GuaGuaKaFragment;
import com.huishen_app.zc.ui.fragment.LuckDrawFragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class JFSCActivity extends BaseActivity {

//   private GuaGuaKa guaguaka ;
   private GuaGuaKaFragment guaguakaf ;
   private LuckDrawFragment luckdrawf ;

	@Override
	protected void findViewById_Init() {
		    guaguakaf = new GuaGuaKaFragment(this ,1) ;
		    luckdrawf = new LuckDrawFragment(this ,2);
		    FragmentManager fm = this.getSupportFragmentManager();  
	        FragmentTransaction tx = fm.beginTransaction();  
//	        tx.add(R.id.container, guaguakaf,"guaguakaf"); 
	        tx.add(R.id.container, luckdrawf,"luckdrawf"); 
	        tx.commit();
	        
		setContentView(R.layout.activity_jfsc);
	
	}

	@Override
	protected void initView() {
		
		
	}

	@Override
	protected void initData() {
		
	}
	
	@Override
	public void switchcenter(Object tag ,Map<String, Object> param) {
		FragmentManager fm = this.getSupportFragmentManager();  
        FragmentTransaction tx = fm.beginTransaction();
//		   switch((Integer)tag){
//		   case 0:
//
//			   confirminfo = new ConfirmInfoFragment(this ,1);
//		    	tx.setTransition(FragmentTransaction.TRANSIT_UNSET);
//		        tx.hide(wesu); 
////		        tx.replace(R.id.id_content, fTwo, "TWO");  
//		        tx.add(R.id.container, confirminfo, "TWO");
//		        tx.addToBackStack(null);  
//		        tx.commit();
//	        break ;
//		   case 1:
//				confirmorder = new ConfirmOrderFragment(this,2);
//		        tx.setTransition(FragmentTransaction.TRANSIT_UNSET);
//		        tx.hide(confirminfo); 
////		      tx.replace(R.id.id_content, fTwo, "TWO");  
//		        tx.add(R.id.container, confirmorder, "confirmorder");
//		        tx.addToBackStack(null);  
//		        tx.commit();
//			   break ;
//		   case 2:
//			   tx.hide(confirmorder);
//			   tx.add(R.id.container, new TipFragment(this),"tipfragment"); 
//			   tx.addToBackStack(null);
//		       tx.commit();
//			   break ;
//			   
//		   }
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
}
