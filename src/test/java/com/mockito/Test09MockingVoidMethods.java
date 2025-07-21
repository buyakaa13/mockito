package com.mockito;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class Test09MockingVoidMethods {
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
    void should_ThrowException_When_NoRoomAvailable() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, true);
        doThrow(new BusinessException()).when(mailSenderMock).sendRentingConfirmation(any());
        Executable executable = () -> rentingService.makeBooking(rentingRequest);
        assertThrows(BusinessException.class, executable);
    }

    @Test
    void should_Not_ThrowException_When_MailNotReady() {
        RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                LocalDate.of(2025, Month.JANUARY, 8), 2, false);
        doNothing().when(mailSenderMock).sendRentingConfirmation(any());
        rentingService.makeBooking(rentingRequest);
    }
}
