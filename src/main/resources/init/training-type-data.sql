CREATE TABLE IF NOT EXISTS training_types
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO training_types (id, name)
VALUES (1, 'Fitness'),
       (2, 'Yoga'),
       (3, 'Zumba'),
       (4, 'Stretching'),
       (5, 'Resistance')
ON CONFLICT (id) DO NOTHING;