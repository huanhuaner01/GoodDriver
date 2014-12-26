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
 * ���ڻ�ȡ����ͼƬ���첽�߳�
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
    protected Bitmap doInBackground(String... params) {//�����䳤�Ŀɱ���� �ͣգ��߳��е�Asyna.execute()��Ӧ  
    /* 
     * �÷�����OnpreExecuteִ���Ժ�����ִ�У��ķ���ִ���ں�̨�̵߳��У������ʱ�ļ��㣬���Ե���publishProcess������ʵʱ����������� 
     */  
        Bitmap bitmap=null;  
        try {  
             URL url=new URL(params[0]); 
             HttpURLConnection connection=(HttpURLConnection) url.openConnection();   
             connection.setRequestMethod("GET");
             connection.setConnectTimeout(6*1000);
             //������10��
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
         * ��doInbackgroundִ������Ժ�onPostExecute�������ã���̨�Ľ�������ظ�UI�̣߳������ͼƬ��ʾ���� 
         */  
		taskUpdate.update(result);
		taskUpdate.setProgressEnd();
        super.onPostExecute(result);  
    }  
  
  
}   
