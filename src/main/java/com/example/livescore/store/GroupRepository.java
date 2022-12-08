package com.example.livescore.store;

import com.example.livescore.models.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GroupRepository extends JpaRepository<GroupEntity,Long>{
}
