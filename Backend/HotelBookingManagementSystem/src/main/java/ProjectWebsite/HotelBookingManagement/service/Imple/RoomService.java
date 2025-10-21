package ProjectWebsite.HotelBookingManagement.service.Imple;

import ProjectWebsite.HotelBookingManagement.dto.Response;
import ProjectWebsite.HotelBookingManagement.dto.RoomDTO;
import ProjectWebsite.HotelBookingManagement.entity.Room;
import ProjectWebsite.HotelBookingManagement.exception.OurException;
import ProjectWebsite.HotelBookingManagement.repository.RoomRepo;
import ProjectWebsite.HotelBookingManagement.service.AwsS3;
import ProjectWebsite.HotelBookingManagement.service.interfa.IRoomService;
import ProjectWebsite.HotelBookingManagement.utils.Utils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomService implements IRoomService {

    @Autowired
    RoomRepo roomRepo;

    @Autowired
    AwsS3 awsS3;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        byte[] avatarImage = null;
        try {
            avatarImage = photo.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
            String imageUrl = awsS3.saveImageToS3(photo);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepo.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Add room successfully!");
            response.setRoom(roomDTO);
        }
        catch (Exception er){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + er.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
       return roomRepo.findDistinctRoomType();
    }

    @Override
    public Response getAllRoom() {
        Response response = new Response();
        try{
            List<Room> roomList = roomRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<RoomDTO> roomDTOList = Utils.mapListRoomToListRoomDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("Get all room successfully!");
            response.setRoomList(roomDTOList);
        }
        catch (Exception ignored){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + ignored.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try{
            Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("Room not found. Please try again"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            response.setRoom(roomDTO);
            response.setStatusCode(200);
            response.setMessage("Get room successful");
        }
        catch (OurException ourException){
            response.setStatusCode(404);
            response.setMessage(ourException.getMessage());
        }
        catch (Exception ignored){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + ignored.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try{
            roomRepo.findById(roomId).orElseThrow(() -> new OurException("Room not found. Please try again"));
            roomRepo.deleteById(roomId);
            response.setStatusCode(200);
            response.setMessage("Delete room successful");
        }
        catch (OurException ourException){
            response.setStatusCode(404);
            response.setMessage(ourException.getMessage());
        }
        catch (Exception ignored){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + ignored.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description,String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
        try{
            String imgUrl = null;
            if(photo != null && !photo.isEmpty()){
                imgUrl = awsS3.saveImageToS3(photo);
            }

            Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("Room not found"));
            if(roomType != null)room.setRoomType(roomType);
            if(roomPrice != null)room.setRoomPrice(roomPrice);
            if(description != null)room.setRoomDescription(description);
            if(imgUrl != null) room.setRoomPhotoUrl(imgUrl);

            Room updateRoom = roomRepo.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updateRoom);
            response.setStatusCode(200);
            response.setMessage("Update room successful");
            response.setRoom(roomDTO);
        }
        catch (OurException ourException){
            response.setStatusCode(404);
            response.setMessage("Error update room failed!");
        }
        catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error update room failed!" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try{
            List<Room> availableRooms = roomRepo.findAvailableRoomsByDatesAndTypes(checkInDate,checkOutDate,roomType);
            List<RoomDTO> roomDTOList = Utils.mapListRoomToListRoomDTO(availableRooms);
            response.setRoomList(roomDTOList);
            response.setStatusCode(200);
            response.setMessage("Successful");
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error filter room" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();
        try{
            List<Room> roomList = roomRepo.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapListRoomToListRoomDTO(roomList);
            response.setRoomList(roomDTOList);
            response.setStatusCode(200);
            response.setMessage("Successful");
        }
        catch (OurException ourException){
            response.setStatusCode(404);
            response.setMessage(ourException.getMessage());
        }
        catch (Exception ignored){
            response.setStatusCode(500);
            response.setMessage("Error saving a room" + ignored.getMessage());
        }
        return response;
    }
}
