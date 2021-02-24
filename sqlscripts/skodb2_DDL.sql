drop database if exists skodb2;
create database skodb2;
use skodb2;

-- Vi har valt att inte definiera några "on delete" på tabellernas foreign keys 
-- för att då kunna ha en komplett historik på allt som har funnits i databasen. 
-- Default-beteendet är då restrict som innebär att inget tas bort.

create table ort
(id int not null auto_increment,
namn varchar(50) not null,
primary key(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table kategori
(id int not null auto_increment,
namn varchar(50) not null,
primary key(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table märke
(id int not null auto_increment,
namn varchar(50) not null,
primary key(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table kund
(id int not null auto_increment,
namn varchar(50) not null,
ortid int not null,
användarnamn varchar(50) not null,
lösenord varchar(50) not null,
primary key(id),
foreign key(ortid) references ort(id), -- kan även sätta ON DELETE SET NULL om en stad skulle försvinna av oförklarliga skäl, men då har vi inte längre en komplett historik...
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table sko
(id int not null auto_increment,
namn varchar(50) not null,
pris int not null,
storlek int not null,
färg enum ('Svart', 'Rosa', 'Röd', 'Vit', 'Camo', 'Grön'),
märkeid int not null,
lagerstatus int,
primary key(id),
foreign key(märkeid) references märke(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table beställning
(id int not null auto_increment,
kundid int not null,
primary key(id),
datum date not null default (curdate()),
avslutad boolean not null default false,
foreign key(kundid) references kund(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table beställningsmap
(id int not null auto_increment,
primary key(id),
skoid int not null,
beställningsid int not null,
foreign key(skoid) references sko(id),
foreign key(beställningsid) references beställning(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table skokategorimap
(id int not null auto_increment,
primary key(id),
skoid int not null,
kategoriid int not null,
foreign key(skoid) references sko(id),
foreign key(kategoriid) references kategori(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table betyg
(värde int not null,
betygtext varchar(50) not null,
primary key(värde));

create table betygsättning
(id int not null auto_increment,
primary key(id),
skoid int not null,
kundid int not null,
betyg int not null,
kommentar varchar(16000),
foreign key(skoid) references sko(id),
foreign key(kundid) references kund(id),
foreign key(betyg) references betyg(värde),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);

create table slutilager
(id int not null auto_increment,
primary key(id),
skoid int not null,
datum date not null default (curdate()),
foreign key(skoid) references sko(id),
created timestamp default CURRENT_TIMESTAMP,
lastUpdated timestamp default null on update CURRENT_TIMESTAMP);




insert into ort (namn) values ('Uppsala'), ('Stockholm'), ('Säffle'), ('Åmål'), ('Göteborg');

insert into kategori (namn) values ('Damskor'), ('Herrskor'), ('Löparskor'), ('Sandal'), ('Toffla'), 
('Fotbollssko'), ('Stövlar'), ('Kängor'), ('Seglarskor');

insert into märke (namn) values ('Nike'), ('Ecco'), ('Adidas'), ('Birkenstock'), ('Puma'), ('Dr Martens'), ('Lacoste');

insert into kund (namn, ortid, användarnamn, lösenord) values 
('Jens Jensson', 1, 'Jenne32', 'asdf123'), 
('Björn Björnsson', 1, 'Björne62', '123asdf'), 
('Per Persson', 2, 'Perra', 'qwert234'),
('Lars Larsson', 3, 'Lasseboi666', 'qwert123'), 
('Anders Andersson', 4, 'Andy', 'qwert1337'), 
('Magnus Magnusson', 4, 'Mange', 'asdf123'), 
('Samuel Samuelsson', 5, 'Samme23', 'asdf123'), 
('Bengt Bengtsson', 5, 'Bengan', 'asdf123'),
('Magdalena Magnusson', 4, 'Maggan', 'asdf123');

insert into sko (namn, pris, storlek, färg, märkeid, lagerstatus) values 
('Ecco-sandalen', 399, 38, 'Svart', 2, 1), 
('Nike fotbollssko', 999, 40, 'Grön', 1, 2), 
('Dunder-Kängan', 2400, 46, 'Camo', 6, null), 
('RUNFALCON', 479, 41, 'Svart', 3, 3), 
('Helt vanlig toffla', 2499, 44, 'Vit', 4, 39),
('Puma fotbolsssko', 899, 37, 'Svart', 5, 44), 
('Air Jordan', 999, 42, 'Vit', 1, 23),
('AIR FORCE 1', 499, 40, 'Rosa', 1, 34);

insert into beställning (kundid, datum) values 
(1, '2020-01-01'), 
(2, '2020-01-01'), 
(3, '2020-04-01'), 
(4, '2020-05-01'), 
(5, '2020-06-01'), 
(6, '2020-07-01'), 
(7, '2020-08-01'), 
(8, '2020-09-01');

insert into beställningsmap (skoid, beställningsid) values (1, 1), (2, 2), (3, 3), 
(4, 4), (5, 5), (6, 6), (7, 7), (8, 8),(1, 8);

insert into skokategorimap (skoid, kategoriid) values (1, 4), (1, 1), (1, 2), 
(2, 6), (2, 2),
(3, 8), (3, 1),
(4, 1), (4, 2),
(5, 1), (5, 2), (5, 5),
(6, 1), (6, 2), (6, 6),
(7, 1), (7, 2), (7, 3),
(8, 1), (8, 2), (8, 3);

insert into betyg(värde, betygtext) values
(1, 'Mycket missnöjd'),
(2, 'Ganska missnöjd'),
(3, 'Ganska nöjd'),
(4, 'Nöjd'),
(5, 'Väldigt nöjd');

insert into betygsättning (skoid, kundid, betyg, kommentar) values
(1, 1, 2, 'Turbonajs skor, väldigt skoiga'), 
(3, 2, 1, null),
(1, 2, 1, 'Väldigt dåligt'),
(1, 3, 5, null),
(1, 5, 5, null),
(1, 4, 3, null),
(1, 4, 5, null),
(1, 4, 5, null);

-- Det känns rimligt att söka efter namn, tabellerna ändras inte mycket.
create index IX_ortNamn on ort(namn); 
create index IX_kategoriNamn on kategori(namn);
create index IX_märkeNamn on märke(namn);	
prodAvg

delimiter //
create function prodAvg(productId int) 
returns float
Reads SQL data
begin
	declare temp float default 0;
	select avg(betyg) into temp from betygsättning where skoid=productId;
	return temp;
end//
delimiter ;

-- select prodAvg(1);

create view ratings as
select s.namn, round(prodAvg(s.id), 1) as AverageRating , b.betygtext
from sko s
left join betyg b
on b.värde = round(prodAvg(s.id))
order by värde desc;

-- select * from ratings;

delimiter //
create procedure rate (skoidIN int, kundidIN int, betygIN int, kommentarIN varchar(16000))
BEGIN
declare exit handler for 1452
    begin
    rollback;
    select ('Felaktiga värden');
    resignal set message_text = 'Felaktiga värden';
end;
declare exit handler for sqlexception
begin
    rollback;
    select ('Något gick fel, rollback utförd');
    resignal set message_text = 'Något gick fel, rollback utförd';
end;

start transaction;
insert into betygsättning (skoid, kundid, betyg, kommentar) values (skoidIN, kundidIN, betygIN, kommentarIN);
-- select * from betygsättning;
-- rollback;
commit;
END//
delimiter ;


delimiter //
create procedure addToCart(kundIdIN int, beställningsIdIN int, skoIdIN int)
BEGIN
declare lastId int default 0;
declare no_longer_sold condition for sqlstate '45000';

declare exit handler for SQLEXCEPTION
begin
    rollback;
    select('Något gick fel, rollback utförd');
    resignal set message_text = 'Något gick fel, rollback utförd';
end;
declare exit handler for 1452
begin
    rollback;
    select('Felaktiga värden, rollback utförd');
    resignal set message_text = 'Felaktiga värden, rollback utförd';
end;
declare exit handler for no_longer_sold
begin
    rollback;
    select('Skon säljs inte längre.');
    resignal set message_text = 'Skon säljs inte längre.';
end;

start transaction;
	if beställningsIdIN is null or (select count(*) from beställning where beställning.id like beställningsIdIN) = 0 then
    insert into beställning (kundid, datum) values (kundIdIN, curdate());
    select last_insert_id() into lastId;    
    insert into beställningsmap(skoId, beställningsId) values (skoIdIN, lastId);
    else
    insert into beställningsmap(skoId, beställningsId) values (skoIdIN, beställningsIdIN);
    end if;
    
    if (select sko.lagerstatus from sko where sko.id=skoIdIN) is null then
    signal no_longer_sold;  -- nytt
    end if;
    
    if (select sko.lagerstatus from sko where sko.id=skoIdIN) > 0 then
    update sko set lagerstatus = lagerstatus - 1 where sko.Id = skoIdIN;
    end if;
    
-- rollback;
commit;
	
END //
delimiter ;


delimiter //
create trigger after_lagerstatus_update after update on sko
for each row
begin 
	if new.lagerstatus = 0 then
    insert into slutilager(skoId) values (new.id);
    end if;
end //
delimiter ;

delimiter //
create procedure getProdAvg(in productId int, out average double)
begin
	set average = round(prodAvg(productId), 1);
end //
delimiter ;

-- tester

/*
call addToCart(2, 1, 1);
call addToCart(2, 1, 1);
call addToCart(2, 40, 3);
-- call addToCart(23, 40, 400);

select * from beställningsmap;
select * from slutilager;
*/

-- select * from beställning;
-- select * from sko;
-- select * from kund;
-- select * from slutilager;
-- select * from beställningsmap;

select * from ratings;

