package com.anish.api.objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WCObjectsRepository extends JpaRepository<WCObject, Long> {

}
