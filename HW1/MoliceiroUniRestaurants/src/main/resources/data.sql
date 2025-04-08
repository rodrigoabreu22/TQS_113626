DELETE FROM reservation;
DELETE FROM meal;
DELETE FROM restaurant;

ALTER TABLE restaurant AUTO_INCREMENT = 1;
ALTER TABLE meal AUTO_INCREMENT = 1;

-- Insert restaurants (IDs will be auto-generated)
INSERT INTO restaurant (name, weather_id) VALUES
('Moliceiro Seafood', 1010500),  -- Will get ID 1
('University Grill', 1010500),    -- Will get ID 2
('Dona Maria', 1110600),          -- Will get ID 3
('BurgerUA', 1010500),            -- Will get ID 4
('Sabores do Mondego', 1060300);  -- Will get ID 5

-- Now insert meals using the correct foreign key column name
-- Moliceiro Seafood (ID 1)
INSERT INTO meal (name, type, reservation_limit, date, restaurant) VALUES
('Arroz de Marisco', 'lunch', 40, '2025-04-09', 1),
('Polvo à Lagareiro', 'dinner', 35, '2025-04-09', 1),
('Caldeirada de Peixe', 'lunch', 40, '2025-04-10', 1),
('Bacalhau com Natas', 'dinner', 35, '2025-04-10', 1),
('Bifana', 'lunch', 45, '2025-04-11', 1),
('Lombo de Porco Assado', 'dinner', 40, '2025-04-11', 1),
('Pataniscas de Bacalhau', 'lunch', 45, '2025-04-12', 1),
('Rojões à Minhota', 'dinner', 40, '2025-04-12', 1),
('Migas com Entrecosto', 'lunch', 30, '2025-04-13', 1),
('Ensopado de Borrego', 'dinner', 25, '2025-04-13', 1),
('Gaspacho Alentejano', 'lunch', 30, '2025-04-14', 1),
('Carne de Porco Preto', 'dinner', 25, '2025-04-14', 1),
('Francesinha', 'lunch', 50, '2025-04-15', 1),
('Prego no Pão', 'dinner', 45, '2025-04-15', 1),
('Cachorro Especial', 'lunch', 50, '2025-04-16', 1),
('Hambúrguer Artesanal', 'dinner', 45, '2025-04-16', 1);

-- University Grill (ID 2)
INSERT INTO meal (name, type, reservation_limit, date, restaurant) VALUES
('Bifana', 'lunch', 45, '2025-04-09', 2),
('Lombo de Porco Assado', 'dinner', 40, '2025-04-09', 2),
('Pataniscas de Bacalhau', 'lunch', 45, '2025-04-10', 2),
('Rojões à Minhota', 'dinner', 40, '2025-04-10', 2),
('Chanfana', 'lunch', 35, '2025-04-11', 2),
('Arroz de Lampreia', 'dinner', 30, '2025-04-11', 2),
('Cabrito Assado', 'lunch', 35, '2025-04-12', 2),
('Bacalhau à Lagareiro', 'dinner', 30, '2025-04-12', 2),
('Migas com Entrecosto', 'lunch', 30, '2025-04-13', 2),
('Ensopado de Borrego', 'dinner', 25, '2025-04-13', 2),
('Gaspacho Alentejano', 'lunch', 30, '2025-04-14', 2),
('Carne de Porco Preto', 'dinner', 25, '2025-04-14', 2),
('Arroz de Marisco', 'lunch', 40, '2025-04-15', 2),
('Polvo à Lagareiro', 'dinner', 35, '2025-04-15', 2),
('Caldeirada de Peixe', 'lunch', 40, '2025-04-16', 2),
('Bacalhau com Natas', 'dinner', 35, '2025-04-16', 2);

