DROP TABLE IF EXISTS `service_questions`;
CREATE TABLE `service_questions` (
  id int(11) NOT NULL AUTO_INCREMENT,
  true_false_answer varchar(255) DEFAULT NULL,
  max_range varchar(255) DEFAULT NULL,
  min_range varchar(255) DEFAULT NULL,
  choice varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
