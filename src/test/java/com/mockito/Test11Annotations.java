package com.mockito;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test11Annotations {
    @InjectMocks
    private RentingService rentingService;
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private BookService bookServiceMock;
    @Mock
    private MailSender mailSenderMock;
    @Spy
    private RentingDAO rentingDAOMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void should_PayCorrectPrice_When_InputOK() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, true);
        rentingService.makeBooking(rentingRequest);
        verify(paymentServiceMock, times(1)).pay(eq(rentingRequest), doubleCaptor.capture());
        double capturedArgument = doubleCaptor.getValue();
        assertEquals(700.0, capturedArgument);
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
