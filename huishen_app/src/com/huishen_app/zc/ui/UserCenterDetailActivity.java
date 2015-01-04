package com.huishen_app.zc.ui;

import net.sf.json.JSONObject;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.huishen_app.all.mywidget.RoundImageView;
import com.huishen_app.zc.ui.R;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;
import com.huishen_app.zc.ui.fragment.UserCenterUpdateFragment;
import com.huishen_app.zh.netTool.AppController;
import com.huishen_app.zh.netTool.NetUtil;

public class UserCenterDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView name,sex,license,idnum,tel,addr ,header_title; //���˻�����Ϣ
    private RoundImageView img ;//ͷ��
    private Button unlogin  ; //ע��
    private LinearLayout telLay ,addrLay ,infoLay,updateLay; //�绰���벼�֣���ַ����,������Ϣ���֣��޸Ĳ���
    
	public final static int LOGINCODE = 1000;
	public final static String LoginstateName = "loginState";
	private String TAG = "UserCenterDetailActivity";

	/** ��¼��־ */
	private boolean logintrage = true;
	
	private LoadingDialog_ui dialog ; //���ؿ�
	private UserCenterUpdateFragment fragment ; //�޸Ľ��沼��
    
	protected void findViewById_Init() {
		setContentView(R.layout.user_center_detail_lay);
        name = (TextView) this.findViewById(R.id.userdetail_name);
        sex = (TextView) this.findViewById(R.id.userdetail_sex);
        license = (TextView) this.findViewById(R.id.userdetail_license);
        idnum = (TextView) this.findViewById(R.id.userdetail_idnum);
        tel = (TextView) this.findViewById(R.id.userdetail_tel);
        addr = (TextView) this.findViewById(R.id.userdetail_addr);
        img = (RoundImageView) this.findViewById(R.id.userdetail_img) ;
        unlogin = (Button) this.findViewById(R.id.userdetail_unlogin);
        
        header_title = (TextView) this.findViewById(R.id.header_title);
        
        telLay = (LinearLayout) this.findViewById(R.id.userdetail_l_tel);
        addrLay = (LinearLayout) this.findViewById(R.id.userdetail_l_addr) ;
        infoLay = (LinearLayout) this.findViewById(R.id.userdetail_info_l);
        updateLay = (LinearLayout) this.findViewById(R.id.userdetail_update_l);
	}

	protected void initView() {
           unlogin.setOnClickListener(this);
           telLay.setOnClickListener(this);
           addrLay.setOnClickListener(this);
	}

	protected void initData() {
		header_title.setText("������Ϣ");
        JSONObject json = JSONObject.fromObject(this.readString("login_result"));
        name.setText(json.getString("stuname"));
        sex.setText(json.getBoolean("sex")?"��":"Ů");
        license.setText(json.optString("DrivingLicenceType","����"));
        idnum.setText(json.optString("licenceCode","����"));
        tel.setText(json.getString("phone"));
        addr.setText(json.optString("address","����"));
        //ͼƬ����
        getUserPhoto(readString("path"));
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.userdetail_unlogin:
			unLogin();
			break ;
		case R.id.userdetail_l_addr:
			switchcenter(2);
			break;
		case R.id.userdetail_l_tel:
			switchcenter(1);
			break ;
		}
		
	}
	
	/**
	 * ע����ť����
	 */
	public void unLogin(){
		// �������
		Main_fragment_ui.setLoginhttpclient(null);
		this.logintrage = false ;
		finish();
	}

	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra(LoginstateName, logintrage);
		setResult(Login_ui.LOGINCODE, data);
		super.finish();
	} 
	
	/**
	 * ͨ�÷��ذ�ť
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	
	
	//�л�����
	@Override
	public void switchcenter(int index) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		switch(index){
		case 0: //��ʾ������Ϣ
			infoLay.setVisibility(View.VISIBLE);
			updateLay.setVisibility(View.GONE);
			transaction.remove(fragment);
			transaction.commit();
		 break;
		case 1: //��ʾ�绰�����޸�ҳ��
			fragment = new UserCenterUpdateFragment(this,0);
			infoLay.setVisibility(View.GONE);
			updateLay.setVisibility(View.VISIBLE);
			transaction.replace(R.id.userdetail_update_l, fragment);
			transaction.commit();
			break;
		case 2: //��ʾ��ַ�޸�ҳ��
			fragment = new UserCenterUpdateFragment(this,1);
			infoLay.setVisibility(View.GONE);
			updateLay.setVisibility(View.VISIBLE);
			transaction.replace(R.id.userdetail_update_l, fragment);
			transaction.commit();
			break;
		case 3: //�޸ĳɹ�
			updateInfo();
			infoLay.setVisibility(View.VISIBLE);
			updateLay.setVisibility(View.GONE);
			transaction.remove(fragment);
			transaction.commit();
		}
		super.switchcenter(index);
	}
	
    /**  ���µ�¼��ȡ������Ϣ*/
	private void updateInfo() {
		initData(); 
	}

	//ͼƬ����
	public void getUserPhoto(String path){
		
		if(path.equals("")){
			Log.i(TAG, "ͷ��Ϊ��");
			return ;
			
		}
		if(!NetUtil.isNetworkConnected(this)){
			Toast.makeText(this, "����δ����", Toast.LENGTH_SHORT).show();
			return ;
		}
		String url = getString(R.string.webbaseurl)+path;
		Log.i(TAG, "url:"+url);
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		dialog = new LoadingDialog_ui(UserCenterDetailActivity.this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		dialog.setCancelable(false);
		dialog.show();
		// If you are using normal ImageView
		imageLoader.get(url, new ImageListener() {

		    @Override
		    public void onErrorResponse(VolleyError error) {
		    	dialog.dismiss();
		    }

		    @Override
		    public void onResponse(ImageContainer response, boolean arg1) {
		        if (response.getBitmap() != null) {
		            // load image into imageview
		            img.setImageBitmap(response.getBitmap());
		            dialog.dismiss();
		        }
		    }
		});
		Log.i("rt543tertr", "url"+url);
	}
}