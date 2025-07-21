package com.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class Test15Answers {
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
            double expected = 400.0 * 0.8;
            mockedCurrencyConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer(inv->(double) inv.getArgument(0)*0.8);
            double actual = rentingService.calculatePrice(rentingRequest);
            assertEquals(expected, actual);
        }

    }
}
