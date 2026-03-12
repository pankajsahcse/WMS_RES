package com.res.bean;

import java.util.List;

public class chapterDesciptionDto {

  private Long chapterId;

  private String chapterName;
  
  private List<WorkTemplateBean>  ItemList;

public Long getChapterId() {
	return chapterId;
}

public void setChapterId(Long chapterId) {
	this.chapterId = chapterId;
}

public String getChapterName() {
	return chapterName;
}

public void setChapterName(String chapterName) {
	this.chapterName = chapterName;
}

public List<WorkTemplateBean> getItemList() {
	return ItemList;
}

public void setItemList(List<WorkTemplateBean> itemList) {
	ItemList = itemList;
}
  
  
  
	
}
