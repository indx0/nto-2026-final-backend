package com.example.nto.util;

import com.example.nto.dto.BookingDto;
import com.example.nto.entity.Booking;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingMapper {
    public static BookingDto convertToDto(Booking bk) {
        return new BookingDto(bk.getId(), bk.getDate(), PlaceMapper.convertToDto(bk.getPlace()), bk.getEmployee().getName());
    }
}
