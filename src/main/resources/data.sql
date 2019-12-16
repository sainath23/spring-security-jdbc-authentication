INSERT INTO my_users (username, password, enabled) values ('sainath', 'sainath', 'true');
INSERT INTO my_users (username, password, enabled) values ('admin', 'admin', 'true');

INSERT INTO my_authorities (username, authority) values ('sainath', 'ROLE_USER');
INSERT INTO my_authorities (username, authority) values ('admin', 'ROLE_ADMIN');