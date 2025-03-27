CREATE TABLE car (
    car_id SERIAL PRIMARY KEY,
    maker varchar(40) not null,
    model varchar(40) not null
);


INSERT INTO car (maker, model) VALUES ('Toyota','Corolla');
INSERT INTO car (maker, model) VALUES ('Toyota','Supra');
INSERT INTO car (maker, model) VALUES ('Porsche','Turbo');
INSERT INTO car (maker, model) VALUES ('Renault','Clio');
INSERT INTO car (maker, model) VALUES ('Seat','Ibiza');