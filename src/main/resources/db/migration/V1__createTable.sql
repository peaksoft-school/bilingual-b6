create table auth_info
(
    id       bigserial not null
        primary key,
    email    varchar(255),
    password varchar(255),
    role     varchar(255)
);

create table clients
(
    id           bigserial not null
        primary key,
    first_name   varchar(255),
    last_name    varchar(255),
    auth_info_id bigserial
        constraint fk6pq0b7u93ncu6jjlemlyajbry
            references auth_info
);

create table tests
(
    id                bigserial not null
        primary key,
    is_active         boolean,
    short_description varchar(10000),
    title             varchar(10000)
);

create table contents
(
    id           bigserial not null
        primary key,
    content      varchar(10000),
    content_type varchar(255)
);

create table results
(
    id                 bigserial not null
        primary key,
    date_of_submission timestamp,
    final_score        integer,
    status             varchar(255),
    client_id          bigserial
        constraint fk2gs18ufsnwjlm5qbjpknpm3e
            references clients,
    test_id            bigserial
        constraint fke9uvk96os1lxpp8pf93p13lmv
            references tests
);

alter table results
    owner to postgres;



create table questions
(
    id                  bigserial not null
        primary key,
    correct_answer      varchar(10000),
    duration            integer,
    is_active           boolean,
    min_number_of_words integer,
    number_of_replays   integer,
    option_type         varchar(255),
    passage             varchar(10000),
    question_type       varchar(255),
    statement           varchar(10000),
    title               varchar(10000),
    content_id          bigserial
        constraint fk93r2rqdwq1ki0l45w8v4s2uoo
            references contents,
    test_id             bigserial
        constraint fkoc6xkgj16nhyyes4ath9dyxxw
            references tests
);

create table options
(
    id      bigserial not null
        primary key,
    is_true boolean,
    option  varchar(10000)
);

create table questions_options
(
    question_id bigserial
        constraint fkmi5crpqara9iodbjoerxy3up6
            references questions,
    options_id  bigserial not null
        primary key
        constraint fk7boyu2mls3t78taee802fembf
            references options
);

create table question_answers
(
    id              bigserial not null
        primary key,
    number_of_words integer,
    score           integer,
    content_id      bigserial
        constraint fk3p55kpyvbi53w4c4q75f8gupo
            references contents,
    question_id     bigserial
        constraint fkrms3u35c10orgjqyw03ajd7x7
            references questions,
    result_id       bigserial
        constraint fk9hxsb9u0vmaxe9kmbrngf0xv4
            references results
);

create table question_answers_options
(
    question_answer_id bigserial
        constraint fk2lq849dkvyh777or840b9s8yf
            references question_answers,
    options_id         bigserial not null
        primary key
        constraint fkay62gg09ornlw8gr0rk9vx2fb
            references options
);


