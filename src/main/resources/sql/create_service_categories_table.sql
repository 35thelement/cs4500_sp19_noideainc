DROP TABLE IF EXISTS service_categories;
CREATE TABLE service_categories (
  id int(11) NOT NULL AUTO_INCREMENT,
  service_category_name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;