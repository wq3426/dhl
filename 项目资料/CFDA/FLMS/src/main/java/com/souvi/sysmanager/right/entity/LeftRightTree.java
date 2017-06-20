package com.souvi.sysmanager.right.entity;

import java.util.List;

public class LeftRightTree {
	
	private String id;
	private String name;
	private String rightCode;
	private boolean open;
	private List<LeftRightTree> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRightCode() {
		return rightCode;
	}
	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public List<LeftRightTree> getChildren() {
		return children;
	}
	public void setChildren(List<LeftRightTree> children) {
		this.children = children;
	}
	
	

}
