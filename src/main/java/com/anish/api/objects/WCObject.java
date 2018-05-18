package com.anish.api.objects;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.anish.api.tags.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "WCObjects")
public class WCObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String wildcraftId;
	
	@OneToMany
	private List<Tag> tags;
	
	public WCObject() {
		
	}
	
	public WCObject(Long id, String name, String wildcraftId) {
		this.setId(id);
		this.setName(name);
		this.setWildcraftId(wildcraftId);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWildcraftId() {
		return wildcraftId;
	}

	public void setWildcraftId(String wildcraftId) {
		this.wildcraftId = wildcraftId;
	}
	
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	public List<Tag> getTags() {
		return tags;
	}
}
