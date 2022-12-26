-- truncate record cascade;
truncate city cascade;
truncate city_tax cascade;
truncate company cascade;
truncate container cascade;
truncate ship cascade;
truncate state cascade;
TRUNCATE staff RESTART IDENTITY CASCADE;
truncate delivery cascade;
truncate staff_type cascade;
truncate log cascade;

WITH t as ((select DISTINCT "Retrieval City" as name, substr("Retrieval Courier Phone Number", 1, 4) as code
            from raw_data)
           UNION
           (select DISTINCT "Delivery City" as name, substr("Delivery Courier Phone Number", 1, 4) as code
            from raw_data)
           UNION
           (select DISTINCT "Item Import City" as name, null as code
            from raw_data)
           UNION
           (select DISTINCT "Item Export City" as name, null as code
            from raw_data)),
     t1 as (select name, max(cast(code as integer)) as max from t group by name)
insert
into city(name, phone_code)
select name, code
from t
         natural join t1
where cast(code as integer) = max
   OR max is null
on conflict (name)
    DO UPDATE SET phone_code = excluded.phone_code;

insert into tax_rate(city, item_type, export_tax_rate)
select DISTINCT "Item Export City", "Item Type", cast("Item Export Tax" / "Item Price" as numeric(12, 2))
from raw_data
where "Item Export City" is not null
  AND "Item Type" is not null
ON CONFLICT (city,item_type) DO UPDATE SET export_tax_rate = excluded.export_tax_rate;
insert into tax_rate(city, item_type, import_tax_rate)
select DISTINCT "Item Import City", "Item Type", cast("Item Import Tax" / "Item Price" as numeric(12, 2))
from raw_data
where "Item Import City" is not null
  AND "Item Type" is not null
ON CONFLICT (city,item_type) DO UPDATE SET import_tax_rate = excluded.import_tax_rate;

insert
into company(name)
select DISTINCT "Company Name"
from raw_data
on conflict do nothing;

insert
into container(code, type)
select DISTINCT "Container Code", "Container Type"
from raw_data
where "Container Code" is not null
on conflict do nothing;

insert
into ship(name, company)
select DISTINCT "Ship Name", "Company Name"
from raw_data
where "Ship Name" is not null
on conflict do nothing;

insert
into courier(name, company, gender, birth_year, phone_number, city)
select DISTINCT "Retrieval Courier",
                "Company Name",
                "Retrieval Courier Gender",
                to_date((to_char(cast((EXTRACT(YEAR from "Retrieval Start Time")) as integer) - "Retrieval Courier Age",
                                 '9999')), 'YYYY'),
                substr("Retrieval Courier Phone Number", 6),
                "Item Export City"
from raw_data
where "Retrieval Courier" is not null
on conflict do nothing;
insert
into courier(name, company, gender, birth_year, phone_number, city)
select DISTINCT "Delivery Courier",
                "Company Name",
                "Delivery Courier Gender",
                to_date((to_char(
                            cast((EXTRACT(YEAR from "Delivery Finished Time")) as integer) - "Delivery Courier Age",
                            '9999')), 'YYYY'),
                substr("Delivery Courier Phone Number", 6),
                "Item Import City"
from raw_data
where "Delivery Courier" is not null
on conflict do nothing;

alter table raw_data
    add column if not exists retrieval_courier_id integer;
alter table raw_data
    add column if not exists delivery_courier_id integer;

-- Linux: 56s
update raw_data
set retrieval_courier_id=courier.id
from courier
where raw_data."Retrieval Courier" like courier.name
  AND raw_data."Company Name" like courier.company
  AND raw_data."Retrieval Courier Gender" like courier.gender
  AND raw_data."Retrieval Courier Age" = cast(extract(year from age("Retrieval Start Time"
    , birth_year)) as integer)
  AND substr(raw_data."Retrieval Courier Phone Number"
          , 6) like courier.phone_number
  AND raw_data."Item Export City" like courier.city
;
--
update raw_data
set delivery_courier_id=courier.id
from courier
where raw_data."Delivery Courier" like courier.name
  AND raw_data."Company Name" like courier.company
  AND raw_data."Delivery Courier Gender" like courier.gender
  AND raw_data."Delivery Courier Age" = cast(extract(year from age("Delivery Finished Time"
    , birth_year)) as integer)
  AND substr(raw_data."Delivery Courier Phone Number"
          , 6) like courier.phone_number
  AND raw_data."Item Import City" like courier.city
;

-- 53s, 4s
insert
into delivery(item_name, item_type, item_price,
              retrieval_start_time, retrieval_city, retrieval_courier_id,
              export_time, export_city,
              import_time, import_city,
              delivery_finish_time, delivery_city, delivery_courier_id,
              container_code, ship_name, company, log_time)
select "Item Name",
       "Item Type",
       "Item Price",
       "Retrieval Start Time",
       "Retrieval City",
       retrieval_courier_id,
       "Item Export Time",
       "Item Export City",
       "Item Import Time",
       "Item Import City",
       "Delivery Finished Time",
       "Delivery City",
       delivery_courier_id,
       "Container Code",
       "Ship Name",
       "Company Name",
       "Log Time"
from raw_data
on conflict do nothing;

-- the lastest three
-- prestmt 56s,107s,154s
-- stmt 49s,977s,144s

select * from city_rate where city = ? and item_type = ?;

