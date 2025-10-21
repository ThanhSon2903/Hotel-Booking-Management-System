import logo from './logo.svg';
import './App.css';
import React from 'react';
import { BrowserRouter,Routes,Route,Navigate } from 'react-router-dom';
import AllRoomsPage from './component/booking_rooms/AllRoomPages';
import Navbar from './component/common/Navbar';
import FooterComponent from './component/common/Footer';
import HomePage from './component/home/HomePage';
import FindBookingPage from './component/booking_rooms/FindBookingPage';
import RoomDetailsPage from './component/booking_rooms/RoomDetailsPage ';
import LoginPage from './component/auth/LoginPage';
import { ProtectedRoute,AdminRoute } from './service/security';
function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar/>
        <div className='content'>

          <Routes>  

          /*public router */
            <Route exact path='/home' element = {<HomePage/>}/>
            <Route exact path="/login" element={<LoginPage />} />
            <Route path="/rooms" element={<AllRoomsPage />} />
            <Route path='/find-booking' element = {<FindBookingPage/>}/>


            <Route path='/room-details-book/:roomId'
              element={<ProtectedRoute element={<RoomDetailsPage />} />}
            />
          </Routes>
        </div>
        <FooterComponent/>

      </div>
    </BrowserRouter>
  );
}

export default App;
