package com.anish.api.tags;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anish.api.categories.Category;

public interface TagsRepository extends JpaRepository<Tag, Long>{
	
	public List<Tag> findByCategory(Category category);

}
