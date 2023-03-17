create
    or replace function update_delivery() returns trigger as
$$
DECLARE
    container_using bool;
BEGIN
    case NEW.state
        when 'Picking-up'
            then RAISE EXCEPTION 'failed';
        when 'To-Export Transporting'
            then if OLD.state like 'Picking-up' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Export Checking'
            then if OLD.state like 'To-Export Transporting' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Export Check Fail'
            then if OLD.state like 'Export Checking' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Packing to Container'
            then if OLD.state like 'Export Checking' then
                return new;
                 elseif OLD.state like 'Packing to Container' then
                     select "using"
                     from container
                     where code like NEW.container_code
                     into container_using;
                     if container_using is false then
                         delete
                         from sea_transportation
                         where item_name like old.item_name;
                         update container
                         set "using" = true
                         where code like NEW.container_code;
                         insert into sea_transportation
                         values (new.item_name, new.container_code, null, new.company);
                         return new;
                     end if;
                 end if;
                 raise exception 'failed';
        when 'Waiting for Shipping'
            then if OLD.state like 'Packing to Container' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Shipping'
            then if OLD.state like 'Waiting for Shipping' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Unpacking from Container'
            then if OLD.state like 'Shipping' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Import Checking'
            then if OLD.state like 'Unpacking from Container' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Import Check Fail'
            then if OLD.state like 'Import Checking' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'From-Import Transporting'
            then if OLD.state like ('Import Checking' or 'From-Import Transporting') and
                    OLD.delivery_courier_id is null then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Delivering'
            then if OLD.state like 'From-Import Transporting' then
                return new;
            else
                raise exception 'failed';
            end if;
        when 'Finish'
            then if OLD.state like 'Delivering' then
                return new;
            else
                raise exception 'failed';
            end if;
        end case;
    return null;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '(%)', SQLERRM;
END ;
$$
    language plpgsql;

create
    or replace function update_ship() returns trigger as
$$
BEGIN
    if old.sailing is false and new.sailing is true
    then
        update delivery
        set state = 'Shipping'
        where ship_name like old.name
          and state like 'Waiting for Shipping';
        return new;
    end if;
    if old.sailing is true and new.sailing is false
    then
        return new;
    end if;
    RAISE EXCEPTION 'something wrong';
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION '(%)', SQLERRM;
END;
$$
    language plpgsql;

create
    or replace function update_sea_transportation() returns trigger as
$$
BEGIN
    if (old.ship_name is null and new.ship_name is not null) then
        update delivery
        set (state, ship_name) = ('Waiting for Shipping', new.ship_name)
        where item_name like new.item_name;
        return new;
    end if;
    return null;
END;
$$
    language plpgsql;

create
    or replace function delete_sea_transportation() returns trigger as
$$
DECLARE
    count integer;
BEGIN
    if old.ship_name is not null then
        update delivery
        set state = 'Unpacking from Container'
        where item_name like old.item_name;
        select count(*) from sea_transportation where ship_name like old.ship_name into count;
        if (count = 1) then
            update ship set sailing = false where name like old.ship_name;
        end if;
    end if;
    update container
    set "using" = false
    where code like old.container_code;
    return old;
END;
$$
    language plpgsql;

create or replace trigger update_delivery
    before update
    on delivery
    for each row
execute procedure update_delivery();

create or replace trigger update_ship
    before update
    on ship
    for each row
execute procedure update_ship();

create or replace trigger update_sea_transportation
    before update
    on sea_transportation
    for each row
execute procedure update_sea_transportation();

create or replace trigger delete_sea_transportation
    before delete
    on sea_transportation
    for each row
execute procedure delete_sea_transportation();
