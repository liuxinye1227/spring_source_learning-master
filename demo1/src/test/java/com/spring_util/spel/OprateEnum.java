package com.spring_util.spel;

/**
 * @author pengYongQiang
 * @date 2022/5/30 23:06
 */
public enum OprateEnum {
	ADD("addPpr"),
	DELETE("deletePpr");

	String val;

	public OprateEnum getOprate(String s){
		OprateEnum[] values = values();
		for (OprateEnum value : values) {
			if (value.val.equals(s)){
				return value;
			}
		}
		return null;
	}

	public String getVal() {
		return val;
	}
	OprateEnum(String deletePpr) {

		val = deletePpr;
	}
}
