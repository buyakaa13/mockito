package com.mockito;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Test03CustomReturnValues {
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
    void should_CountAvailableBooks_When_OneBookAvailable() {
        when(bookServiceMock.getAvailableBooks()).thenReturn(Collections.singletonList(new Book("Book 1", 100)));
        int expected = 1;
        int actual = rentingService.getAvailableBookCount();
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailableBooks_When_MultipleBooksAvailable() {
        List<Book> bookList = Arrays.asList(new Book("Book 1", 100), new Book("Book 2", 200));
        when(bookServiceMock.getAvailableBooks()).thenReturn(bookList);
        int expected = 2;
        int actual = rentingService.getAvailableBookCount();
        assertEquals(expected, actual);
    }

    @Test
    void should_ReturnZero_When_NoBooksAvailable() {
        when(bookServiceMock.getAvailableBooks()).thenReturn(Collections.emptyList());
        int expected = 0;
        int actual = rentingService.getAvailableBookCount();
        assertEquals(expected, actual);
    }
}
