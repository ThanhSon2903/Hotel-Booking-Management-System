package ProjectWebsite.HotelBookingManagement.service.Imple;


import ProjectWebsite.HotelBookingManagement.dto.LoginRequest;
import ProjectWebsite.HotelBookingManagement.dto.Response;
import ProjectWebsite.HotelBookingManagement.dto.UserDTO;
import ProjectWebsite.HotelBookingManagement.entity.User;
import ProjectWebsite.HotelBookingManagement.exception.OurException;
import ProjectWebsite.HotelBookingManagement.repository.UserRepo;
import ProjectWebsite.HotelBookingManagement.service.interfa.IUserService;
import ProjectWebsite.HotelBookingManagement.utils.JWTUtils;
import ProjectWebsite.HotelBookingManagement.utils.Utils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService implements IUserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Response register(User user){
        Response response = new Response();
        try {
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepo.existsByEmail(user.getEmail())){
                throw new OurException(user.getEmail() + "Already Exists!");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Regis " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try{
            //Dùng authenticationManager để xác thực người dùng bằng email và password.
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
            var user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User Not Found"));

            var jwt = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Successful!");
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Regis " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUser() {
        Response response = new Response();
        try{
            List<User>userList = userRepo.findAll();
            if(userList.isEmpty()){
                throw new OurException("No results found. Try adjusting your filters.");
            }
            List<UserDTO>userDTOList = Utils.mapListUserToListUserDTO(userList);
            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(userDTOList);
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Regis " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();
        try{
            User user = userRepo.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found!"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
            response.setStatusCode(200);
            response.setMessage("Get all booking successful");
            response.setUser(userDTO);
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Register " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try{
            userRepo.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            userRepo.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("Delete Successful ✔");
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Regis " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try{
            User user = userRepo.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Get user successful ✔");
            response.setUser(userDTO);
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Regis " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepo.findByEmail(email).orElseThrow(() -> new OurException("Email Not Found,please try again"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Get user successful ✔");
            response.setUser(userDTO);
        }
        catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occurred During User Regis " + e.getMessage());
        }
        return response;
    }
}
