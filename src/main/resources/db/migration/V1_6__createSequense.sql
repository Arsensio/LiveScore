create table group_info
(
    group_info_id   bigserial primary key,
    group_name      varchar,
    team_name       varchar,
    status          varchar,
    team_logo       varchar,
    tournament_logo varchar,
    draw_count      integer,
    game_played     integer,
    goal_count      integer,
    goal_missed     integer,
    lose_count      integer,
    points          integer,
    win_count       integer,
    tournament_id   bigint not null,
    group_id        bigint not null,
    team_id         bigint not null,
    foreign key (tournament_id)
        references tournaments (tournament_id),
    foreign key (team_id)
        references teams (team_id),
    foreign key (group_id)
        references groups (group_id)
);
