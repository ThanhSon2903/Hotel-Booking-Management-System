package ProjectWebsite.HotelBookingManagement.dto;


import ProjectWebsite.HotelBookingManagement.entity.Booking;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDTO {
    Long id;
    String roomType;
    BigDecimal roomPrice;
    String roomPhotoUrl;
    String roomDescription;
    List<BookingDTO>bookings;
}
