create table users
(
    name                 text,
    surname              text,
    img                  text,
    login                text,
    password             text,
    last_login_date_time timestamp,
    profession           text,
    id                   serial
        constraint users_pk
            primary key
);

alter table users
    owner to postgres;

INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('John', 'Black', 'https://img.freepik.com/free-photo/inside-portrait-of-confident-young-man-in-white-clothes-posing-with-charming-smile-over-isolated-wall_291650-95.jpg?w=740&t=st=1710269612~exp=1710270212~hmac=21ec49050565c93e301030c28f4daed2d5b08e0c86153223d01ecf85923025b1', '1', '1', '2024-03-13 19:42:40.000000', 'Manager', 2);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Jane', 'Smith', 'https://img.freepik.com/free-photo/stylish-confident-businesswoman-smiling_176420-19466.jpg?w=740&t=st=1710681082~exp=1710681682~hmac=86aa23d809b84a2ec80ae020808e4f5a9bd2670a12db1a45d57d1a15d12d6e35', '2', '2', '1989-01-07 15:13:55.000000', 'Accountant', 3);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Jack', 'Brown', 'https://img.freepik.com/free-photo/handsome-bearded-guy-posing-against-the-white-wall_273609-20597.jpg?w=740&t=st=1710337219~exp=1710337819~hmac=5cc2f97c6f732663872cb5faa6f6e015f72f23e5a32be7588a4e4d2be38c044b', '3', '3', '2002-09-26 15:14:27.000000', 'Football player', 4);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Patricia', 'Lee', 'https://img.freepik.com/free-photo/young-happy-female-student-in-glasses-smiling-joyful_176420-20748.jpg?w=740&t=st=1710681170~exp=1710681770~hmac=fdbe1a9acefa3b7a520dd90ad4fac8491459089c78c2093ffbf1c12c3dabbea9', '4', '4', '1997-12-21 15:15:20.000000', 'Financial director', 5);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Nick', 'Anderson', 'https://img.freepik.com/free-photo/waist-up-portrait-handsome-serious-unshaven-male-keeps-hands-together-dressed-dark-blue-shirt-has-talk-with-interlocutor-stands-against-white-wall-self-confident-man-freelancer_273609-16320.jpg?w=740&t=st=1710658892~exp=1710659492~hmac=83797e7db3c1a60bb670bfa4b2db7a9b7aa04af10fbc438874323d2fc1b88972', '5', '5', '1995-06-15 15:19:48.000000', 'Plumber', 6);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Linda', 'Clark', 'https://img.freepik.com/premium-photo/young-woman-standing-with-her-arms-crossed_1368-123664.jpg?w=740', '6', '6', '1993-11-20 15:20:22.000000', 'Housewife', 7);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Robby', 'Williams', 'https://img.freepik.com/free-photo/handsome-bearded-guy-posing-against-white-wall_273609-20597.jpg?w=740&t=st=1710681369~exp=1710681969~hmac=c17f492df45f20aa0c7e1a05384ee11b4fa196fe6163d962b2a06121e43a60dc', '7', '7', '1989-02-23 15:20:35.000000', 'Singer', 8);
INSERT INTO public.users (name, surname, img, login, password, last_login_date_time, profession, id) VALUES ('Jennifer', 'Torres', 'https://img.freepik.com/free-photo/creative-office-worker-relaxing-after-long-day-at-work-positive-good-looking-female-with-blond-hair-in-blue-blouse-holding-hands-in-pockets-and-smiling-expressing-confidence-over-gray-wall_176420-25026.jpg?w=740&t=st=1710681431~exp=1710682031~hmac=111711ef9eeff05c0493a649f8b7314454deac9606b89ca612453e04f653b892', '8', '8', '2000-05-09 15:20:50.000000', 'Singer', 9);
