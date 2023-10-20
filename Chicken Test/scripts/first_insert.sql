
INSERT INTO farms (farm_name) VALUES ('krazychikz');
           
INSERT INTO users (first_name, last_name, username, password, email, user_observations, active, farm_id) 
           VALUES ('Anakin', 'Skywalker', 'lordvader', '{bcrypt}$2a$10$ZPryzkJKUs0Dpls6WqfvP.strIzxUCSpne2ScVuMQF/7rZ27AHj6m', 'vader@deathstar.org', 'pw: iamyourfather', 1, 1);
INSERT INTO authorities (username, authority) VALUES ('lordvader', 'EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('lordvader', 'MANAGER');
INSERT INTO authorities (username, authority) VALUES ('lordvader', 'ADMIN');
/*encrypted password is: "iamyourfather" */

INSERT INTO users (first_name, last_name, username, password, email, user_observations, active, farm_id) 
           VALUES ('Michael', 'Scott', 'mcmike', '{bcrypt}$2a$10$RBOTBZ44JlpFoaTdVgOon.MEuavQpEnvdg9c6P2JoRVtuYdVvFq6G', 'mscott@dundermifflin.com', 'pw: worldsbestboss', 1, 1);
INSERT INTO authorities (username, authority) VALUES ('mcmike', 'EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('mcmike', 'MANAGER');
/*encrypted password is: "worldsbestboss" */

INSERT INTO users (first_name, last_name, username, password, email, user_observations, active, farm_id) 
           VALUES ('Jacob', 'Peralta', 'jakeperalta', '{bcrypt}$2a$10$6StXj1V/MdcrXVLO1UJUVuR4fjKnygcPgqjagXr8niE9vsPXEfaZ6', 'awesomejake@hotmail.com', 'pw: coolcoolcool', 1, 1);
INSERT INTO authorities (username, authority) VALUES ('jakeperalta', 'EMPLOYEE');
/*encrypted password is: "coolcoolcool" */

INSERT INTO products (product_id, product_name, product_value, product_stock, farm_id) VALUES (1, 'Egg', 2.05, 8, 1);
INSERT INTO products (product_id, product_name, product_value, product_stock, farm_id) VALUES (2, 'Chicken', 12.50, 6, 1);

INSERT INTO eggs (egg_birthday, product_id, farm_id)
VALUES 
(STR_TO_DATE('01/01/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('02/02/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('03/03/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('04/04/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('05/05/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('06/06/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('20/09/2023', '%d/%m/%Y'), 1, 1),
(STR_TO_DATE('01/10/2023', '%d/%m/%Y'), 1, 1);

INSERT INTO chickens (chicken_birthday, product_id, farm_id)
VALUES 
(STR_TO_DATE('01/01/2022', '%d/%m/%Y'), 2, 1),
(STR_TO_DATE('02/02/2022', '%d/%m/%Y'), 2, 1),
(STR_TO_DATE('03/03/2022', '%d/%m/%Y'), 2, 1),
(STR_TO_DATE('04/04/2022', '%d/%m/%Y'), 2, 1),
(STR_TO_DATE('05/05/2022', '%d/%m/%Y'), 2, 1),
(STR_TO_DATE('06/06/2022', '%d/%m/%Y'), 2, 1);

INSERT INTO parameters (parameter_id, parameter_name, parameter_value, farm_id) VALUES (1, 'Egg capacity', 36000, 1);
INSERT INTO parameters (parameter_id, parameter_name, parameter_value, farm_id) VALUES (2, 'Chicken capacity', 200, 1);

COMMIT;