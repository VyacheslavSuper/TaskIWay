package com.example.testtask.dto.responses;

import com.example.testtask.dto.ResponseBase;
import com.example.testtask.models.base.StatusReservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse implements ResponseBase {
    private Long id;
    private StatusReservation status;
    private String fromWhere;
    private List<String> toWhereList;
    private LocalDateTime arrivalDate;
}
