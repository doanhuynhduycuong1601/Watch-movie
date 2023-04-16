create database java4_lab6
go
use java4_lab6

create table users(
	id varchar(100) primary key,
	email varchar(100),
	pass varchar(40),
	img nvarchar(100),
	fullname nvarchar(100),
	phone char(10),
	admins bit default 0,
	gender bit,
	dateCreate date default getdate(),
	birth date,
	existss bit
)

create table country(
	idCountry int identity(1,1) primary key,
	nameCountry nvarchar(100)
)

create table star(
	idStar int identity(1,1) primary key,
	nameStar nvarchar(100),
	imgStar nvarchar(50),
	countryID int,
	birth date,
	gender bit,
	introduce nvarchar(200),
	constraint fk_star_country foreign key(countryID) references country(idCountry)
)

create table director(
	idDirector int identity(1,1) primary key,
	nameDirector nvarchar(100),
	imgDirector nvarchar(50),
	countryID int,
	birth date,
	gender bit,
	introduce nvarchar(200),
	constraint fk_director_country foreign key(countryID) references country(idCountry)
)



--create table videos(
--	idVideo int identity(1,1) primary key,
--	title varchar(100),
--	poster varchar(50),
--	descriptions varchar(300),
--	viewVideo int,
--	active bit default 1,
--	dateUpload date default getdate(),
--	times varchar(100),
--	directorID int,
--	countryID int,
--	constraint fk_videos_star foreign key(directorID) references star(idStar),
--	constraint fk_videos_country foreign key(countryID) references country(idCountry)
--)

create table videos(
	idVideo int identity(1,1) primary key,
	title nvarchar(100),
	poster nvarchar(50),
	viewVideo int,
	descriptions nvarchar(300),
	active bit default 1,
	dateUpload date default getdate(),
	times nvarchar(100),
	directorID int,
	countryID int,
	constraint fk_videoDetail_director foreign key(directorID) references director(idDirector),
	constraint fk_videoDetail_country foreign key(countryID) references country(idCountry),
)

--create table videoDetail(
--	idVideo int primary key,
--	descriptions nvarchar(300),
--	active bit default 1,
--	dateUpload date default getdate(),
--	times nvarchar(100),
--	constraint fk_videoDetail_videos foreign key(idVideo) references videos(idVideo),
--)


create table favorities(
	idFavor bigint identity(1,1) primary key,
	idUser varchar(100),
	idVideo int,
	likeDate date default getdate(),
	likes bit,
	constraint fk_favorities_users foreign key (idUser) references users(id),
	constraint fk_favorities_videos foreign key (idVideo)references videos(idVideo)
)

create table genre(
	id int identity(1,1) primary key,
	names nvarchar(200),
	img nvarchar(100),
	descriptions nvarchar(500)
)

create table genresInVideo(
	id int identity(1,1) primary key,
	id_Genre int,
	id_Video int,
	constraint fk_genresInVideo_genre foreign key (id_Genre) references genre(id),
	constraint fk_genresInVideo_videos foreign key (id_Video) references videos(idVideo)
)

create table starsInVideo(
	id int identity(1,1) primary key,
	id_Star int,
	id_Video int,
	constraint fk_starsInVideo_star foreign key (id_Star) references star(idStar),
	constraint fk_starsInVideo_videos foreign key (id_Video) references videos(idVideo)
)

create table linkVideo(
	id int identity(1,1) primary key,
	urlVideo nvarchar(200),
	id_Video int,
	constraint fk_linkVideo_videos foreign key (id_Video) references videos(idVideo)
)
go
create PROC spFavoriteByYear(@Year INT)
AS
BEGIN
	SELECT v.Title AS 'group', count(f.idFavor) AS 'likes', max(f.LikeDate) AS 'newest',
		min(f.LikeDate) AS 'oldest'
	FROM favorities f JOIN Videos v ON v.idVideo = f.idVideo
WHERE year(f.LikeDate) = @Year
GROUP BY v.Title
END

create table link(
	id int identity(1,1) primary key,
	names nvarchar(100),
	img nvarchar(100),
	linkURL nvarchar(200),
	dates date,
	times nvarchar(100)
)

select * from favorities
select * from link



insert into country values ('VN')
insert into star(nameStar,countryID) values ('Country 2',1)
insert into genre(names) values ('genre 1')


--truncate table favorities

