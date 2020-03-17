package com.itensis.ecat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * Base class for entity implementations. Uses a {@link Long} id.
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@EqualsAndHashCode
class AbstractEntity{

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	AbstractEntity() {
		this.id = null;
	}

	AbstractEntity(Long id) {
		this.id = id;
	}

}
