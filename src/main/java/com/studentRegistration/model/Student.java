package com.studentRegistration.model;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    @NotEmpty
    private String sid;
    @NotEmpty
    private String name;
    @NotEmpty
    private String dob;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String education;
    @NotEmpty
    private List<String>courses;

}
