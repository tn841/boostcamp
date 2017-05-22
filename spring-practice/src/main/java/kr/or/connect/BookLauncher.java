package kr.or.connect;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.domain.Book;
import kr.or.connect.persistence.BookDao;

public class BookLauncher {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		//DataSource dataSource = context.getBean(DataSource.class); --> DataSource는 @Bean에 의해 context에 등록되어 있다.
		//BookDao dao = new BookDao(dataSource); --> BookDao 생성시 DataSource를 주입시켜주는 작업은 AppConfig에서 @ComponentScan에 의해 수행된다.
		BookDao dao = context.getBean(BookDao.class);	//context에 등록된 BookDao Bean을 사용하면 됨
		int count = dao.countBooks();
		
		System.out.println(count);
		
		Book book = dao.selectById(1);
		System.out.println(book.toString());
		
		System.out.println(dao.selectById2(1).toString());
		
		Book newbook =  new Book("네이버", "Java와 Spring", 241);
		int lastkey = dao.insert(newbook);
		System.out.println(lastkey);
		System.out.println(dao.selectById2(lastkey));

	}

}
