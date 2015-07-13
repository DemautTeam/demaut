CREATE TABLE ANNEXE (
	id    int(11) PRIMARY KEY NOT NULL,
	name  varchar(255) NOT NULL,
	type  varchar(255) NOT NULL,
	size  int(11),
  file  blob NOT NULL
	);

CREATE INDEX annexe_pkey ON annexe (id);