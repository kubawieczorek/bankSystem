INSERT INTO role (roleName) VALUES ('ROLE_USER');
INSERT INTO role (roleName) VALUES ('ROLE_ADMIN');
INSERT INTO client (name, password, email) VALUES ('admin', 'admin1', 'admin@admin.pl');
INSERT INTO client (name, password, email) VALUES ('JanKowalski', 'kowalski1', 'jkowalski@wp.pl');
INSERT INTO client (name, password, email) VALUES ('TomaszTomczykiewicz', 'tom123', 'tomcz@wp.pl');
INSERT INTO account (money, clientId, dateCreated, type, number)
VALUES(1500, SELECT clientId FROM client WHERE name = 'JanKowalski', '20120605', 'Savings Account', '100');
INSERT INTO account (money, clientId, dateCreated, type, number)
VALUES(750, SELECT clientId FROM client WHERE name = 'JanKowalski', '20160810', 'Investment Account', '101');
INSERT INTO account (money, clientId, dateCreated, type, number)
VALUES(750, SELECT clientId FROM client WHERE name = 'TomaszTomczykiewicz', '20170111', 'Savings Account', '200');
