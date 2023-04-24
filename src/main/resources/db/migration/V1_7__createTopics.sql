create table topics
(
    topic_id      bigserial primary key,
    topic_name    varchar,
    tournament_id bigint not null,
    foreign key (tournament_id)
        references tournaments (tournament_id)
)