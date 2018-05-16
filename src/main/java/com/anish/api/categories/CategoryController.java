package com.anish.api.categories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anish.api.exceptions.ResourceNotFoundException;

@RestController
public class CategoryController {

	private final String urlPath = "/api/v1/categories";
	
	@Autowired
	CategoriesRepository categoryRepository;
	
	@GetMapping(value = urlPath)
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	@GetMapping(value = urlPath + "/{id}")
	public Category getCategpru(@PathVariable Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
	}
	
	@PutMapping(value = urlPath + "/{id}")
	public Category updateCategory(@PathVariable Long id, @RequestBody Category newCategory) {
		Category oldCategory = categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
		newCategory.setId(oldCategory.getId());
		newCategory = categoryRepository.save(newCategory);
		return newCategory;
	}

	@PostMapping(value = urlPath)
	public Category createCategory(@RequestBody Category newCategory) {
		return categoryRepository.save(newCategory);
	}


}
