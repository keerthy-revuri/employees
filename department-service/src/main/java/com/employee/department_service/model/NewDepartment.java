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
public class NewDepartment {
    @JsonProperty("deptid")
    UUID deptid;
    @JsonProperty("deptname")
    String deptname;
    @JsonProperty("hodid")
    UUID hodid;

}
