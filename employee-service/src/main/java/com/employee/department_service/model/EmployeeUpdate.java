package com.employee.department_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeUpdate {
    @JsonProperty("id")
    UUID id;
    @JsonProperty("name")
    String name;
    @JsonProperty("dept")
    String department;

}
