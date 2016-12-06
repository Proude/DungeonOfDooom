CREATE database dungeonofdoom;
USE dungeonofdoom;
CREATE table player (username char(40) primary key, password char(40), salt varbinary(32), level int);
CREATE table score (id int primary key auto_increment, username char(40), value int);



