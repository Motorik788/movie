create table USER_INFO
(
    ID_USER integer primary key,
    LOGIN varchar(20) unique,
    PASSWORD varchar(8),
    NAME varchar(20),
    IS_ADMIN varchar(5)
);

create table Movie_INFO
(
    IMDB varchar(10) primary key,
    MOVIE_TYPE varchar(50),
    MOVIE_NAME varchar(50),
    GENRE varchar(50),
    RELEASE date,
    RATING number(3,1),
    DESCRIPTION varchar(255)   
);

create table Comments
(
    ID varchar(10) primary key,
    ID_IMDB varchar(10),
    AUTHOR_ID integer,
    CREATE_DATE date,
    TEXT varchar(255),
    RATING number(3,1)
);

alter table Comments
    add constraint IMDB_FK foreign key(ID_IMDB) references Movie_INFO(IMDB)
    add constraint USER_FK foreign key(AUTHOR_ID) references USER_INFO(ID_USER);
        
insert into USER_INFO values (1,'admin','admin','Administrator','true');

--drop table Movie_INFO;
--drop table Comments;
--delete from USER_INFO where ID_USER > 1
--
--select * from USER_INFO
--
--insert into Movie_INFO values('1','maslo','maslo','maslo',TO_DATE('1.1.1998','dd.mm.yyyy'),1.5,'govno')
--
--select * from Movie_INFO where extract(YEAR from RELEASE) >= 1998
--delete from Movie_INFO
--
--select * from Comments