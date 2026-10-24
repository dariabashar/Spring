create table students (
    id bigserial primary key,
    uni_id varchar(9) not null unique,
    full_name varchar(255) not null
);
