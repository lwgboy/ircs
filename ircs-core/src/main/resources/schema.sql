create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

create table oauth_client_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

create table oauth_access_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BLOB,
  refresh_token VARCHAR(255)
);

create table oauth_refresh_token (
  token_id VARCHAR(255) PRIMARY KEY,
  token BLOB,
  authentication BLOB
);

create table oauth_code (
  code VARCHAR(255),
  authentication BLOB
);

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `fee` double(11,2) NOT NULL,
  `groupname` varchar(255) NOT NULL,
  `id` bigint(20) NOT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `account_roles`;
CREATE TABLE `account_roles` (
  `account_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FKtp61eta5i06bug3w1qr6286uf` (`account_id`),
  CONSTRAINT `FKtp61eta5i06bug3w1qr6286uf` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `content_price`;
CREATE TABLE `content_price` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `type` char(3) NOT NULL,
  `price` double(11,2) NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `msg_log`;
CREATE TABLE `msg_log` (
  `msg_key` varchar(40) NOT NULL,
  `groupname` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `res_date` datetime DEFAULT NULL,
  `sent_date` datetime DEFAULT NULL,
  `done_date` datetime DEFAULT NULL,
  `report_date` datetime DEFAULT NULL,
  `route_date` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user_key` varchar(40) DEFAULT NULL,
  `msg_type` char(3) DEFAULT NULL,
  `content_type` char(3) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `callback` varchar(20) DEFAULT NULL,
  `message` varchar(2000) DEFAULT NULL,
  `result_code` char(4) DEFAULT NULL,
  `result_desc` varchar(100) DEFAULT NULL,
  `net` char(3) DEFAULT NULL,
  `sender` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`msg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
