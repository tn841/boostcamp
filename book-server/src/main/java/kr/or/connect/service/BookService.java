package kr.or.connect.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.domain.Book;
import kr.or.connect.persistence.BookDao;

@Service
public class BookService {
	//private ConcurrentHashMap<Integer, Book> repo = new ConcurrentHashMap<>();	//메모리에 book 객체 저장
	//private AtomicInteger maxId = new AtomicInteger(0);	//자동 숫자 증가
	@Autowired
	private BookDao dao;	//@Autowired로 DI, 또는 생성자로 연결해준다.
	
	
	
	public Book findById(Integer id){
		//return new Book(1, "책이름", "저자", 100);
		return dao.selectById2(id);
	}
	
	public Collection<Book> findAll(){
		/*return Arrays.asList(
				new Book(1, "네이버 네비 좋아요", "김광근", 300),
				new Book(2, "HTTP 완벽 이해하기", "김명호", 300));	*/
		return dao.selectAll();
	}
	public Book create(Book book){
		Integer id = dao.insert(book);
		book.setId(id);
		return book;
	}
	
	public boolean update(Book book){
		int affected = dao.update(book);
		return affected == 1;
	}
	
	public boolean delete(Integer id){
		int affected = dao.deleteById(id);
		return affected == 1;
	}
	
}
