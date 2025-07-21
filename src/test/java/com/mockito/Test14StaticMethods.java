package com.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class Test14StaticMethods {
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
    void should_CalculateCorrectPrice(){
        try(MockedStatic<CurrencyConverter> mockedCurrencyConverter = mockStatic(CurrencyConverter.class)){
            RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                    LocalDate.of(2025, Month.JANUARY, 5), 2, false);
            double expected = 400.0;
            mockedCurrencyConverter.when(() -> CurrencyConverter.toEuro(expected)).thenReturn(400.0);
            double actual = rentingService.calculatePrice(rentingRequest);
            assertEquals(expected, actual);
        }

    }
}
