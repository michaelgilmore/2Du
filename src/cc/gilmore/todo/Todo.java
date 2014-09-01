package cc.gilmore.todo;

public class Todo {
	private String title;
	private String created_date;
	private long db_id;

	public Todo(String title, String created_date) {
		this.title = title;
		this.created_date = created_date;
	}

	public String getTitle() {
		return title;
	}
	public void setTitleForDebugging(String title) {
		this.title = title;
	}

	public String getCreatedDate() {
		return created_date;
	}
//	public void setCreatedDate(String created_date) {
//		this.created_date = created_date;
//	}
	
	public String toString() {
		return getTitle();
	}

	public long getDbId() {
		return db_id;
	}
	public void setDbId(long db_id) {
		this.db_id = db_id;
	}
}
