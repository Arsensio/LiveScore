Create table goal_info
(
    id        bigint not null,
    player_id bigint null,
    constraint fk_group
        foreign key (id)
            references events (event_id),
    primary key (id)
);



