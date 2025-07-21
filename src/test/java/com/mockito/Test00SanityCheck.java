package com.mockito;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test00SanityCheck {
	private RentingService rentingService;
	private PaymentService paymentService;
	private BookService bookService;
	private MailSender mailSender;
	private RentingDAO rentingDAO;

	@BeforeEach
	void setUp() {
		this.paymentService = mock(PaymentService.class);
		this.bookService = mock(BookService.class);
		this.mailSender = mock(MailSender.class);
		this.rentingDAO = mock(RentingDAO.class);
		this.rentingService = new RentingService(paymentService, bookService, rentingDAO, mailSender);
	}

	@Test
	void testCalculateCorrectPrice() {
		// should pass
		RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
				LocalDate.of(2025, Month.JANUARY, 8), 2, false);
		double expected = 7 * 2 * 50.0;
		double actual = rentingService.calculatePrice(rentingRequest);
		assertEquals(expected, actual);
	}

}
