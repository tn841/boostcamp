package kr.or.connect.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.AppConfig;
import kr.or.connect.domain.Book;

@RunWith(SpringRunner.class)		//spring application context 로딩
@ContextConfiguration(classes = AppConfig.class)	//Spring Application-context에 등록된 설정 클래스(파일)을 지정
@Transactional		//해당 클래스는 하나의 트랜젝션안에서 실행됨을 표시, 디폴트 설정에 의해 테스트 메소드는 끝날때 롤백한다. (insert 되지 않음)
public class BookDaoTest {
	
	@Autowired
	private BookDao dao;	//appConfig에서 @ComponentScan에 의해 Context에 bean으로 등록된 BookDao가 대입된다.
	
	@Test	//JUnit의 테스팅 대상
	public void shouldCont(){
		int count = dao.countBooks();
		System.out.println(count);
	}
	
	@Test
	public void shouldInsertAndSelect(){
		//given
		Book book = new Book("네이버","java 웹 개발", 123);
		
		//when
		Integer id = dao.insert(book);
		
		//then
		Book selectedBook = dao.selectById2(id);
		System.out.println(selectedBook.toString());
		assertThat(selectedBook.getTitle(), is("java 웹 개발"));	//검증 메서드, static import로 추가해줘야함..
	}
	
	@Test
	public void shouldDelete(){
		//given
		Book book = new Book("저자", "청추니까 아프니다", 332);
		Integer id = dao.insert(book);
		
		//when
		int affected = dao.deleteById(id);
		
		//then
		assertThat(affected, is(1));
	}
	
	@Test
	public void shouldUpdate(){
		//given
		Book book = new Book("갱신", "좌석배정을 반납", 112);
		Integer id = dao.insert(book);
		
		//when
		book.setId(id);
		book.setAuthor("갱신 저자");
		book.setTitle("갱신 타이틀");
		int affected = dao.update(book);
		
		//then
		assertThat(affected, is(1));
		Book updated = dao.selectById(id);
		assertThat(updated.getTitle(), is("갱신 타이틀"));
		
	}
	
}
