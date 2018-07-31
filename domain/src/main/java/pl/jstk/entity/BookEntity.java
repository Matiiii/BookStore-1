package pl.jstk.entity;

import java.io.Serializable;

import javax.persistence.*;

import pl.jstk.enumerations.BookStatus;

@Entity
@Table(name = "BOOK")
@TableGenerator(name="tab", initialValue=5, allocationSize=50)
public class BookEntity implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	private Long id;
	@Column(nullable = false, length = 50)
	private String title;
	@Column(nullable = false, length = 200)
	private String authors;
	@Enumerated(EnumType.STRING)
	private BookStatus status;

	protected BookEntity() {
	}

	public BookEntity(String title, String authors, BookStatus status) {
		this.title = title;
		this.authors = authors;
		this.setStatus(status);
	}

	public BookEntity(Long id, String title, String authors, BookStatus status) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.setStatus(status);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}
}
