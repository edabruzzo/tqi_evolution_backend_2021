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

DELETE FROM authorities;
DELETE FROM usuario_roles;
DELETE FROM users;
	
insert into users (username, password)
values('99999999999','$2a$10$V1r4djNJed8bvEGnLMQJce7hhoL5vcymIpwJhBWxKz8HByKPJewtK');

insert into authorities
select '99999999999',
'ROLE_FUNCIONARIO';

insert into authorities
select '99999999999',
'ROLE_SUPER_ADMIN';



insert into tb_cliente
select 
nextval('hibernate_sequence'),
'11111111111',
'asdf@gmail.com',
'Rua x, xxxxx',
'João',
10000,
'111111111';





*/