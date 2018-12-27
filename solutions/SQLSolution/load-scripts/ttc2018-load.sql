-- Populate posts table
COPY posts FROM 'PATHVAR/csv-posts-initial.csv' WITH DELIMITER '|' CSV;

-- Populate comments table
COPY comments FROM 'PATHVAR/csv-comments-initial.csv' WITH DELIMITER '|' CSV;

-- Populate users table
COPY users FROM 'PATHVAR/csv-users-initial.csv' WITH DELIMITER '|' CSV;

-- Populate friends table
COPY friends FROM 'PATHVAR/csv-friends-initial.csv' WITH DELIMITER '|' CSV;

-- Populate likes table
COPY likes FROM 'PATHVAR/csv-likes-initial.csv' WITH DELIMITER '|' CSV;
