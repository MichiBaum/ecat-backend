package com.itensis.ecat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


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
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	AbstractEntity() {
		this.id = null;
	}

	AbstractEntity(Long id) {
		this.id = id;
	}

}
