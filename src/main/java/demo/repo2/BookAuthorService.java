package demo.repo2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.repo2.repo.AuthorRepository;
import demo.repo2.repo.BookRepository;

@Service
public class BookAuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	public List<BookAuthorDto> getBookAuthor() {
		List<Author> authors = (List<Author>) authorRepository.findAll();
		List<Book> books = (List<Book>) bookRepository.findAll();
		List<BookAuthorDto> bookAuthorDtos = new ArrayList<>();
		for(Author author: authors) {
			for(Book book: books) {
				if(author.getName().equals(book.getAuthor())) {
					//System.out.println("Authorname : " + author.getName());
					BookAuthorDto dto = new BookAuthorDto(book.getId(), book.getName(), author.getName(), book.getPrice());
					bookAuthorDtos.add(dto);
				}
				
			}
		}
		System.out.println(bookAuthorDtos);
		return bookAuthorDtos;
	}
	
	 public List<Author> listAll() {
	        return (List<Author>) authorRepository.findAll();
	    }
	 
	 public List<Book> list1() {
	        return (List<Book>) bookRepository.findAll();
	    }
}
