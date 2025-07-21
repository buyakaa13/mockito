package com.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class Test12Bdd {
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
    void should_CountAvailableBooks_When_OneBookAvailable(){
        given(this.bookServiceMock.getAvailableBooks()).willReturn(Collections.singletonList(new Book("Book 1", 2)));
        int expected = 2;
        int actual = rentingService.getAvailableBookCount();
        assertEquals(expected, actual);
    }

    @Test
    void should_InvokePayment_When_Prepaid(){
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 5), 2, true);
        rentingService.makeBooking(rentingRequest);
        then(paymentServiceMock).should(times(1)).pay(rentingRequest, 400.0);
        verifyNoMoreInteractions(paymentServiceMock);
    }
}
