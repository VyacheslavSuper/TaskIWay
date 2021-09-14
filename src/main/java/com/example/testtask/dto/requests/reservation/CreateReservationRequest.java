package com.example.testtask.dto.requests.reservation;

import com.example.testtask.dto.requests.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateReservationRequest implements RequestBase {
    @NotNull
    @NotBlank
    private String fromWhere;
    @NotNull
    @Size(min = 1)
    private List<String> toWhereList;
    @NotNull
    private LocalDateTime arrivalDate;
}
