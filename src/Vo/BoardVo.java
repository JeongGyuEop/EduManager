package Vo;

import java.sql.Date;

//조회한 하나의 글정보를 저장할 용도
//또는
//수정할 하나의 글정보를 조회한후 저장할 용도
//또는
//DB의  Fileboard테이블에 입력한 새글 정보를 추가하기  위해 임시로 저장할 용도

public class BoardVo {

	// 변수
	private int notice_id, b_group, b_level, schedule_id;

	private String author_id, title, content, event_name, description;
	private Date created_date, start_date, end_date;

	@Override
	public String toString() {
		return "BoardVo{" + "notice_id=" + notice_id + ", title='" + title + '\'' + ", content='" + content + '\''
				+ ", author_id='" + author_id + '\'' + ", created_date=" + created_date + ", b_group=" + b_group
				+ ", b_level=" + b_level + '}';
	}

	// 생성자
	public BoardVo() {
	}

	public BoardVo(int notice_id, String author_id, String title, String content, Date created_date) {
		super();
		this.notice_id = notice_id;
		this.author_id = author_id;
		this.title = title;
		this.content = content;
		this.created_date = created_date;
	}

	public BoardVo(int notice_id, int b_group, int b_level, String author_id, String title, String content) {
		super();
		this.notice_id = notice_id;
		this.b_group = b_group;
		this.b_level = b_level;
		this.author_id = author_id;
		this.title = title;
		this.content = content;
	}

	public BoardVo(int notice_id, int b_group, int b_level, String author_id, String title, String content,
			Date created_date) {
		super();
		this.notice_id = notice_id;
		this.b_group = b_group;
		this.b_level = b_level;
		this.author_id = author_id;
		this.title = title;
		this.content = content;
		this.created_date = created_date;
	}

	public BoardVo(int schedule_id, String event_name, String description, Date start_date, Date end_date) {
		super();
		this.schedule_id = schedule_id;
		this.event_name = event_name;
		this.description = description;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public int getSchedule_id() {
		return schedule_id;
	}

	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
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

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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

	public int getB_group() {
		return b_group;
	}

	public void setB_group(int b_group) {
		this.b_group = b_group;
	}

	public int getB_level() {
		return b_level;
	}

	public void setB_level(int b_level) {
		this.b_level = b_level;
	}

}
