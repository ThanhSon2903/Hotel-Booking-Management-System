package ProjectWebsite.HotelBookingManagement.service.interfa;

import ProjectWebsite.HotelBookingManagement.dto.Response;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Response getAllRoom();

    Response getRoomById(Long roomId);

    Response deleteRoom(Long roomId);

    Response updateRoom(Long roomId, String description,String roomType, BigDecimal roomPrice,MultipartFile photo);

    Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    Response getAllAvailableRooms();
}
