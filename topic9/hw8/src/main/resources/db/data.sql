DROP TABLE IF EXISTS user_to_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS books_in_wishlist;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS wishlist;

create table books(
    id int primary key auto_increment,
    title varchar,
    author varchar,
    isbn varchar
);
CREATE TABLE users(
                      id int primary key auto_increment,
                      login VARCHAR(25) not null,
                      password VARCHAR(255) not null
);

create table wishlist(
    id int primary key auto_increment,
    user_id int not null,
    constraint fk_user_id_to_user foreign key (user_id) references users(id)
);

create table roles
(
    id   int primary key auto_increment,
    role varchar(30) not null
);


create table books_in_wishlist(
    book_id int not null,
    wishlist_id int not null,
    constraint fk_book_to_book_wishlist foreign key (book_id) references books(id),
    constraint fk_wishlist_to_book_wishlist foreign key (wishlist_id) references wishlist(id)
);




create table user_to_roles (
        user_id int not null,
        role_id int not null,
        constraint fk_user_to_role_user foreign key (user_id) references users(id),
        constraint fk_user_to_role_role foreign key (role_id) references roles(id)
);

insert into users (login, password) values
                                        ('admin', 'password'),
                                        ('user', 'pass');

insert into roles (role) values
                                         ('USER'),
                                         ('GUEST');

insert into user_to_roles values
                                 (1, 1),
                                 (2, 2)
