package com.itensis.ecat.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class ReturnUserDto {

	private Long id;
	private String name;
	private List<String> permissions;
	private Long creationDate;

}
