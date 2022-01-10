-- https://docs.spring.io/spring-security/reference/5.7/servlet/authentication/passwords/jdbc.html


DROP TABLE IF EXISTS authorities;

DROP table if exists users CASCADE;



CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (username)
);
  
CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);

/*
SELECT * FROM users;
SELECT * FROM authorities;
SELECT * FROM usuario_roles;
*/

DELETE FROM authorities;
DELETE FROM usuario_roles;
DELETE FROM users;
	
insert into users (username, password,enabled)
values('superAdmin@gmail.com','$2a$10$V1r4djNJed8bvEGnLMQJce7hhoL5vcymIpwJhBWxKz8HByKPJewtK',true), -- senha 9999
('funcionario@gmail.com', '$2a$10$nFf6Im.KutzkUvjDPsua/Ob1W8VgD31ARgcR.okRf2FOY7BHb2EsS', true), --senha 1111
('cliente@gmail.com', '$2a$10$HiCPekkocMWOQQj8EbCbMODZvakReM0dtzDeqRUyKJhs7jA4plGFa', true); -- senha 2222

insert into authorities
values('superAdmin@gmail.com','ROLE_FUNCIONARIO'), -- senha 9999
('superAdmin@gmail.com','ROLE_SUPER_ADMIN'), -- senha 9999
('funcionario@gmail.com','ROLE_FUNCIONARIO'), -- senha 1111
('cliente@gmail.com','ROLE_CLIENTE'); -- senha 2222


DELETE FROM tb_cliente;

insert into tb_cliente
select 
nextval('hibernate_sequence'),
'22222222222',
'cliente@gmail.com',
'Rua x, xxxxx',
'João',
10000,
'222222';
