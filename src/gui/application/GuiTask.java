package gui.application;

import java.util.ArrayList;

public class GuiTask extends javafx.scene.control.Label{
	
	private String name;
	private String author;
	private String description;
	private short status;
	private int color;
	private ArrayList<String> tagList;
	private ArrayList<String> commentList;
	
	public GuiTask() {
		super();
		this.name = "";
		this.author = "";
		this.description = "";
		this.status = 0;
		
	}
	
	/*********************************************************************************************************
	 * 
	 * Getter & Setter:
	 * 
	 *********************************************************************************************************/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public ArrayList<String> getTagList() {
		return tagList;
	}
	public void setTagList(ArrayList<String> tagList) {
		this.tagList = tagList;
	}
	public ArrayList<String> getCommentList() {
		return commentList;
	}
	public void setCommentList(ArrayList<String> commentList) {
		this.commentList = commentList;
	}
	
	/*********************************************************************************************************
	 * 
	 * Andere Funktionen:
	 * 
	 *********************************************************************************************************/
	
	public void addTag(String newTag) {
		boolean exists = false;
		for(int i = 0; i < this.tagList.size(); i++) {
			if(name == this.tagList.get(i)) {
				exists = true;
			}
		}
		if(!exists) {
			this.tagList.add(newTag);
		}
	}
	
	public void addComment(String newComment) {
			this.commentList.add(newComment);
	}
	
	public void showText() {
		this.setText(this.name + "\n-----------------------------\n" + this.description);
	}
	
	
	
	
}
