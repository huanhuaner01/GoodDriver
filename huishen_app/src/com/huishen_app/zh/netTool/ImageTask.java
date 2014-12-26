package com.huishen_app.zh.netTool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
/**
 * 用于获取网络图片的异步线程
 * @author Zhanghuan
 * @create 2014-3-25
 * @version 1.0
 */
public class ImageTask extends AsyncTask<String, Integer, Bitmap>{  
    
	private ImageTUpdate taskUpdate = null;
    public ImageTask(ImageTUpdate listUpate) {
    	
        super();  
        this.taskUpdate = listUpate;
    }  
  
  
    @Override  
    protected void onPreExecute() {  
    	taskUpdate.setProgressStart();
         super.onPreExecute();  
    }  
  
    @Override  
    protected Bitmap doInBackground(String... params) {//输入编变长的可变参数 和ＵＩ线程中的Asyna.execute()对应  
    /* 
     * 该方法在OnpreExecute执行以后马上执行，改方法执行在后台线程当中，负责耗时的计算，可以调用publishProcess方法来实时更新任务进度 
     */  
        Bitmap bitmap=null;  
        try {  
             URL url=new URL(params[0]); 
             HttpURLConnection connection=(HttpURLConnection) url.openConnection();   
             connection.setRequestMethod("GET");
             connection.setConnectTimeout(6*1000);
             //不超过10秒
             Log.i("ImageTask", ""+connection.getResponseCode());
             if(connection.getResponseCode() == 200){
             int MAX=connection.getContentLength();     
             InputStream inputStream=connection.getInputStream();  
             ByteArrayOutputStream outputStream=new ByteArrayOutputStream();  
             byte []buf=new byte[1024];  
             int len = 0;  
             while( (len=inputStream.read(buf))!=-1){  
                 outputStream.write(buf, 0, len);  
             } 
             bitmap=BitmapFactory.decodeByteArray(outputStream.toByteArray(),0, MAX);  
             inputStream.close();  
             }   
        } catch (Exception e) {  
        	e.printStackTrace();
        	return null;
        }  
        return bitmap;  
    }     
    @Override  
    protected void onPostExecute(Bitmap result) {  
        /* 
         * 在doInbackground执行完成以后，onPostExecute将被调用，后台的结果将返回给UI线程，将获得图片显示出来 
         */  
		taskUpdate.update(result);
		taskUpdate.setProgressEnd();
        super.onPostExecute(result);  
    }  
  
  
}   
