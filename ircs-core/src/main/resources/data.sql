INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES
('my-client-with-registered-redirect',	'oauth2-resource',	NULL,	'read,trust',	'authorization_code',	'http://anywhere?key=value',	'ROLE_CLIENT',	NULL,	NULL,	'{}',	''),
('normal-app',	'oauth2_application',	NULL,	'read,write',	'authorization_code,implicit',	'',	'ROLE_CLIENT',	3600,	10000,	'{}',	''),
('register-app',	'oauth2_application',	'$2a$10$emuO8h7Dni/XOLUVlGtzP.6uDl.9ALoQUlm3hIFGStuF7dCKg6C/O',	'read',	'client_credentials',	'',	'ROLE_REGISTER',	NULL,	NULL,	'{}',	''),
('trusted-app',	'oauth2_application',	'$2a$10$PpsL0aTHjNfDW2F4bYagouHBpfz5bQ6OVGsN8OAljq8SQvWuBjG96',	'read,write',	'client_credentials,password,refresh_token',	'',	'ROLE_TRUSTED_CLIENT',	3600,	10000,	'{}',	'');

INSERT INTO `account` (`fee`, `groupname`, `id`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `email`, `enabled`, `first_name`, `last_name`, `password`, `username`) VALUES
(988670.00,	'TEST',	3,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	CONV('1', 2, 10) + 0,	'vaxzeen@hotmail.com',	CONV('1', 2, 10) + 0,	'Hasoo',	'Kim',	'$2a$10$hcU83W1HtES0UIb1WdtsaeuHS9tDMcSGP2WwR9m1bmwTz56PRnK7W',	'test');

INSERT INTO `content_price` (`seq`, `account_id`, `type`, `price`) VALUES
(1,	1,	'SMS',	10),
(2,	1,	'TXT',	20);
