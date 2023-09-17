INSERT INTO role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO role (authority) VALUES ('ROLE_USER');

INSERT INTO users (first_name,last_name, age, user_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, role_id) VALUES ('admin','admin', 30,'admin@mail.ru', '$2a$12$IJUOxFKRTi2KuwwPrQOhculKT/bq5zJDHxt1RROUL5BLVS4ZaNyse',false,false,false,false, 1);
INSERT INTO users (first_name,last_name, age,user_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, role_id) VALUES ('user','user', 30,'user@mail.ru', '$2a$12$0pDazczhtN2L2REMfOdnV.WyuOzkaewvT4IM1WYeX8GyuSLNdQC.G', false,false,false,false, 2);

