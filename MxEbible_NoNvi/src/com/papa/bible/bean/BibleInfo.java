package com.papa.bible.bean;

public class BibleInfo {
	private Integer id;// 记录ID
	private Integer bookID;// 书籍ID
	private String bookName;// 书籍名称
	private String chapter;// 第几章

	private String section;// 这一章中的第几句
	private String verse;// 诗句
	private String bgcolor;// 诗句高亮的颜色
	private String underline;// 诗句的是否有下划线：等于1有下划线，其他没有下划线
	private String reserve06;// 加入下划线的时间

	private String reserve01;// 是否做有笔记，1有笔记内容；其他没有笔记内容
	private String reserve02;// 笔记的内容
	private String reserve03;// 做笔记的时间

	private String reserve04;// 是否加入书签：1为已加入书签，其他没有加入书签
	private String reserve05;// 加入书签的时间
	private String reserve07;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getVerse() {
		return verse;
	}

	public void setVerse(String verse) {
		this.verse = verse;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getUnderline() {
		return underline;
	}

	public void setUnderline(String underline) {
		this.underline = underline;
	}

	public String getReserve01() {
		return reserve01;
	}

	public void setReserve01(String reserve01) {
		this.reserve01 = reserve01;
	}

	public String getReserve02() {
		return reserve02;
	}

	public void setReserve02(String reserve02) {
		this.reserve02 = reserve02;
	}

	public String getReserve03() {
		return reserve03;
	}

	public void setReserve03(String reserve03) {
		this.reserve03 = reserve03;
	}

	public String getReserve04() {
		return reserve04;
	}

	public void setReserve04(String reserve04) {
		this.reserve04 = reserve04;
	}

	public String getReserve05() {
		return reserve05;
	}

	public void setReserve05(String reserve05) {
		this.reserve05 = reserve05;
	}

	public String getReserve06() {
		return reserve06;
	}

	public void setReserve06(String reserve06) {
		this.reserve06 = reserve06;
	}

	public String getReserve07() {
		return reserve07;
	}

	public void setReserve07(String reserve07) {
		this.reserve07 = reserve07;
	}

	@Override
	public String toString() {
		return "BibleInfo [id=" + id + ", bookID=" + bookID + ", bookName=" + bookName + ", chapter=" + chapter
				+ ", section=" + section + ", verse=" + verse + ", bgcolor=" + bgcolor + ", underline=" + underline
				+ ", reserve01=" + reserve01 + ", reserve02=" + reserve02 + ", reserve03=" + reserve03 + ", reserve04="
				+ reserve04 + ", reserve05=" + reserve05 + ", reserve06=" + reserve06 + ", reserve07=" + reserve07
				+ "]";
	}
}
