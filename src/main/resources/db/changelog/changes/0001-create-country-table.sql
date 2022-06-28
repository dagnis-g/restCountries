--liquibase formatted sql

--changeset dagnis:1

create table country(
    country_id VARCHAR PRIMARY KEY,
    name VARCHAR,
    capital VARCHAR,


)