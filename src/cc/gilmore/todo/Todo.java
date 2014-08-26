package cc.gilmore.todo;

public class Todo {
	private String title;
	private String created_date;

	public Todo(String title, String created_date) {
		this.title = title;
		this.created_date = created_date;
	}
	public String getTitle() {
		return title;
	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
	public String getCreatedDate() {
		return created_date;
	}
//	public void setCreatedDate(String created_date) {
//		this.created_date = created_date;
//	}
	
	public String toString() {
		return getTitle();
	}
}
