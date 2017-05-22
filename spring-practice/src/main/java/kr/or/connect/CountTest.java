package kr.or.connect;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CountTest {
	private int count = 0;
	
	
	
	@Before
	public void setUp(){
		System.out.println("setup() : "+count++);
	}
	
	@Test
	public void testPlus() {
		System.out.println("testPlus() : "+count++);
	}
	
	@Test
	public void increase(){
		System.out.println("increase() : "+count++);
	}
	
	@After
	public void finish(){
		System.out.println("finish() : "+count);
	}
	
	@BeforeClass
	public static void beforeClass(){
		System.out.println("beforeClass()");
	}
	
	@AfterClass
	public static void afterClass(){
		System.out.println("afterClass()");
	}
	

}
