package com.mockito;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Test07VerifingBehaviour {
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
    void should_InvokePayment_When_PrePaid() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, true);
        rentingService.makeBooking(rentingRequest);
        verify(paymentServiceMock, times(1)).pay(rentingRequest, 700.0);
        //checking pay method just one calling
        verifyNoMoreInteractions(paymentServiceMock);
    }

    @Test
    void should_NotInvokePayment_When_NotPrePaid() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, false);
        rentingService.makeBooking(rentingRequest);
        verify(paymentServiceMock, never()).pay(any(), anyDouble());
    }
}
