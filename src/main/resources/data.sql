INSERT INTO category (id,name) VALUES (1,'shirt');
INSERT INTO category (id,name) VALUES (2,'cup');
INSERT INTO category (id,name) VALUES (3,'comics');
INSERT INTO category (id,name) VALUES (4,'toy');

INSERT INTO product (id, name, reference, category_id, status, stock, date_created, date_updated)
VALUES (1, 'hulk shirt', 'HS1', 1, 1, 10, '2022-01-01', NOW());
INSERT INTO product (id, name, reference, category_id, status, stock, date_created, date_updated)
VALUES (2, 'cup DC', 'CP1', 2, 1, 20, '2022-01-01', NOW());
INSERT INTO product (id, name, reference, category_id, status, stock, date_created, date_updated)
VALUES (3, 'Marvel Comic 01', 'CM1', 3, 1, 30, '2022-01-01', NOW());
INSERT INTO product (id, name, reference, category_id, status, stock, date_created, date_updated)
VALUES (4, 'Marvel shirt', 'MS1', 1, 0, 0, '2022-01-01', NOW());

INSERT INTO user (id, name, lastname, email, username, password, role, date_created)
VALUES (1, 'Daniel', 'Duarte', 'xxxxx@xxxxx.com', 'admin', '$2a$10$Dkrn6l/AvsM8.Xd7HrgOPOXcoTnKhrOShu.u2JABEynO4NT1u7Oje','ROLE_ADMIN', NOW());
INSERT INTO user (id, name, lastname, email, username, password, role, date_created)
VALUES (2, 'customer', 'customer', 'xxxxx@xxxxx.com', 'user', '$2a$10$Z.W6l19oWcLs3VY6P7cQue8CHopHqdTuiZ5ixfSVUCaCDX1jr3Azi','ROLE_USER', NOW());
