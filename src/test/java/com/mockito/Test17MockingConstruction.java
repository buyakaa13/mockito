package com.mockito;

import org.junit.jupiter.api.*;
import org.mockito.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Test17MockingConstruction {
    //given
    private BookService bookServiceMock;

    @Test
    void should_ShowAvailableBook_When_BigQuantity(){
        try(MockedConstruction<Book> mockedConstruction = Mockito.mockConstruction(Book.class, (mock, context)->{
            when(mock.getPageCount()).thenReturn(6);
            when(mock.getId()).thenReturn("5.0");
        })){
            this.bookServiceMock = mock(BookService.class);
            RentingRequest rentingRequest = new RentingRequest("1", LocalDate.of(2025, Month.JANUARY, 1),
                    LocalDate.of(2025, Month.JANUARY, 5), 6, false);
            String actualRoomId = bookServiceMock.findAvailableBookId(rentingRequest);
            assertEquals("5.0", actualRoomId);
        }
    }
}
