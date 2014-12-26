package com.huishen_app.all.address;

public class AddressListItem {
	private String name;
	private String code;
	public Boolean isSelected = false;
	
	public String getName(){
		return name;
	}
	public String getCode(){
		return code;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setCode(String code){
		this.code=code;
	}
}
