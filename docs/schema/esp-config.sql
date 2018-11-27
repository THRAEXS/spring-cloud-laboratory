create database `riped-config`;

USE `riped-config`;
DROP TABLE `properties`;
CREATE TABLE `properties` (
  `id` VARCHAR(32) NOT NULL,
  `application` VARCHAR(50) DEFAULT NULL,
  `profile` VARCHAR(50) DEFAULT NULL,
  `label` VARCHAR(50) DEFAULT NULL,
  `key` VARCHAR(50) DEFAULT NULL,
  `value` VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;

-- list.add(new Properties("esp-gateway", "dev", "master", "foo", "Val-"));
--         list.add(new Properties("esp-gateway", "dev", "feat", "info", "Val-"));
--         list.add(new Properties("esp-gateway", "dev", "feat", "version", "Val-"));
--         list.add(new Properties("esp-gateway", "test", "master", "foo", "Val-"));
--         list.add(new Properties("esp-gateway", "prod", "master", "foo", "Val-"));
--         list.add(new Properties("esp-admin", "dev", "master", "foo-admin", "Val-"));
--         list.add(new Properties("esp-admin", "dev", "master", "app.name", "Val-"));
--         list.add(new Properties("esp-admin", "test", "master", "foo-admin", "Val-"));
--         list.add(new Properties("esp-admin", "prod", "master", "foo-admin", "Val-"));

INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-gateway', 'dev', 'master', 'foo', 'Guiwang');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-gateway', 'dev', 'feat', 'info', 'feat-info');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-gateway', 'dev', 'feat', 'version', 'feat-info');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-gateway', 'test', 'master', 'foo', 'The king of fight');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-gateway', 'prod', 'master', 'foo', 'Prod');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-admin', 'dev', 'master', 'foo-admin', 'Admin: Guiwang');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-admin', 'dev', 'master', 'app.name', 'Administrator');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-admin', 'test', 'master', 'foo-admin', 'Admin: The king of fight');
INSERT INTO properties(id, application, profile, label, `key`, `value`) VALUES (REPLACE(UUID(), '-', ''), 'esp-admin', 'prod', 'master', 'foo-admin', 'Admin: Prod');

