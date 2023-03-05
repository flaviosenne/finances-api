insert into custom_user (id, email, is_Active, first_name, last_name, password)
values (uuid(),'flaviosenne123@gmail.com', true, 'joao', 'senne', '123');

insert into bank(id, description, user_id)
values
(uuid(),'Santander', (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(),'Nubank', (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(),'Neon', (SELECT id from custom_user WHERE first_name = 'joao') );


insert into category(id, description, user_id)
values
(uuid(),'Comida', (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(),'Bobeira', (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(),'Teste', (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(),'Bem estar', (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(),'Salario', (SELECT id from custom_user WHERE first_name = 'joao') );


insert into custom_release (id, description, status_release, type_release, category_id, bank_id, value, user_id)
values
(uuid(), 'salario bart', 'PAID', 'RECEP', (SELECT id from category WHERE description = 'Salario'),  (SELECT id from bank WHERE description = 'Nubank'), 4573.21 , (SELECT id from custom_user WHERE first_name = 'joao')),
(uuid(), 'hotdog', 'PAID', 'EXPENSE', (SELECT id from category WHERE description = 'Bobeira'),  (SELECT id from bank WHERE description = 'Nubank'), 32, (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(), 'cortar cabelo', 'PAID', 'EXPENSE', (SELECT id from category WHERE description = 'Bem estar'),  (SELECT id from bank WHERE description = 'Nubank'), 29, (SELECT id from custom_user WHERE first_name = 'joao') ),
(uuid(), 'comida', 'PAID', 'EXPENSE', (SELECT id from category WHERE description = 'Comida'),  (SELECT id from bank WHERE description = 'Nubank'), 200, (SELECT id from custom_user WHERE first_name = 'joao') );