package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserDto {

    private Long id;
    private String name;
    private String password;
    private List<String> permissions;

}
