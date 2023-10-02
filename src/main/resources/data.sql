INSERT INTO user_info (first_name, last_name, date_of_birth)
VALUES ('Admin', 'Admin', '31.01.2023'),
       ('User', 'User', '31.01.2023');

INSERT INTO security_credenttials (user_login, user_password, user_role, user_info_id, user_email, activation_code, active)
VALUES ('Admin', '$2a$12$t2YKNA/EgVAFlD0VJppEweb6hSt7UUl8u05NgriutetSuA23cWTBm', 'ADMIN', 'childrenpage65@gmail.com',
        '123-qaz-456-WSX-789-edc', false),
       ('User', '$2a$12$t2YKNA/EgVAFlD0VJppEweb6hSt7UUl8u05NgriutetSuA23cWTBm', 'USER', 'dodaga5493@klanze.com',
        '034b5b81-ce3c-4a2a-8e27-69695ecff5da', false);

INSERT INTO description_file (name_file, description_file, author, path_to_file, categories, genres)
VALUES ('Три поросенка', 'Fairy tales', 'Михалков С.В.', 'tri-porosenka.pdf', 'FAIRY_TALES', 'FAIRY_TALES'),
       ('Маша и медведь на рыбалке', 'Coloring', 'Кузоков О.Г.', 'masha-i-medved-na-rybalke.jpg', 'CARTOONS', 'COLORING_PAGES'),
       ('Колобок', 'Audio', 'Толстой А.Н.', 'Kolobok.mp3', 'FAIRY_TALES', 'AUDIO_FAIRY_TALES'),
       ('Алиса в стране чудес', 'Fairy tales', 'Льюис Кэрролл', 'Алиса-в-стране-чудес.txt', 'FAIRY_TALES', 'FAIRY_TALES'),
       ('Spiderman', 'Coloring', 'not known', 'Spiderman.jpg', 'COMICS', 'COLORING_PAGES');
