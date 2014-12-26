package com.huishen_app.zc.ui;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.huishen_app.all.alarmservice.HanderListObject;
import com.huishen_app.zc.operate_thread.GetHttpResultThread;
import com.huishen_app.zc.ui.base.BaseActivity;
import com.huishen_app.zc.ui.dialog.LoadingDialog_ui;

import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class JudgeActivity extends BaseActivity implements OnRatingBarChangeListener {
	private String tag = "JudgeActivity";
	private TextView title ,quality , attitude , standard ,teacher_tv , kemu_tv ,lessonCode_tv; //标题,教学质量得分，教学态度得分，教学规范得分
	private EditText jugdecontent ; //评价内容
	private RadioGroup rg ; //评价方式
	private RatingBar rb_ql,rb_at,rb_st ;//星条
	
	 private String trainCode ,kemu ,teacher ,lessonCode; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		trainCode =  getParams("trainCode").toString();
		kemu =  getParams("kemu").toString();
		teacher =  getParams("teacher").toString();
		lessonCode =  getParams("lessonCode").toString();
		super.onCreate(savedInstanceState);
	}


	@Override
	protected void findViewById_Init() {
		setContentView(R.layout.activity_judge);

		rb_ql = (RatingBar) findViewById(R.id.jugde_rb_ql);
		rb_at = (RatingBar) findViewById(R.id.jugde_rb_at);
		rb_st = (RatingBar) findViewById(R.id.jugde_rb_st);
		
        title = (TextView) findViewById(R.id.header_title);
        quality = (TextView) findViewById(R.id.jugde_quality);
        attitude = (TextView) findViewById(R.id.jugde_attitude);
        standard = (TextView) findViewById(R.id.jugde_standard);
        teacher_tv  = (TextView) findViewById(R.id.judge_teacher);
        kemu_tv  = (TextView) findViewById(R.id.judge_kemu);
        lessonCode_tv  = (TextView) findViewById(R.id.judge_lessonCode);
        
        
        jugdecontent = (EditText) findViewById(R.id.jugde_content);
        rg = (RadioGroup) findViewById(R.id.jugde_rdgroup);
	}
   
	
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
            switch(ratingBar.getId()){
            case R.id.jugde_rb_ql:
            	if(rating == 0){
                	quality.setText("0分");
            	}else{
            	quality.setText(Html.fromHtml("<font color =\"#ff0000\">"+rating+"分</font>"));
            	}
            	break;
            case R.id.jugde_rb_at:
            if(rating == 0){
            	attitude.setText("0分");
        	}else{
        		attitude.setText(Html.fromHtml("<font color =\"#ff0000\">"+rating+"分</font>"));
        	}
            	break;
            case R.id.jugde_rb_st:
            	 if(rating == 0){
            		 standard.setText("0分");
             	}else{
             		standard.setText(Html.fromHtml("<font color =\"#ff0000\">"+rating+"分</font>"));
             	}
            	break;
            };
            

	}
	@Override
	protected void initView() {
        title.setText("课程评价");
        teacher_tv.setText("教练："+teacher);
        kemu_tv.setText("课程："+kemu);
        lessonCode_tv.setText("培训代码："+lessonCode);
        
        rb_ql.setOnRatingBarChangeListener(this);
        rb_at.setOnRatingBarChangeListener(this);
        rb_st.setOnRatingBarChangeListener(this);
        rg.check(R.id.jugde_rb_1);
	}

	@Override
	protected void initData() {
		
	}
	/**
	 * 通用返回按钮
	 * 
	 * @param v
	 */
	public void backIntent(View v) {
		finish();
	}
	/**
	 * 提交按钮事件响应
	 */
	public void jugdeCommit(View v){
		if(rb_ql.getRating() == 0){
			Toast.makeText(this, "请对教学质量评分", Toast.LENGTH_SHORT).show();
			return;
		}
		if(rb_at.getRating() == 0){
			Toast.makeText(this, "请对教学态度评分", Toast.LENGTH_SHORT).show();
			return;
		}
		if(rb_st.getRating() == 0){
			Toast.makeText(this, "请对教学规范评分", Toast.LENGTH_SHORT).show();
			return;
		}
		//如何处理评星0分问题
		
		//提交评价
		
		String operurl = getOperateURL(R.string.webbaseurl,
				R.string.submit_judgeurl);
		// 登录10s超时 且只收一条消息
		HanderListObject commithander = new HanderListObject(10, true) {
			public void handleMessage(Message msg) {
				if (msg.what == 13) {

					// 保存对象
					Main_fragment_ui.setLoginhttpclient(null);
					Toast.makeText(JudgeActivity.this, "对不起，您已下线", Toast.LENGTH_SHORT)
							.show();
				} else 
				if (msg.what == 11) {
					// 登录信息
					JSONObject jobj = JSONObject.fromObject(msg.obj.toString());
					DisPlay("评价成功",true);
					JudgeActivity.this.finish();

				} else {
					Toast.makeText(JudgeActivity.this, msg.obj + "",
							Toast.LENGTH_SHORT).show();
				}

				// 关闭
				if (loading != null) {
					loading.dismiss();
					loading = null;
				}
			}
		};
		// 初始化参数
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("lessonInfoId", trainCode);
		param.put("isHide", (rg.getCheckedRadioButtonId() == R.id.jugde_rb_1)?1:2);
		param.put("content", jugdecontent.getText());
		param.put("qualityScore", rb_ql.getRating());
		param.put("serviceScore", rb_at.getRating());
		param.put("ruleScore", rb_st.getRating());
		param.put("mobileFlag", readString("mobileFlag"));
		param.put("operateurl", operurl);
		param.put("encoding", getStringValue(R.string.encoding));
		// 新建操作对象
		GetHttpResultThread login = new GetHttpResultThread(commithander, param);

		// 注册监听
		commithander.addtolisttask(login);

		loading = new LoadingDialog_ui(this, R.style.loadingstyle,
				R.layout.dialog_loading_lay);
		loading.setCancelable(false);
		loading.show();
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
