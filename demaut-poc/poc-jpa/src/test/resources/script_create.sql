CREATE TABLE ANNEXE (
	ID    int(11) PRIMARY KEY NOT NULL,
	NAME  varchar(50) NOT NULL,
	TYPE  varchar(100) NOT NULL,
	SIZE  int(11),
  FILE  blob NOT NULL
	);

CREATE INDEX annexe_pkey ON annexe (id);