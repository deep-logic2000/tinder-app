create table messages
(
    id          serial,
    sender_id   integer                 not null
        constraint messages_users_id_fk_2
            references users,
    receiver_id integer                 not null
        constraint messages_users_id_fk
            references users,
    message     text                    not null,
    time        timestamp default now() not null
);

alter table messages
    owner to postgres;

INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (7, 4, 2, 'Hello!', '2024-03-17 15:13:16.773502');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (5, 2, 3, 'What are you doing today evening?', '2024-03-17 15:10:15.947073');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (1, 2, 3, 'Hello', '2024-03-17 14:29:25.448613');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (2, 2, 3, 'How are you?', '2024-03-17 14:29:25.448613');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (6, 2, 4, 'Hi! Let''s go play football today.', '2024-03-17 15:13:16.773502');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (4, 2, 3, 'I''m fine too. Thank you!', '2024-03-17 15:10:15.947073');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (3, 3, 2, 'I''m fine. Thank you. And you?', '2024-03-17 14:29:25.448613');
INSERT INTO public.messages (id, sender_id, receiver_id, message, time) VALUES (8, 4, 2, 'Sorry, but I will be busy. I have some plans for today.', '2024-03-17 15:13:16.773502');
