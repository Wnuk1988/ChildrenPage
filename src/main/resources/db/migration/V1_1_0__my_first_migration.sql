create sequence description_id_seq;

create table user_info
(
    id            bigserial
        primary key,
    first_name    varchar(50) default 'not indicated'::character varying,
    last_name     varchar(50) default 'not indicated'::character varying,
    date_of_birth varchar     default 'not indicated'::character varying
);

alter table user_info
    owner to postgres;

create table description_file
(
    id               bigint      default nextval('description_id_seq'::regclass) not null
        constraint description_pkey
            primary key,
    name_file        varchar                                                     not null,
    description_file varchar     default 'not indicated'::character varying      not null,
    author           varchar(50) default 'not known'::character varying,
    path_to_file     varchar                                                     not null
        unique,
    categories       varchar                                                     not null,
    genres           varchar                                                     not null
);

alter table description_file
    owner to postgres;

create table security_credentials
(
    id              bigserial
        constraint site_security_pkey
            primary key,
    user_login      varchar                                   not null
        constraint site_security_login_key
            unique,
    user_password   varchar                                   not null,
    user_role       varchar default 'USER'::character varying not null,
    user_info_id    bigint                                    not null
        constraint site_security_user_info_id_key
            unique
        constraint site_security_user_info_id_fk
            references user_info
            on update cascade on delete cascade,
    user_email      varchar                                   not null
        constraint security_credentials_email_key
            unique,
    activation_code varchar
        unique,
    active          boolean                                   not null
);

alter table security_credentials
    owner to postgres;

create table l_user_file
(
    id                  bigserial
        constraint favorites_pkey
            primary key,
    description_file_id bigint not null
        constraint l_user_file_description_file_id_fk
            references description_file
            on update cascade on delete cascade,
    user_info_id        bigint not null
        constraint l_user_file_user_info_id_fk
            references user_info
            on update cascade on delete cascade
);

alter table l_user_file
    owner to postgres;