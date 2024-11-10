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
public class NewEmployee {
    @JsonProperty("id")
    UUID id;
    @JsonProperty("name")
    String name;
    @JsonProperty("dept")
    String department;
    @JsonProperty("doj")
    String dateofjoining;

    public String getDateOfJoining() {
        return dateofjoining;
    }
}
