package ProjectWebsite.HotelBookingManagement.service.interfa;

import ProjectWebsite.HotelBookingManagement.dto.Response;
import ProjectWebsite.HotelBookingManagement.entity.Booking;

public interface IBookingService {

    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBooking();

    Response cancelBooking(Long id);
}
