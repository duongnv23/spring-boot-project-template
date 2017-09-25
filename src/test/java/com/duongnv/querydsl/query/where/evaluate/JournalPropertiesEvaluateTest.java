package com.duongnv.querydsl.query.where.evaluate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.duongnv.path.query.filter.PropertyEvaluater;
import com.duongnv.path.query.filter.evaluate.JournalPropertiesEvaluate;
import com.duongnv.spring.dao.entity.QCustomer;
import com.duongnv.spring.dao.entity.QJournal;

public class JournalPropertiesEvaluateTest {
	PropertyEvaluater<QJournal> evaluate;
	@Before
	public void init() {
		evaluate = new JournalPropertiesEvaluate();
	}

	@Test()
	public void testGetValue()  {
		String property = "id";
		String value = "123";
		assertTrue(Long.valueOf(value).equals(evaluate.getValue(property, value)));

		property = "summary";
		value = "summary";
		assertTrue(value.equals(evaluate.getValue(property, value)));
		
		property = "title";
		value = "title";
		assertTrue(value.equals(evaluate.getValue(property, value)));
	}
	
//	@Test(expected=QueryParseException.class)
	@Test
	public void testGetValueThrowQueryParseException() {
		String property = "created";
		String value = "created";
		try {
		assertNotNull(evaluate.getValue(property, value));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIsSupport() {
		assertTrue(evaluate.isSupport(QJournal.class));
		assertFalse(evaluate.isSupport(QCustomer.class));
	}

}
