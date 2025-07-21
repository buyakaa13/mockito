package com.mockito;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Test10ArgumentCaptors {
    private RentingService rentingService;
    private PaymentService paymentServiceMock;
    private BookService bookServiceMock;
    private MailSender mailSenderMock;
    private RentingDAO rentingDAOMock;
    private ArgumentCaptor<Double> doubleCaptor;

    @BeforeEach
    void setUp() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.bookServiceMock = mock(BookService.class);
        this.mailSenderMock = mock(MailSender.class);
        this.rentingDAOMock = mock(RentingDAO.class);
        this.rentingService = new RentingService(paymentServiceMock, bookServiceMock, rentingDAOMock, mailSenderMock);
        this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
    }

    @Test
    void should_PayCorrectPrice_When_InputOK() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, true);
        rentingService.makeBooking(rentingRequest);
        verify(paymentServiceMock, times(1)).pay(eq(rentingRequest), doubleCaptor.capture());
        double capturedArgument = doubleCaptor.getValue();
        assertEquals(400.0, capturedArgument);
    }

    @Test
    void should_PayCorrectPrice_When_MultipleCalls() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 5), 2, true);
        RentingRequest rentingRequest2 = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 2), 2, true);
        List<Double> expectedValues = Arrays.asList(400.0, 100.0);
        rentingService.makeBooking(rentingRequest);
        rentingService.makeBooking(rentingRequest2);
        verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
        List<Double> capturedArgument = doubleCaptor.getAllValues();
        assertEquals(expectedValues, capturedArgument);
    }
}
