package com.anish.api.tags;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.anish.api.categories.Category;
import com.anish.api.objects.WCObject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Tag")
public class Tag {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@ManyToOne
    @JoinColumn(name="categoryId", nullable=false)
    @JsonBackReference(value="category-tag")
    private Category category;
	
	@OneToMany
	@JoinColumn(name="wcobjectId", nullable=false)
    @JsonBackReference(value="wcobject-tag")
	private List<WCObject> wcObject;
	
	public Tag() {
		
	}
	
	public Tag(Long id, String name) {
		this.id = id;
		this.name = name;
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
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}