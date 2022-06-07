package com.herokuapp.restful_booker.model;

import java.util.Date;

public class BookingDatesPojo {
    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    private Date checkin;
    private Date checkout;

    public static BookingDatesPojo getBookingDates(Date checkin, Date checkout){
        BookingDatesPojo bookingDates = new BookingDatesPojo();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);
        return bookingDates;
    }
}

