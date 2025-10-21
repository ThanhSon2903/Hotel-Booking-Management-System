package ProjectWebsite.HotelBookingManagement.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    Long id;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    int numOfAdults;
    int numOfChildren;
    int totalNumberOfGuest;
    String bookingConfirmationCode;
    UserDTO userDTO;
    RoomDTO roomDTO;

}
