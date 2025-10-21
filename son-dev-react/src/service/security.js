import React from "react";
import apiService from "./apiService";
import { useLocation,Navigate} from "react-router-dom";


/*Nếu là AD thì sẽ cho truy cập vào path không thì điều hướng về trang /login */
export const AdminRoute = ({ element: Component }) => {
  const location = useLocation();

  return apiService.isAdmin() ? (Component) : ( <Navigate to="/login" replace state={{ from: location }} /> );
};


/*Kiểm tra xem người dùng có đăng nhập chưa, nếu chưa thì tự động chuyển về trang /login */
export const ProtectedRoute = ({ element: Component }) => {
  const location = useLocation();

  return apiService.isAuthenticated() ? (Component) : (<Navigate to="/login" replace state={{ from: location }} />);
};