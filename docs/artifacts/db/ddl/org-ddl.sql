
CREATE TABLE org_user (
  user_id varchar(100) NOT NULL,
  password varchar(100)  NOT NULL,
  email varchar(100) NOT NULL,
  name varchar(100) NOT NULL
);

ALTER TABLE org_user ADD CONSTRAINT pk_org_user PRIMARY KEY(user_id);






