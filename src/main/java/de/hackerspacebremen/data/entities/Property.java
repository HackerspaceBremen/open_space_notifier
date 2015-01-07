package de.hackerspacebremen.data.entities;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import de.hackerspacebremen.data.annotations.Entity;
import de.hackerspacebremen.data.annotations.FormatPart;

@Entity(name="Property")
@com.googlecode.objectify.annotation.Entity
@Cache
public final class Property implements BasicEntity{

	@Id
	@Getter
	@Setter
	@FormatPart(key="P1")
	private Long id;
	
	@Index
	@Getter
	@Setter
	@FormatPart(key="P2")
	private String key;
	
	@Getter
	@Setter
	@FormatPart(key="P3")
	private String value;
}
