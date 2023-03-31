package org.example.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualUser {
    private String name;
    private String email;
    private String gender;
    private String status;
    private String id;
}
