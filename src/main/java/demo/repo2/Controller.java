package demo.repo2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.annotation.CreateAnnotation;
import demo.member.MemberAccount;

@RestController
public class Controller {

	@Autowired
	private BookAuthorService bookAuthorService;
	
	//@CreateAnnotation("join")
	@GetMapping("/getBookAuthor")
	public ResponseEntity<List<BookAuthorDto>> getBookAuthor() {
		return new ResponseEntity<List<BookAuthorDto>>(bookAuthorService.getBookAuthor(), HttpStatus.OK);
	}	
	
	 @GetMapping("/author")
	    public List<Author> list() {
	        return bookAuthorService.listAll();
	    }
	 
	 @GetMapping("/book")
	    public List<Book> list1() {
	        return bookAuthorService.list1();
	    }
}
