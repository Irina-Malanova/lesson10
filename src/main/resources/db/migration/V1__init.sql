-- create table products
-- (
--     id    bigserial primary key,
--     title varchar(255),
--     price int
-- );
-- insert into products (title, price)
-- values ('Bread', 25),
--        ('Milk', 80),
--        ('Cheese', 450);

create table categories
(
    id    bigserial primary key,
    title varchar(255)
);
insert into categories (title)
values ('Food');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       int,
    category_id bigint references categories (id)
);
insert into products (title, price, category_id)
values ('Bread', 25, 1),
       ('Milk', 80, 1),
       ('Cheese', 450, 1),
       ('Cheese1', 450, 1),
       ('Cheese2', 450, 1),
       ('Cheese3', 450, 1),
       ('Cheese4', 450, 1),
       ('Cheese5', 450, 1),
       ('Cheese6', 450, 1),
       ('Cheese7', 450, 1),
       ('Cheese8', 450, 1),
       ('Cheese9', 450, 1),
       ('Cheese10', 450, 1),
       ('Cheese11', 450, 1),
       ('Cheese12', 450, 1),
       ('Cheese13', 450, 1),
       ('Cheese14', 450, 1),
       ('Cheese15', 450, 1),
       ('Cheese16', 450, 1),
       ('Cheese17', 450, 1),
       ('Cheese18', 450, 1),
       ('Cheese19', 450, 1),
       ('Cheese20', 450, 1),
       ('Cheese21', 450, 1),
       ('Cheese22', 450, 1),
       ('Cheese23', 450, 1);