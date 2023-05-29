ALTER TABLE tournaments
    ADD COLUMN user_id bigint,
ADD CONSTRAINT fk_user
FOREIGN KEY (user_id)
REFERENCES users (user_id);

UPDATE tournaments
SET user_id = 1
WHERE tournament_id = 1;

UPDATE tournaments
SET user_id = 1
WHERE tournament_id = 2;

UPDATE tournaments
SET user_id = 1
WHERE tournament_id = 3;