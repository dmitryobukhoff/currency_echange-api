insert into currency_exchanger.currencies (code, full_name, sign) values ('USD', 'US Dollar', '$');
insert into currency_exchanger.currencies (code, full_name, sign) values ('EUR', 'Euro', '€');
insert into currency_exchanger.currencies (code, full_name, sign) values ('RUB', 'Russian Ruble', '₽');
insert into currency_exchanger.currencies (code, full_name, sign) values ('UAH', 'Hryvnia', '₴');
insert into currency_exchanger.currencies (code, full_name, sign) values ('KZT', 'Tenge', '₸');
insert into currency_exchanger.currencies (code, full_name, sign) values ('GBP', 'Pound Sterling', '£');

insert into currency_exchanger.exchange_rates (base_currency_id, target_currency_id, rate) values (1, 2,0.94);
insert into currency_exchanger.exchange_rates (base_currency_id, target_currency_id, rate) values (1, 3, 63.75);
insert into currency_exchanger.exchange_rates (base_currency_id, target_currency_id, rate) values (1, 4, 36.95);
insert into currency_exchanger.exchange_rates (base_currency_id, target_currency_id, rate) values (1, 5, 469.88);
insert into currency_exchanger.exchange_rates (base_currency_id, target_currency_id, rate) values (1, 6, 0.81);