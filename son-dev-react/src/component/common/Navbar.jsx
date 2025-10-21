import React  from "react";
import { NavLink,Navigate, useNavigate } from "react-router-dom";
import apiService from "../../service/apiService";

function Navbar(){
    const isAuthenticated = apiService.isAuthenticated();
    const isAdmin = apiService.isAdmin();
    const isUser = apiService.isUser();
    const navigate = useNavigate();//Dùng để chuyển trang

    const handLogOut =() => {
        const isLogOut = window.confirm("Quý khách có thực sự chắc chắn muốn đăng xuất không?");
        if(isLogOut){
            apiService.logOut();
            navigate("/home");
        }
    }

    return(
        <nav className="navbar">
            <div className="navbar-brand">
                <NavLink to = "/home"> Bông Lúa Vàng Hotel </NavLink>
            </div>
            
            <ul className="navbar-ul">
                <li><NavLink to="/home" activeclass = "active"> Home</NavLink></li>
                <li><NavLink to="/rooms" activeclass = "active"> Rooms</NavLink></li>
                <li><NavLink to="/find-booking" activeclass = "active"> Find My Booking</NavLink></li>

                {isUser && <li><NavLink to="/profile" activeclass = "active"> Profile</NavLink></li>}
                {isAdmin && <li><NavLink to="/admin" activeclass = "active"> Admin</NavLink></li>}

                {!isAuthenticated && <li><NavLink to="/login" activeclass = "active"> Login</NavLink></li>}
                {!isAuthenticated && <li><NavLink to="/register" activeclass = "active"> Register</NavLink></li>}

                {isAuthenticated && <li onClick={handLogOut}>Logout</li>}


            </ul>
        </nav>
    );
}
export default Navbar;