drop table if exists raw_data_records cascade;
drop table if exists raw_data_staffs cascade;
drop table if exists city cascade;
drop table if exists city_tax cascade;
drop table if exists company cascade;
drop table if exists container cascade;
drop table if exists delivery cascade;
drop table if exists log cascade;
drop table if exists sea_transportation cascade;
drop table if exists ship cascade;
drop table if exists staff cascade ;
drop table if exists staff_type cascade;
drop table if exists state cascade;

-- copy raw_data from 'C:\Users\a\Desktop\data.csv' DELIMITER ',' CSV HEADER;

create table if not exists  raw_data_records
(
    "Item Name"         text,
    "Item Class"        text,
    "Item Price"        numeric(16,6),
    "Retrieval City"    text,
    "Retrieval Courier" text,
    "Delivery City"     text,
    "Delivery Courier"  text,
    "Export City"       text,
    "Import City"       text,
    "Export Tax"        numeric(16,6),
    "Import Tax"        numeric(16,6),
    "Export Officer"    text,
    "Import Officer"    text,
    "Container Code"    text,
    "Container Type"    text,
    "Ship Name"         text,
    "Company Name"      text,
    "Item State"        text
);

create table if not exists  raw_data_staffs
(
    "Name"     text,
    "Type"     text,
    "Company"  text,
    "City"     text,
    "Gender"   text,
    "Age"      integer,
    "Phone"    text,
    "Password" text
);

create table if not exists city
(
    name varchar primary key
);

create table if not exists city_tax
(
    city            varchar references city,
    item_type       varchar,
    import_tax_rate numeric(7,6),
    export_tax_rate numeric(7,6),
    primary key (city, item_type)
);

create table if not exists company
(
    name varchar primary key
);

create table if not exists container
(
    code    varchar primary key,
    type    varchar not null,
    "using" bool    not null
);

create table if not exists ship
(
    name    varchar unique,
    company varchar references company,
    sailing bool not null,
    primary key (name, company)
);



create table if not exists staff_type
(
    type varchar primary key
);

create table if not exists staff
(
    id           SERIAL primary key,
    company      varchar references company,
    city         varchar references city,
    name         varchar  not null,
    type         varchar  not null references staff_type,
    gender       varchar  not null
        constraint gender_check
            check (gender like 'male' OR gender like 'female'),
    birthday     date,
    phone_number char(11) not null,
    unique (company, city, name, type, gender, birthday, phone_number)
);

create table if not exists log
(
    staff_id integer primary key references staff,
    password varchar not null
);

create table if not exists state
(
    name varchar primary key
);

create table if not exists delivery
(
    item_name            varchar primary key,
    item_type            varchar        not null,
    item_price           numeric(16,6) not null,
    retrieval_city       varchar        not null references city,
    retrieval_courier_id integer        not null references staff,
    export_city          varchar        not null,
    foreign key (export_city, item_type) references city_tax,
    export_officer_id    integer references staff,
    import_city          varchar        not null,
    foreign key (import_city, item_type) references city_tax,
    import_officer_id    integer references staff,
    delivery_city        varchar        not null references city,
    delivery_courier_id  integer references staff,
    container_code       varchar references container,
    ship_name            varchar,
    foreign key (ship_name, company) references ship,
    company              varchar        not null references company,
    state                varchar        not null references state
);

create table if not exists sea_transportation
(
    item_name      varchar primary key references delivery,
    container_code varchar not null references container,
    ship_name      varchar,
    company        varchar not null references company,
    foreign key (ship_name, company) references ship
);