-- Dona Maria (ID 3)
INSERT INTO meal (name, type, reservation_limit, date, restaurant) VALUES
('Migas com Entrecosto', 'lunch', 30, '2025-04-09', 3),
('Ensopado de Borrego', 'dinner', 25, '2025-04-09', 3),
('Gaspacho Alentejano', 'lunch', 30, '2025-04-10', 3),
('Carne de Porco Preto', 'dinner', 25, '2025-04-10', 3),
('Bifana', 'lunch', 45, '2025-04-11', 3),
('Lombo de Porco Assado', 'dinner', 40, '2025-04-11', 3),
('Pataniscas de Bacalhau', 'lunch', 45, '2025-04-12', 3),
('Rojões à Minhota', 'dinner', 40, '2025-04-12', 3),
('Chanfana', 'lunch', 35, '2025-04-13', 3),
('Arroz de Lampreia', 'dinner', 30, '2025-04-13', 3),
('Cabrito Assado', 'lunch', 35, '2025-04-14', 3),
('Bacalhau à Lagareiro', 'dinner', 30, '2025-04-14', 3),
('Francesinha', 'lunch', 50, '2025-04-15', 3),
('Prego no Pão', 'dinner', 45, '2025-04-15', 3),
('Cachorro Especial', 'lunch', 50, '2025-04-16', 3),
('Hambúrguer Artesanal', 'dinner', 45, '2025-04-16', 3);

-- BurgerUA (ID 4)
INSERT INTO meal (name, type, reservation_limit, date, restaurant) VALUES
('Francesinha', 'lunch', 50, '2025-04-09', 4),
('Prego no Pão', 'dinner', 45, '2025-04-09', 4),
('Cachorro Especial', 'lunch', 50, '2025-04-10', 4),
('Hambúrguer Artesanal', 'dinner', 45, '2025-04-10', 4),
('Arroz de Marisco', 'lunch', 40, '2025-04-11', 4),
('Polvo à Lagareiro', 'dinner', 35, '2025-04-11', 4),
('Caldeirada de Peixe', 'lunch', 40, '2025-04-12', 4),
('Bacalhau com Natas', 'dinner', 35, '2025-04-12', 4),
('Migas com Entrecosto', 'lunch', 30, '2025-04-13', 4),
('Ensopado de Borrego', 'dinner', 25, '2025-04-13', 4),
('Gaspacho Alentejano', 'lunch', 30, '2025-04-14', 4),
('Carne de Porco Preto', 'dinner', 25, '2025-04-14', 4),
('Bifana', 'lunch', 45, '2025-04-15', 4),
('Lombo de Porco Assado', 'dinner', 40, '2025-04-15', 4),
('Pataniscas de Bacalhau', 'lunch', 45, '2025-04-16', 4),
('Rojões à Minhota', 'dinner', 40, '2025-04-16', 4);

-- Sabores do Mondego (ID 5)
INSERT INTO meal (name, type, reservation_limit, date, restaurant) VALUES
('Chanfana', 'lunch', 35, '2025-04-09', 5),
('Arroz de Lampreia', 'dinner', 30, '2025-04-09', 5),
('Cabrito Assado', 'lunch', 35, '2025-04-10', 5),
('Bacalhau à Lagareiro', 'dinner', 30, '2025-04-10', 5),
('Francesinha', 'lunch', 50, '2025-04-11', 5),
('Prego no Pão', 'dinner', 45, '2025-04-11', 5),
('Cachorro Especial', 'lunch', 50, '2025-04-12', 5),
('Hambúrguer Artesanal', 'dinner', 45, '2025-04-12', 5),
('Bifana', 'lunch', 45, '2025-04-13', 5),
('Lombo de Porco Assado', 'dinner', 40, '2025-04-13', 5),
('Pataniscas de Bacalhau', 'lunch', 45, '2025-04-14', 5),
('Rojões à Minhota', 'dinner', 40, '2025-04-14', 5),
('Arroz de Marisco', 'lunch', 40, '2025-04-15', 5),
('Polvo à Lagareiro', 'dinner', 35, '2025-04-15', 5),
('Caldeirada de Peixe', 'lunch', 40, '2025-04-16', 5),
('Bacalhau com Natas', 'dinner', 35, '2025-04-16', 5);


