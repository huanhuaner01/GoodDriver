package com.huishen_app.zc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class MainActivity extends Activity {
   private ImageView icon , title ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon = (ImageView)findViewById(R.id.loadding_icon);
        title = (ImageView)findViewById(R.id.loadding_title);
//        RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT ,LayoutParams.MATCH_PARENT/3);
//        icon.setLayoutParams(rp);
//        icon.setImageResource(R.drawable.loadding_icon);
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 3000);
        
    }
    class splashhandler implements Runnable{

        public void run() {
            startActivity(new Intent(getApplication(),Main_fragment_ui.class));
            MainActivity.this.finish();
        }
        
    }
}
