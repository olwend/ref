package uk.ac.nhm.core.impl.servlets;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.day.cq.search.QueryBuilder;

import io.wcm.testing.mock.aem.junit.AemContext;

@RunWith(MockitoJUnitRunner.class)
public class DiscoverRssFeedServletTest {

	@Mock
    private QueryBuilder queryBuilder;
	
	private DiscoverRssFeedServlet servlet;
	
	@Rule
    public final AemContext context = new AemContext();

	@Before
	public void setUp() throws Exception {
		servlet = new DiscoverRssFeedServlet();
	}
	
	@Test
	public void testDayConversion() {
		assertEquals(servlet.getDayOfWeek(1), "Mon");
		assertEquals(servlet.getDayOfWeek(2), "Tue");
		assertEquals(servlet.getDayOfWeek(3), "Wed");
		assertEquals(servlet.getDayOfWeek(4), "Thu");
		assertEquals(servlet.getDayOfWeek(5), "Fri");
		assertEquals(servlet.getDayOfWeek(6), "Sat");
		assertEquals(servlet.getDayOfWeek(7), "Sun");
	}
	
	@Test
	public void testDayNumberValidator() {
		assertEquals(servlet.getDayOfMonth(1), "01");
		assertEquals(servlet.getDayOfMonth(9), "09");
		assertEquals(servlet.getDayOfMonth(10), "10");
		assertEquals(servlet.getDayOfMonth(31), "31");
	}
	
	@Test
	public void testMonthConversion() {
		assertEquals(servlet.getMonth(1), "Jan");
		assertEquals(servlet.getMonth(12), "Dec");
	}
	
}
