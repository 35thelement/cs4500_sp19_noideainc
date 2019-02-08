CREATE TABLE `frequently_asked_answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `frequently_asked_question_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32wb3j2g6x9vj508pqtoccalt` (`frequently_asked_question_id`),
  KEY `FKigw804r5kgchga0avjw23q9ik` (`user_id`),
  CONSTRAINT `FK32wb3j2g6x9vj508pqtoccalt` FOREIGN KEY (`frequently_asked_question_id`) REFERENCES `frequently_asked_questions` (`id`),
  CONSTRAINT `FKigw804r5kgchga0avjw23q9ik` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
