create database `riped-config`;

USE `riped-config`;
DROP TABLE `properties`;
CREATE TABLE `properties` (
  `id` VARCHAR(32) NOT NULL,
  `application` VARCHAR(50) DEFAULT NULL,
  `profile` VARCHAR(50) DEFAULT NULL,
  `label` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

DROP TABLE `key_value`;
CREATE TABLE `key_value` (
  `id` VARCHAR(32) NOT NULL,
  `pid` VARCHAR(32) NOT NULL,
  `pkey` VARCHAR(50) DEFAULT NULL,
  `pvalue` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-gateway', 'dev', 'master', 'foo', 'Guiwang');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-gateway', 'dev', 'feat', 'info', 'feat-info');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-gateway', 'dev', 'feat', 'version', 'feat-info');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-gateway', 'test', 'master', 'foo', 'The king of fight');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-gateway', 'prod', 'master', 'foo', 'Prod');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-admin', 'dev', 'master', 'foo-admin', 'Admin: Guiwang');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-admin', 'dev', 'master', 'app.name', 'Administrator');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-admin', 'test', 'master', 'foo-admin', 'Admin: The king of fight');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'thraex-admin', 'prod', 'master', 'foo-admin', 'Admin: Prod');

