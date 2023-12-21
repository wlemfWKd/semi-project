package com.semi.dto;

public class CheckDTO {
	
	private String type;		// 피부타입
	private String trouble;		// 피부고민
	private String dot;			// 주군꺠, 기미 정도
	private String color;		// 색소 남는편?
	private String older;		// 피부 얼마나 늙어보이냐?
	private String gender;		// 남자냐 여자냐?
	
	public void CheckDTO() {
		
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTrouble() {
		return trouble;
	}
	public void setTrouble(String trouble) {
		this.trouble = trouble;
	}
	public String getDot() {
		return dot;
	}
	public void setDot(String dot) {
		this.dot = dot;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getOlder() {
		return older;
	}
	public void setOlder(String older) {
		this.older = older;
	}
}
