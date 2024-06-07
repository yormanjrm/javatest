CREATE TABLE IF NOT EXISTS credentials (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS trainers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    main_pokemon VARCHAR(255) NOT NULL
);

INSERT INTO credentials (name, email, password)
VALUES ('John Doe', 'john.doe@example.com', '$2a$10$/iOt2Bx.vd4PHhWWJttE3.mNlX/jriVf4dASqOwbijXR.0goXmyRe');

INSERT INTO trainers (name, main_pokemon) VALUES
('Ash', 'Pikachu'),
('Serena', 'Sylveon'),
('Red', 'Charizard'),
('Misty', 'Starmie'),
('Brock', 'Onix'),
('Gary', 'Blastoise'),
('Erika', 'Vileplume'),
('Lt. Surge', 'Raichu'),
('Sabrina', 'Alakazam'),
('Koga', 'Venomoth'),
('Blaine', 'Magmar'),
('Giovanni', 'Nidoking'),
('Lance', 'Dragonite'),
('Falkner', 'Pidgeot'),
('Bugsy', 'Scyther'),
('Whitney', 'Miltank'),
('Morty', 'Gengar'),
('Jasmine', 'Steelix'),
('Chuck', 'Poliwrath'),
('Pryce', 'Piloswine'),
('Clair', 'Kingdra'),
('Roxanne', 'Nosepass'),
('Brawly', 'Makuhita');