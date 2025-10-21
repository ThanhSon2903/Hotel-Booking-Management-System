package ProjectWebsite.HotelBookingManagement.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Response {
    int statusCode;
    String message,token,role,expirationTime,bookingConfirmationCode;
    UserDTO user;
    RoomDTO room;
    BookingDTO booking;
    List<UserDTO> userList;
    List<RoomDTO> roomList;
    List<BookingDTO> bookingList;
}
