package com.wemeow.web.notification.entity;

import lombok.Data;

@Data
public class Notification {

	public static enum Type{
		MOMENT_RELATIVE{
			public String getDes(){
				return "moment";
			}
		},
		SYSTEM{
			public String getDes(){
				return "moment";
			}
		};
		public abstract String getDes();
	}
	
	public Notification(Notification.Type type){
		this.type=type.getDes();
	}
	
	private int id;
	
	private int userId;
	
	private String content;
	
	private boolean isCheck;
	
	private boolean isDelete;
	
	private String type;
	
}
