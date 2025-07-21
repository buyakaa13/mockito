package com.mockito;

import org.junit.jupiter.api.*;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Test04MultipleThenReturnCalls {
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
    void should_CountAvailableBooks_When_CalledMultipleTimes() {
        when(this.bookServiceMock.getAvailableBooks())
                .thenReturn(Collections.singletonList(new Book("Book 1", 5)))
                .thenReturn(Collections.emptyList());
        int expectedFirstCall = 5;
        int expectedSecondCall = 0;
        int actualFirst = rentingService.getAvailableBookCount();
        int actualSecond = rentingService.getAvailableBookCount();
        assertAll(()->assertEquals(expectedFirstCall, actualFirst),
                ()->assertEquals(expectedSecondCall, actualSecond));
    }
}
