package demo.repo2;

public class BookAuthorDto {
	private int bookId;
	private String bookName;
	private String bookAuthor;
	private int price;

	public BookAuthorDto(int bookId, String bookName, String bookAuthor, int price) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.price = price;
	}

	@Override
	public String toString() {
		return "BookAuthorDto [bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", price="
				+ price + "]";
	}


}
