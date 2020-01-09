create database am_db;
use am_db;

CREATE TABLE am_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  login_id varchar(255) NOT NULL,
  password varchar(255) DEFAULT NULL,
  user_name varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  mobile bigint(10) DEFAULT NULL,
  date_of_joining datetime DEFAULT NULL,
  is_active bit(1) NOT NULL,
  created_date datetime DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_d6ikwcv1fdsy9ms19mro8rmo2 (login_id)
);

CREATE TABLE am_asset_type (
  id int(11) NOT NULL AUTO_INCREMENT,
  asset_type_code varchar(255) DEFAULT NULL,
  asset_type_description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE am_asset (
  id int(11) NOT NULL AUTO_INCREMENT,
  asset_no varchar(255) DEFAULT NULL,
  asset_name varchar(255) DEFAULT NULL,
  asset_type_id int(11) DEFAULT NULL,
  user_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FKe2qfcgkkdhwdna5kvj21jpqql (asset_type_id),
  KEY FKarddqxh04k21fjyq4jmh3b1ke (user_id)
);


insert into am_user(login_id, password, is_active) values('vivek', 'pass', true);

select * from am_user;

update am_user set login_id='061a01a98f80f415b1431236b62bb10b'
where id=1;
update am_user set password='1a1dc91c907325c69271ddf0c944bc72'
where id=1;


INSERT INTO `am_db`.`am_asset_type` (`id`,`asset_type_code`,`asset_type_description`) VALUES(1, 'Laptop', 'Laptop');
INSERT INTO `am_db`.`am_asset_type` (`id`,`asset_type_code`,`asset_type_description`) VALUES(2, 'Seat', 'Seat');

INSERT INTO `am_db`.`am_asset` (`id`, `asset_no`, `asset_name`, `asset_type_id`) VALUES ('1', 'pqr-123', 'lenovo E3490', '1');
INSERT INTO `am_db`.`am_asset` (`id`, `asset_no`, `asset_name`, `asset_type_id`) VALUES ('2', 'mnp-4334', 'dell lattitude 5400', '1');

SELECT * FROM am_db.am_asset;


CREATE TABLE am_user_asset_audit (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  asset_id int(11) NOT NULL,
  operation varchar(255) NOT NULL,
  created_date datetime NOT NULL,
  PRIMARY KEY (id)
);


