create table liked
(
    id_from integer
        constraint liked_users_id_fk
            references users,
    id_to   integer
        constraint liked_users_id_fk_2
            references users,
    status  boolean
);

alter table liked
    owner to postgres;

INSERT INTO public.liked (id_from, id_to, status) VALUES (3, 4, true);
INSERT INTO public.liked (id_from, id_to, status) VALUES (3, 5, false);
INSERT INTO public.liked (id_from, id_to, status) VALUES (4, 2, true);
