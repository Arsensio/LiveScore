package com.example.livescore.repository;

import com.example.livescore.models.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    @Query(value = "SELECT * FROM groups where tournament_id = ?1 order by group_name", nativeQuery = true)
    List<GroupEntity> findAllByTournamentId(long tournamentId);
}
