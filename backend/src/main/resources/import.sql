INSERT INTO tb_category (name, created_at) VALUES ('Elf', NOW());
INSERT INTO tb_category (name, created_at) VALUES ('Dwarf', NOW());
INSERT INTO tb_category (name, created_at) VALUES ('Hobbit', NOW());
INSERT INTO tb_category (name, created_at) VALUES ('Maias', NOW());
INSERT INTO tb_category (name, created_at) VALUES ('Orc', NOW());
INSERT INTO tb_category (name, created_at) VALUES ('Gobblin', NOW());

INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Arco de Legolas', 'Réplica do arco utilizado pelo Príncipe Legolas da Floresta Verde. Perfeito para arqueiros de elite.', 250.00, 'legolas_bow.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Manto de Lórien', 'Um manto leve e camuflado, feito com a perícia dos Elfos de Lórien. Ideal para viajantes e aventureiros.', 120.00, 'lorien_cloak.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Espada Sting (Ferroada)', 'A adaga élfica que brilha na presença de Orcs. Uma peça essencial para qualquer aventureiro.', 180.00, 'sting_sword.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Lembas (Pão Élfico)', 'O pão de viagem dos Elfos, que sustenta um viajante por um dia inteiro. Leve e nutritivo.', 15.00, 'lembas_bread.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Flauta de Rivendell', 'Uma flauta artesanal inspirada nas melodias élficas de Valfenda.', 80.00, 'rivendell_flute.jpg', NOW());

INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Machado de Gimli', 'Um machado de batalha robusto, forjado nas montanhas como os utilizados pelos Anões de Erebor.', 200.00, 'gimli_axe.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Capacete de Moria', 'Capacete de batalha resistente, inspirado nas armaduras dos Anões de Khazad-dûm.', 90.00, 'moria_helmet.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Caneca de Cerveja Anã', 'Uma caneca rústica e robusta, perfeita para suas bebidas favoritas, como as apreciadas pelos Anões.', 40.00, 'dwarf_mug.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Picareta de Minas Anãs', 'Uma picareta resistente, ideal para escavações e mineração, como as ferramentas dos Anões.', 75.00, 'dwarf_pickaxe.jpg', NOW());

INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Cachimbo de Bolseiro', 'Um cachimbo confortável, perfeito para desfrutar de um bom tabaco, como Bilbo e Frodo.', 60.00, 'hobbit_pipe.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Capa de Viajante do Condado', 'Uma capa simples e durável, ideal para caminhadas leves pelo Condado.', 70.00, 'shire_cloak.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Mapas do Condado', 'Mapas detalhados das trilhas e vilarejos do Condado, perfeito para Hobbits aventureiros.', 30.00, 'shire_map.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Geleia de Amoras de Samwise', 'Geleia caseira feita com amoras frescas, inspirada nas delícias culinárias do Samwise.', 25.00, 'sam_jam.jpg', NOW());

INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Cajado de Gandalf', 'Uma réplica do cajado do Mago Branco, com detalhes intrincados.', 300.00, 'gandalf_staff.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Um Anel (Réplica)', 'A joia mais poderosa da Terra Média, em uma réplica fiel. Atenção: Não concede poderes de invisibilidade.', 150.00, 'one_ring.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Palantír (Orbe Vidente)', 'Uma réplica da pedra de comunicação utilizada pelos Reis de Gondor.', 220.00, 'palantir.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Amuleto da Estrela da Noite (Arwen)', 'Colar deslumbrante, réplica do Evenstar de Arwen.', 110.00, 'evenstar_amulet.jpg', NOW());

INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Espada Orc de Mordor', 'Uma espada rústica e bruta, como as utilizadas pelos Orcs de Mordor.', 100.00, 'orc_sword.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Escudo de Uruk-Hai', 'Um escudo robusto e resistente, com o símbolo da Mão Branca de Saruman.', 85.00, 'uruk_hai_shield.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Máscara de Goblins das Montanhas', 'Uma máscara assustadora, inspirada nos Goblins das Montanhas da Névoa.', 50.00, 'goblin_mask.jpg', NOW());

INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Adaga Goblin', 'Uma adaga pequena e serrilhada, comum entre os Goblins das Minas da Névoa.', 60.00, 'goblin_dagger.jpg', NOW());
INSERT INTO tb_product (name, description, price, img_url, created_at) VALUES ('Armadilha Goblin', 'Um conjunto de pequenas armadilhas, inspiradas nas táticas sorrateiras dos Goblins.', 45.00, 'goblin_trap.jpg', NOW());

-- Associação dos produtos Elf (assumindo product_id de 1 a 5 para os produtos Elfos)
INSERT INTO product_category (product_id, category_id) VALUES (1, 1); -- Arco de Legolas
INSERT INTO product_category (product_id, category_id) VALUES (2, 1); -- Manto de Lórien
INSERT INTO product_category (product_id, category_id) VALUES (3, 1); -- Espada Sting (Ferroada)
INSERT INTO product_category (product_id, category_id) VALUES (4, 1); -- Lembas (Pão Élfico)
INSERT INTO product_category (product_id, category_id) VALUES (5, 1); -- Flauta de Rivendell

-- Associação dos produtos Dwarf (assumindo product_id de 6 a 9 para os produtos Anões)
INSERT INTO product_category (product_id, category_id) VALUES (6, 2); -- Machado de Gimli
INSERT INTO product_category (product_id, category_id) VALUES (7, 2); -- Capacete de Moria
INSERT INTO product_category (product_id, category_id) VALUES (8, 2); -- Caneca de Cerveja Anã
INSERT INTO product_category (product_id, category_id) VALUES (9, 2); -- Picareta de Minas Anãs

-- Associação dos produtos Hobbit (assumindo product_id de 10 a 13 para os produtos Hobbits)
INSERT INTO product_category (product_id, category_id) VALUES (10, 3); -- Cachimbo de Bolseiro
INSERT INTO product_category (product_id, category_id) VALUES (11, 3); -- Capa de Viajante do Condado
INSERT INTO product_category (product_id, category_id) VALUES (12, 3); -- Mapas do Condado
INSERT INTO product_category (product_id, category_id) VALUES (13, 3); -- Geleia de Amoras de Samwise

-- Associação dos produtos Maias (assumindo product_id de 14 a 17 para os produtos Maias)
INSERT INTO product_category (product_id, category_id) VALUES (14, 4); -- Cajado de Gandalf
INSERT INTO product_category (product_id, category_id) VALUES (15, 4); -- Um Anel (Réplica)
INSERT INTO product_category (product_id, category_id) VALUES (16, 4); -- Palantír (Orbe Vidente)
INSERT INTO product_category (product_id, category_id) VALUES (17, 4); -- Amuleto da Estrela da Noite (Arwen)

-- Associação dos produtos Orc (assumindo product_id de 18 a 20 para os produtos Orc)
INSERT INTO product_category (product_id, category_id) VALUES (18, 5); -- Espada Orc de Mordor
INSERT INTO product_category (product_id, category_id) VALUES (19, 5); -- Escudo de Uruk-Hai
INSERT INTO product_category (product_id, category_id) VALUES (20, 5); -- Máscara de Goblins das Montanhas

-- Associação dos produtos Gobblin (assumindo product_id de 21 a 22 para os produtos Goblin)
INSERT INTO product_category (product_id, category_id) VALUES (21, 6); -- Adaga Goblin
INSERT INTO product_category (product_id, category_id) VALUES (22, 6); -- Armadilha Goblin

INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Alex', 'Brown', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Maria', 'Green', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');


INSERT INTO tb_roles (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_roles (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);