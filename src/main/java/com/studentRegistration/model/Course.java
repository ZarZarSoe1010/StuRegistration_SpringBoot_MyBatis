package com.studentRegistration.model;
import javax.validation.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {

	private String cid;
    @NotEmpty
    private String name;
    
}
