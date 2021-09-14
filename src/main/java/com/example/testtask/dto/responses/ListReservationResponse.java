package com.example.testtask.dto.responses;

import com.example.testtask.dto.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListReservationResponse implements ResponseBase {
    private List<ReservationResponse> list;
}
