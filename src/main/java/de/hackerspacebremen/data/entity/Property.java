package de.hackerspacebremen.data.entity;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import de.hackerspacebremen.data.annotation.FormatPart;
import de.hackerspacebremen.data.annotation.OsnEntity;

@OsnEntity("Property")
@Entity
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
