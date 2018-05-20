package com.anish.api.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anish.api.exceptions.ResourceNotFoundException;
import com.anish.api.tags.Tag;
import com.anish.api.tags.TagsRepository;

@RestController
public class WCObjectController {

	private final String urlPath = "/api/v1/objects";
	@Autowired
	WCObjectsRepository wcobjectRepository;
	
	
	@Autowired
	TagsRepository tagRepository;
	
	@Autowired
	WCobjectDAO objectDao;
	
	@GetMapping(value = urlPath)
	public List<WCObject> getAllObjects() {
		return wcobjectRepository.findAll();
	}
	
	@GetMapping(value = urlPath + "/{id}")
	public WCObject getObject(@PathVariable Long id) {
		return wcobjectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("objects", "id", id));
	}

	@PostMapping(value = urlPath)
	public WCObject createObject(@RequestBody WCObject entity) {
		entity = wcobjectRepository.save(entity);
		return entity;
	}
	
	@PutMapping(value = urlPath + "/{id}")
	public WCObject updateObject(@PathVariable Long id, @RequestBody WCObject newWCObject) {
		WCObject wcObject = wcobjectRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Objects", "id", id));
		newWCObject.setId(wcObject.getId());
    	newWCObject = wcobjectRepository.save(newWCObject);
    	return newWCObject;
	}
	
	@PostMapping(value = urlPath + "/{id}/associateTags")
	public WCObject associateTags(@PathVariable Long id, @RequestBody Map<String , Object> tagMap) {
		try {
			WCObject wcObject = wcobjectRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Objects", "id", id));
			List<Integer> tagIds = (ArrayList<Integer>)tagMap.get("tagIds");
			List<Long> tagas = new ArrayList<Long>();
			for (Integer ids : tagIds) {
				tagas.add(Long.valueOf(ids));
			}
			List<Tag> tags = tagRepository.findByIdIn(tagas);
			wcObject.setTags(tags);
			wcObject = wcobjectRepository.save(wcObject);
			objectDao.insertWCObject(wcObject);
			return wcObject;
		} catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping(value = urlPath + "/{id}/search")
	public WCObject searchObject(@PathVariable Long id) {
		return objectDao.getWCObjectById(id+"");
	}
	
}
