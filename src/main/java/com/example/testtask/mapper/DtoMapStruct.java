package com.example.testtask.mapper;

import com.example.testtask.dto.requests.reservation.CreateReservationRequest;
import com.example.testtask.dto.requests.user.RegisterUserRequest;
import com.example.testtask.dto.responses.ListReservationResponse;
import com.example.testtask.dto.responses.ReservationResponse;
import com.example.testtask.dto.responses.UserResponse;
import com.example.testtask.models.Reservation;
import com.example.testtask.models.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface DtoMapStruct {
    User toUser(RegisterUserRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    Reservation toReservation(CreateReservationRequest request, User user);

    ReservationResponse toReservationResponse(Reservation reservation);

    List<ReservationResponse> toReservationResponse(List<Reservation> reservations);

    default ListReservationResponse toListReservationResponse(final List<ReservationResponse> list) {
        return new ListReservationResponse(list);
    }
}
