package com.mockito;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Test16FinalMethods {
    //given
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
    void should_CountAvailableBooks_When_OneBookAvailable(){
        when(this.bookServiceMock.getAvailableBooks()).thenReturn(Collections.singletonList(new Book("Book 1", 2)));
        int expected = 2;
        int actual = rentingService.getAvailableBookCount();
        assertEquals(expected, actual);
    }
}
