CREATE SCHEMA GIFT_CERTIFICATES;

CREATE TABLE GIFT_CERTIFICATES.certificates (
  id_cert bigint NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  description tinytext,
  price double NOT NULL,
  create_date varchar(45) NOT NULL,
  last_update_date varchar(45) NOT NULL,
  duration int NOT NULL,
  PRIMARY KEY (id_cert),
  UNIQUE KEY id_cert_UNIQUE (id_cert)
  );

CREATE TABLE GIFT_CERTIFICATES.tags (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY Name_UNIQUE (name),
  UNIQUE KEY id_UNIQUE (id)
);

CREATE TABLE GIFT_CERTIFICATES.tagged_certificates (
  certificate_id bigint NOT NULL,
  tag_id int NOT NULL,
  PRIMARY KEY (certificate_id,tag_id),
  CONSTRAINT fk_certificate_id FOREIGN KEY (certificate_id) REFERENCES certificates (id_cert) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE ON UPDATE CASCADE
);

