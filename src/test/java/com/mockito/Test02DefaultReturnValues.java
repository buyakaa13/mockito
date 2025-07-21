package com.mockito;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class Test02DefaultReturnValues {
    private RentingService rentingService;
    private PaymentService paymentServiceMock;
    private BookService bookServiceMock;
    private MailSender mailSenderMock;
    private RentingDAO rentingDAOMock;

    @BeforeEach
    void setUp() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.bookServiceMock = mock(BookService.class);
        this.mailSenderMock = mock(MailSender.class);
        this.rentingDAOMock = mock(RentingDAO.class);
        this.rentingService = new RentingService(paymentServiceMock, bookServiceMock, rentingDAOMock, mailSenderMock);
        System.out.println("List returned: " + bookServiceMock.getAvailableBooks());
        System.out.println("Object returned: " + bookServiceMock.findAvailableBookId(null));
    }

    @Test
    void should_CountAvailablePlaces() {
        int expected = 0;
        int actual = rentingService.getAvailableBookCount();
        assertEquals(expected, actual);
    }
}
