package Vo;

import java.sql.Date;

//조회한 하나의 글정보를 저장할 용도
//또는
//수정할 하나의 글정보를 조회한후 저장할 용도
//또는
//DB의  Fileboard테이블에 입력한 새글 정보를 추가하기  위해 임시로 저장할 용도

/*
 	notice_id 		int AI PK
	title			varchar(100)
	content			text
	created_date	date
	author_id 		varchar(50)
 
 */

public class BoardVo {

	//변수
	private int notice_id;
	private String author_id, title, content;
	private Date created_date;
		
	//생성자
	public BoardVo() {}

	public BoardVo(int notice_id, String author_id, String title, String content) {
		super();
		this.notice_id = notice_id;
		this.author_id = author_id;
		this.title = title;
		this.content = content;
	}

	public BoardVo(int notice_id, String author_id, String title, String content, Date created_date) {
		super();
		this.notice_id = notice_id;
		this.author_id = author_id;
		this.title = title;
		this.content = content;
		this.created_date = created_date;
	}

	
	
	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	
}








