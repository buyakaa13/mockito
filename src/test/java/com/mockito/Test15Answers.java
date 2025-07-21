package com.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;

import static com.mockito.RentingService.BASE_PRICE_USD;
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
    void should_CalculateCorrectPrice_When_ValidRequest() {
        RentingRequest rentingRequest = new RentingRequest(
                "1",
                LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 5),
                2,
                false
        );
        double expected = BASE_PRICE_USD * 2 * 4; // BASE_PRICE_USD * quantity * days
        double actual = rentingService.calculatePrice(rentingRequest);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    void should_CalculateCorrectPriceInEuro_When_ValidRequest() {
        try (MockedStatic<CurrencyConverter> mockedCurrencyConverter = Mockito.mockStatic(CurrencyConverter.class)) {
            RentingRequest rentingRequest = new RentingRequest(
                    "1",
                    LocalDate.of(2025, Month.JANUARY, 1),
                    LocalDate.of(2025, Month.JANUARY, 5),
                    2,
                    false
            );
            double usdPrice = BASE_PRICE_USD * 2 * 4; // BASE_PRICE_USD * quantity * days
            double expected = usdPrice * 0.8; // Convert to EUR
            mockedCurrencyConverter.when(() -> CurrencyConverter.toEuro(usdPrice))
                    .thenReturn(expected);
            double actual = rentingService.calculatePriceEuro(rentingRequest);
            assertEquals(expected, actual, 0.001);
        }
    }

}
