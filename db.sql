CREATE TABLE resume (
    uuid CHAR(36) PRIMARY KEY NOT NULL,
    full_name TEXT NOT NULL,
    location TEXT,
    home_page TEXT
);

CREATE TABLE contact (
  id SERIAL,
  resume_uuid CHAR(36) NOT NULL,
  type TEXT NOT NULL,
  value TEXT NOT NULL,
  CONSTRAINT contant_pkey PRIMARY KEY(id),
  CONSTRAINT contact_fk FOREIGN KEY (resume_uuid)
  REFERENCES resume(uuid)
  ON DELETE CASCADE
  ON UPDATE NO ACTION
  NOT DEFERRABLE
);

CREATE UNIQUE INDEX contact_idx ON contact
USING btree (resume_uuid, type);


