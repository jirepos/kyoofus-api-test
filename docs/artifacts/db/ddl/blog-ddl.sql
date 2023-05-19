
CREATE TABLE blog (
  blog_id varchar(100) NOT NULL,
  blog_name varchar(100)  NOT NULL,
  user_id varchar(100) NOT NULL
);

ALTER TABLE blog ADD CONSTRAINT pk_blog PRIMARY KEY(blog_id);

CREATE TABLE blog_tag (
  tag_id varchar(100) NOT NULL,
  tag_name varchar(100)  NOT NULL,
  blog_id varchar(100) NOT NULL
);

ALTER TABLE blog_tag ADD CONSTRAINT pk_blog_tag PRIMARY KEY(tag_id);

CREATE TABLE blog_post (
  post_id varchar(100) NOT NULL,
  blog_id varchar(100) NOT NULL,
  post_title varchar(300)  NOT NULL,
  file_name varchar(100) NOT NULL,
  file_ext varchar(20) NOT NULL,
  tag_names varchar(3000) NOT NULL,
  reg_dt timestamp ,
  upd_dt timestamp ,
  del_yn char(1)
);

ALTER TABLE blog_post ADD CONSTRAINT pk_blog_post PRIMARY KEY(post_id);

CREATE TABLE blog_tag_post (
  tag_id varchar(100) NOT NULL,
  post_id varchar(100) NOT NULL,
  blog_id varchar(100) NOT NULL
);

ALTER TABLE blog_tag_post ADD CONSTRAINT pk_blog_tag_post PRIMARY KEY(tag_id, post_id);
CREATE INDEX blog_post_ix01 ON blog_post ( reg_dt desc, post_id);


