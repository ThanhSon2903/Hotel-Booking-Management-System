package ProjectWebsite.HotelBookingManagement.service.interfa;

import ProjectWebsite.HotelBookingManagement.dto.LoginRequest;
import ProjectWebsite.HotelBookingManagement.dto.Response;
import ProjectWebsite.HotelBookingManagement.entity.User;
import org.springframework.stereotype.Service;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUser();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
