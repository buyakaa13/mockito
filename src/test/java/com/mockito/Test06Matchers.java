package com.mockito;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.time.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Test06Matchers {
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
    void should_NotCompleteRenting_When_PriceTooHigh() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 7), 2, true);
        when(this.paymentServiceMock.pay(any(), eq(500.0))).thenThrow(BusinessException.class);
        Executable executable = () -> rentingService.makeBooking(rentingRequest);
        assertThrows(BusinessException.class, executable);
    }
}
