insert into book (id, title, authors, status) values (1, 'First book', 'Jan Kowalski', 'FREE');
insert into book (id, title, authors, status) values (2, 'Second book', 'Zbigniew Nowak', 'FREE');
insert into book (id, title, authors, status) values (3, 'Third book', 'Janusz Jankowski', 'FREE');
insert into book (id, title, authors, status) values (4, 'Starter kit book', 'Kacper Ossoliński', 'FREE');
insert into book (id, title, authors, status) values (5, 'Z kamerą wśród programistów', 'Krystyna Czubówna', 'MISSING');

insert into user (id, user_name, password, enabled, role) values (1, 'admin', '$2a$10$aUHa71mxFxfZoTKy7s9MzuLToMXiJrFYe4EnRb.xFTP0f5SdH7r/2', 1, 'ADMIN');
insert into user (id, user_name, password, enabled, role) values (2, 'user', '$2a$10$EXZplVAU9fBX2rtF9Y8ANePArB8AckMPVUbCUeVx5tFj8Dbphg5Py', 1, 'USER');