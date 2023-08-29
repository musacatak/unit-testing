package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test05ThrowingExceptions {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);

		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
	}

	@Test
	void should_ThrowException(){
		// given
		BookingRequest bookingRequest = new BookingRequest("1",
				LocalDate.of(2020, 1, 1),
				LocalDate.of(2020, 1, 5), 2, false);
		when(this.roomServiceMock.findAvailableRoomId(bookingRequest))
				.thenThrow(BusinessException.class);

		// when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);

		// then
		assertThrows(BusinessException.class, executable);
	}

}