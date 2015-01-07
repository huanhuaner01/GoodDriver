package com.huishen_app.zh.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {

	/**
	 * 对象深度拷贝
	 * 
	 * @param obt
	 * @return
	 */
	public final static Object deepClone(Object obt) {
		Object ct = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obt);
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			ct = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ct;
	}

	/**
	 * 获取UUID 随机码
	 * 
	 * @return
	 */
	public final static String getUuid() {
		String ct = null;
		try {
			ct = UUID.randomUUID().toString().trim().replaceAll("-", "");
		} catch (Exception e) {
		}
		return ct;
	}
	/** 
     * 手机号座机号验证 
     * 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isTel(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^(010\\d{8})|(0[2-9]\\d{9})|(13\\d{9})|(14[57]\\d{8})|(15[0-35-9]\\d{8})|(18[0-35-9]\\d{8})$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
	/** 
     * 邮箱验证 
     * 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isEmail(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$"); // 验证邮箱  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    
	/** 
     * 验证用户名
     * 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isUserName(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[a-z0-9_-]{4,20}$"); // 验证用户名
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
	/** 
     * 验证姓名
     * 
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isRealName(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[\u4e00-\u9fa5]{2,8}$"); // 验证用户名
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
}
