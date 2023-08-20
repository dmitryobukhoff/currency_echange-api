create schema if not exists currency_exchanger;

create table if not exists currency_exchanger.currencies(
    id serial primary key,
    code varchar(50) unique not null,
    full_name varchar(50) not null,
    sign varchar(50) not null
);

create table if not exists currency_exchanger.exchange_rates(
    id serial primary key,
    base_currency_id bigint references currency_exchanger.currencies(id),
    target_currency_id bigint references currency_exchanger.currencies(id),
    rate decimal(6)
);