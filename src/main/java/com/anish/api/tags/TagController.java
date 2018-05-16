package com.anish.api.tags;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anish.api.categories.CategoriesRepository;
import com.anish.api.exceptions.ResourceNotFoundException;

@RestController
public class TagController {

	private final String urlPath = "/api/v1/categories/{categoryId}/tags";
	
	@Autowired
	TagsRepository tagRepository;
	
	@Autowired
	CategoriesRepository categoryRepository;
	
	@GetMapping(value = urlPath)
	public List<Tag> getAllTags(@PathVariable Long categoryId) {
		return tagRepository.findByCategory(categoryRepository.findById(categoryId).get());
	}
	
	@GetMapping(value = urlPath + "/{id}")
	public Tag getTags(@PathVariable Long id) {
		return tagRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Tag", "id", id));
	}
	
	@PutMapping(value = urlPath + "/{id}")
	public Tag updateTag(@PathVariable Long id, @RequestBody Tag newTag) {
		Tag oldTag = tagRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Tag", "id", id));
		newTag.setId(oldTag.getId());
		newTag = tagRepository.save(newTag);
		return newTag;
	}

	@PostMapping(value = urlPath)
	public Tag createTag(@PathVariable Long categoryId, @RequestBody Tag newTag) {
		newTag.setCategory(categoryRepository.findById(categoryId).get());
		return tagRepository.save(newTag);
	}


}