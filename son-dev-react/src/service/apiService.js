import axios from "axios";

export default class apiService{

    //địa chỉ đường dẫn của phía backend
    static BASE_URL = "http://localhost:8080"

    //Lấy về header Authorization trong request HTTP
    static getHeader(){
        const token = localStorage.getItem("token");
        return{
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    }

    /*Đăng ký người dùng mới*/
    static async registerUser(registration) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registration)
        return response.data
    }

    /*Đăng nhập*/
    static async loginUser(loginDetails){
        const response = await axios.post(`${this.BASE_URL}/auth/login`, loginDetails)
        return response.data
    }

    /*Chỉ có admin mới có quyền lấy về hết danh sách người dùng*/
    static async getAllUsers(){
        const response = await axios.get(`${this.BASE_URL}/users/all`, {headers: this.getHeader()})
        return response.data
    }

    static async getUserProfile(){
        const response = await axios.get(`${this.BASE_URL}/users/get-logged-in-profile-info`, {headers: this.getHeader()})
        return response.data
    }

    static async getUserByID(id){
       const response = await axios.get(`${this.BASE_URL}/users/get-by-id/${id}`, {headers: this.getHeader()})
        return response.data
    }

     static async getUserBookings(id) {
        const response = await axios.get(`${this.BASE_URL}/users/get-user-bookings/${id}`, {headers: this.getHeader()})
        return response.data
    }

    static async deleteUser(id) {
        const response = await axios.delete(`${this.BASE_URL}/users/delete/${id}`, {headers: this.getHeader()})
        return response.data
    }


    /* 
    các api liên quan điến phòng
    */
    static async addRoom(formData) {
        const result = await axios.post(`${this.BASE_URL}/rooms/add`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }

    static async getAllAvailableRooms() {
        const result = await axios.get(`${this.BASE_URL}/rooms/all-available-rooms`)
        return result.data
    }

    /*Tìm kiếm theo ngày và loại phòng*/
    static async getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType){
        const result = await axios.get(
            `${this.BASE_URL}/rooms/available-rooms-by-date-and-type?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`)
        return result.data
    }

     static async getRoomTypes() {
        const response = await axios.get(`${this.BASE_URL}/rooms/types`)
        return response.data
    }

    static async getAllRooms() {
        const result = await axios.get(`${this.BASE_URL}/rooms/all`)
        return result.data
    }

    static async getRoomById(roomId) {
        const result = await axios.get(`${this.BASE_URL}/rooms/room-by-id/${roomId}`)
        return result.data
    }

    static async deleteRoom(roomId) {
        const result = await axios.delete(`${this.BASE_URL}/rooms/delete/${roomId}`, {
            headers: this.getHeader()
        })
        return result.data
    }

    static async updateRoom(roomId, formData) {
        const result = await axios.put(`${this.BASE_URL}/rooms/update/${roomId}`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return result.data;
    }

    /*Các API liên quan đến phòng*/
    static async bookRooms(roomId, userId, booking){
        console.log("This is user ID: " + userId)
        
        const response = await axios.post(`${this.BASE_URL}/bookings/book-room/${roomId}/${userId}`, booking, {
            headers: this.getHeader()
        }) 

        return response.data
    }

    static async getAllBookings() {
        const result = await axios.get(`${this.BASE_URL}/bookings/all`, {
            headers: this.getHeader()
        })
        return result.data
    }

    static async getBookingByConfirmationCode(bookingCode) {
        const result = await axios.get(`${this.BASE_URL}/bookings/get-by-confirmation-code/${bookingCode}`)
        return result.data
    }

    static async cancelBooking(bookingId) {
        const result = await axios.delete(`${this.BASE_URL}/bookings/cancel/${bookingId}`, {
            headers: this.getHeader()
        })
        return result.data
    }

    //Người dùng sẽ đăng xuất và sẽ xoá các token liên quan và vai trò ra khỏi bộ nhớ
    static logOut(){
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    //Kiêm tra xem người dùng đã đăng nhập hay chưa
    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token;
    }

    //Kiêm tra xem người dùng có phải admin không?
    static isAdmin(){
        const role = localStorage.getItem('role')
        return role == 'ADMIN'
    }

    //Kiểm tra xem người dùng có phải là user không?
    static isUser(){
        const role = localStorage.getItem('role')
        return role == 'USER'
    }
}