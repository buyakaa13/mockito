package com.mockito;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.mockito.Mockito.*;

public class Test08Spies {
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
    }

    @Test
    void should_MakeBooking_When_InputOK() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, true);
        String bookingId = rentingService.makeBooking(rentingRequest);
        verify(rentingDAOMock).save(rentingRequest);
        System.out.println("BookingId: " + bookingId);
    }

    @Test
    void should_CancelRenting_When_InputOK() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, false);
        rentingRequest.setBookId("1.3");
        String bookingId = "1";
        doReturn(rentingRequest).when(rentingDAOMock).get(bookingId);
        rentingService.cancelBooking(bookingId);
    }
}
