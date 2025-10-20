package ProjectWebsite.HotelBookingManagement.Controller;

import ProjectWebsite.HotelBookingManagement.dto.LoginRequest;
import ProjectWebsite.HotelBookingManagement.dto.Response;
import ProjectWebsite.HotelBookingManagement.entity.User;
import ProjectWebsite.HotelBookingManagement.service.Imple.UserService;
import ProjectWebsite.HotelBookingManagement.service.interfa.IUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthController {

    @Autowired
    UserService iUserService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User user) {
        Response response = iUserService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = iUserService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
