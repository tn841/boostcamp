package kr.or.connect.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.domain.Book;

@Repository
public class BookDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Book> mapper = BeanPropertyRowMapper.newInstance(Book.class);
	private SimpleJdbcInsert insertAction;
	
	private static final String COUNT_BOOK = "SELECT COUNT(*) FROM BOOK";
	private static final String SELECT_BY_ID = "SELECT id, title, author, pages FROM book WHERE id = :id";
	private static final String DELETE_BY_ID = "DELETE FROM book WHERE id = :id";
	private static final String UPDATE_BOOK = "UPDATE book SET title = :title, author = :author, pages = :pages WHERE id = :id";
	
	public BookDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("book").usingGeneratedKeyColumns("id");
	}
	
	public int countBooks(){
		Map<String, Object> params = Collections.emptyMap();
		Integer count = jdbc.queryForObject(COUNT_BOOK, params, Integer.class);
		return count;
	}
	
	public Book selectById(Integer id){
		RowMapper<Book> rowMapper = (rs, i) -> {	//람다식 이용
			Book book = new Book();
			book.setId(rs.getInt("id"));
			book.setAuthor(rs.getString("author"));
			book.setTitle(rs.getString("title"));
			book.setPages(rs.getInt("pages"));
			return book;
		};
		
		RowMapper<Book> rm = new RowMapper<Book>() {
		//일반적인 재정의 방식
			@Override
			public Book mapRow(ResultSet rs, int i) throws SQLException {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				return book;
			}
		};
		
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
	}

	public Book selectById2(int id){
		Book book = new Book();
		
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		book = jdbc.queryForObject(SELECT_BY_ID, params, mapper);	
		return book;
	}

	public Integer insert(Book book){
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		Integer key = insertAction.executeAndReturnKey(params).intValue();
		return key;
	}
	
	public int deleteById(Integer id){
		Map<String, Object> params = Collections.singletonMap("id", id);
		return jdbc.update(DELETE_BY_ID, params);
	}
	
	public int update(Book book){
		SqlParameterSource params = new BeanPropertySqlParameterSource(book);
		return jdbc.update(UPDATE_BOOK, params);
	}
}
