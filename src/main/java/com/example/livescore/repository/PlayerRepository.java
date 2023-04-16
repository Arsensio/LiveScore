package com.example.livescore.repository;

import com.example.livescore.models.PlayerEntity;
import com.example.livescore.web.players.MinPlayerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query("FROM PlayerEntity p WHERE p.team.teamId = ?1")
    List<PlayerEntity> findAllByTeamId(long teamId);

    @Query(value = "select new com.example.livescore.web.players.MinPlayerDto(p.playerId, p.name, p.surname, p.playerNumber) " +
            "from PlayerEntity p " +
            "where p.team.teamId = ?1")
    List<MinPlayerDto> findAllPlayersOfTeam(Long teamId);

    @Query(value = "select player_number " +
            "from players " +
            "join teams on players.team_id = teams.team_id " +
            "where teams.team_id = ?1", nativeQuery = true)
    List<Integer> findAllPlayerNumbersInTeam(Long teamId);
}
