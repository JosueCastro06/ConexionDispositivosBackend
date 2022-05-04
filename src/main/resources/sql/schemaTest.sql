

CREATE TABLE IF NOT EXISTS `connection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `encryption` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nameco` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `device` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `connected` bit(1) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `mac` varchar(255) DEFAULT NULL,
  `trademark` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `connection_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKppy0s2dmp117hagbpbj1cv52n` (`connection_id`),
  CONSTRAINT `FKppy0s2dmp117hagbpbj1cv52n` FOREIGN KEY (`connection_id`) REFERENCES `connection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


CREATE TABLE IF NOT EXISTS `historic` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `encryption` varchar(255) DEFAULT NULL,
  `id_connection` bigint DEFAULT NULL,
  `id_device` bigint DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `mac` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type_connection` int DEFAULT NULL,
  `type_device` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
