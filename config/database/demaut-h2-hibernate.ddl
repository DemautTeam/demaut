drop table Annexe if exists
drop table DEMANDE if exists
drop table UTILISATEUR if exists
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table Annexe (id bigint not null, typeAnnexe varchar(255), annexes_id bigint, primary key (id))
create table DEMANDE (DTYPE varchar(31) not null, id bigint not null, version integer not null, login varchar(255), professionDeLaSante varchar(255), statutDemandeAutorisation varchar(255), primary key (id))
create table UTILISATEUR (id bigint not null, version integer not null, login varchar(255), primary key (id))
alter table Annexe add constraint FKlc42qm0c2k0a5cxtkbxg0ri1g foreign key (annexes_id) references DEMANDE
