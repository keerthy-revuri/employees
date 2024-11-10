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
public class DepartmentDetails {
    @JsonProperty("deptId")
    UUID deptId;
    @JsonProperty("deptName")
    String deptName;
    @JsonProperty("hodId")
    UUID hodId;
}
