DROP TABLE IF EXISTS `service_questions`;
CREATE TABLE `service_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar (255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `choices` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
