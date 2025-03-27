CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name varchar(40) not null,
    last_name varchar(40) not null,
    salary decimal(10,2) not null,
    role varchar(30) not null
);


INSERT INTO employee (first_name,last_name,salary,role) VALUES ('Lebron', 'James', 1000000, 'player');
INSERT INTO employee (first_name,last_name,salary,role) VALUES ('Steph', 'Curry', 700000, 'player');
INSERT INTO employee (first_name,last_name,salary,role) VALUES ('Steve', 'Kerr', 30000, 'coach');
INSERT INTO employee (first_name,last_name,salary,role) VALUES ('Neemias', 'Queta', 500000, 'player');
INSERT INTO employee (first_name,last_name,salary,role) VALUES ('Luka', 'Doncic', 400000, 'player');