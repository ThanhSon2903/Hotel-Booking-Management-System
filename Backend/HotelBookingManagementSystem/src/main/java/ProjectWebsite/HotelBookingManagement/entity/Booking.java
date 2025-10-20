package ProjectWebsite.HotelBookingManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "bookings")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull(message = "Check in date is required")
    LocalDate checkInDate;

    @Future(message = "Check out date must be in the future")
    LocalDate checkOutDate;

    @Min(value = 1,message = "Number of adults must not be less than 1")
    int numOfAdults;

    @Min(value = 0,message = "Number of children must not be less than 0")
    int numOfChildren;

    int totalNumberOfGuest;


    String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;

    public int calcTotalNumberOfGuest(){
        return this.numOfAdults + this.numOfChildren;
    }

    public void setNumberOfAdults(int numberOfAdults){
        this.numOfAdults = numberOfAdults;
        calcTotalNumberOfGuest();
    }

    public void setNumberOfChildren(int numberOfChildren){
        this.numOfChildren = numberOfChildren;
        calcTotalNumberOfGuest();
    }

    @Override
    public String toString() {
        return "Booking{" +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                ", totalNumberOfGuest=" + totalNumberOfGuest +
                ", numOfChildren=" + numOfChildren +
                ", numOfAdults=" + numOfAdults +
                ", checkOutDate=" + checkOutDate +
                ", checkInDate=" + checkInDate +
                ", id=" + id +
                '}';
    }
}
