drop table Annexe if exists
drop table DEMANDE if exists
drop table DEMANDEUR if exists
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table Annexe (id bigint not null, typeAnnexe integer, primary key (id))
create table DEMANDE (DTYPE varchar(31) not null, id bigint not null, version integer not null, reference varchar(255), professionDeLaSante varchar(255), statutDemandeAutorisation varchar(255), demandeur_id bigint, primary key (id))
create table DEMANDEUR (id bigint not null, version integer not null, value varchar(255), primary key (id))
alter table DEMANDE add constraint FK9drwobmf0nnegsigr6s7ff1gd foreign key (demandeur_id) references DEMANDEUR
