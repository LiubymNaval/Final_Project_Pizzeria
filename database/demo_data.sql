USE `Pizzeria`;

-- "123456789Password"
INSERT INTO Pouzivatel (meno, priezvisko, email, heslo, telefon) 
VALUES ('Liubym', 'Naval', 'admin@gmail.sk', '$2a$10$jZJjE6jzXZMtAgGKN9VZH.VCCJpzwsXh0kGZDxlrQ9tRzvPEugEYq', '+4219012345678');
INSERT INTO Pouzivatel (meno, priezvisko, email, heslo, telefon) 
VALUES 
('Peter', 'Kuchár', 'kuchar@gmail.sk', '$2a$10$jZJjE6jzXZMtAgGKN9VZH.VCCJpzwsXh0kGZDxlrQ9tRzvPEugEYq', '+421902111222'),
('Michal', 'Kuriér', 'kurier@gmail.sk', '$2a$10$jZJjE6jzXZMtAgGKN9VZH.VCCJpzwsXh0kGZDxlrQ9tRzvPEugEYq', '+421903333444');

INSERT INTO Rola (nazov) VALUES ('ROLE_ZAKAZNIK'), ('ROLE_KUCHAR'), ('ROLE_KURIER'), ('ROLE_ADMIN');

INSERT INTO Pouzivatel_Roly (Pouzivatel_ID, Rola_ID) VALUES (1, 4); -- ADMIN
INSERT INTO Pouzivatel_Roly (Pouzivatel_ID, Rola_ID) VALUES (2, 2), (3, 3);

INSERT INTO Tag (nazov) VALUES ('Pikantné'), ('Vegetariánske'), ('Novinka'), ('Bezlepkové'), ('Najpredávanejšie');

INSERT INTO Pizza (nazov, popis, slug, obrazok_url, aktivna) VALUES 
('Capricciosa', 'Paradajkový základ, mozzarella, šunka, šampiňóny, olivy, artičoky', 'capricciosa', '/uploads/capricciosa.webp', 1),
('Carbonara', 'Smotanový základ, mozzarella, slanina, vajce, čierne korenie', 'carbonara', '/uploads/carbonara-pizza.webp', 1),
('Delicatezza Rustica', 'Paradajky, mozzarella, prosciutto, rukola, sušené paradajky, olivy', 'delicatezza-rustica', '/uploads/delicatezza-rustica.webp', 1),
('Diavola Piccante', 'Pikantný základ, mozzarella, talianska saláma, feferóny, kukurica', 'diavola', '/uploads/diavola-piccante.webp', 1),
('Funghi', 'Paradajkový základ, mozzarella, čerstvé šampiňóny', 'funghi', '/uploads/funghi.webp', 1),
('Funghi al Panna', 'Smotanový základ, mozzarella, šampiňóny, čerstvý špenát', 'funghi-al-panna', '/uploads/funghi-al-panna.webp', 1),
('Gluten-free Primavera', 'Bezlepkové cesto, zeleninová zmes, cuketa, paprika, cibuľa, rukola', 'primavera-gf', '/uploads/gluten-free-primavera.webp', 1),
('Hawaii Classic', 'Paradajkový základ, mozzarella, šunka, sladký ananás', 'hawaii', '/uploads/hawaii-classic.webp', 1),
('La Crema Bianca', 'Smotanový základ, výber talianskych syrov, bazalkové pesto', 'crema-bianca', '/uploads/la-crema-bianca.webp', 1),
('Margherita Classica', 'Tradičný paradajkový základ, mozzarella, čerstvá bazalka, olivový olej', 'margherita', '/uploads/margherita-classica.webp', 1);

-- 1. Capricciosa
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (1, '33cm', 8.90), (1, '40cm', 11.50), (1, '50cm', 15.90);
-- 2. Carbonara
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (2, '33cm', 9.20), (2, '40cm', 12.00), (2, '50cm', 16.50);
-- 3. Delicatezza Rustica
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (3, '33cm', 10.50), (3, '40cm', 13.50), (3, '50cm', 18.00);
-- 4. Diavola Piccante
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (4, '33cm', 9.50), (4, '40cm', 12.50), (4, '50cm', 16.90);
-- 5. Funghi
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (5, '33cm', 7.90), (5, '40cm', 10.50), (5, '50cm', 14.50);
-- 6. Funghi al Panna
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (6, '33cm', 8.50), (6, '40cm', 11.20), (6, '50cm', 15.20);
-- 7. Gluten-free Primavera
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (7, '33cm', 11.00), (7, '40cm', 14.00), (7, '50cm', 18.50);
-- 8. Hawaii Classic
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (8, '33cm', 8.70), (8, '40cm', 11.40), (8, '50cm', 15.60);
-- 9. La Crema Bianca
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (9, '33cm', 9.80), (9, '40cm', 12.80), (9, '50cm', 17.20);
-- 10. Margherita Classica
INSERT INTO Pizza_Velkost (Pizza_ID, nazov_velkosti, cena) VALUES (10, '33cm', 6.90), (10, '40cm', 9.50), (10, '50cm', 13.00);

INSERT INTO Ingrediencia (nazov, alergen) VALUES 
('Paradajkový základ', NULL), ('Smotanový základ', 'A7'), ('Mozzarella', 'A7'), 
('Šunka', NULL), ('Šampiňóny', NULL), ('Slanina', NULL), 
('Vajce', 'A3'), ('Prosciutto', NULL), ('Rukola', NULL), 
('Sušené paradajky', NULL), ('Olivy', NULL), ('Artičoky', NULL), 
('Pikantná saláma', NULL), ('Feferóny', NULL), ('Špenát', NULL), 
('Bezlepkové cesto', NULL), ('Ananás', NULL), ('Bazalka', NULL);

INSERT INTO Pizza_Tagy (Tag_ID, Pizza_ID) VALUES 
(1, 4), -- Diavola -> Pikantné
(2, 10), -- Margherita -> Vegetariánske
(2, 5), -- Funghi -> Vegetariánske
(4, 7), -- Primavera -> Bezlepkové
(3, 3), -- Delicatezza -> Novinka
(5, 10), -- Margherita -> Najpredávanejšie
(5, 8); -- Hawaii -> Najpredávanejšie

-- 1. Capricciosa
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (1, 1), (1, 3), (1, 4), (1, 5), (1, 11), (1, 12);
-- 2. Carbonara
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (2, 2), (2, 3), (2, 6), (2, 7);
-- 3. Delicatezza Rustica
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (3, 1), (3, 3), (3, 8), (3, 9), (3, 10), (3, 11);
-- 4. Diavola Piccante
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (4, 1), (4, 3), (4, 13), (4, 14);
-- 5. Funghi
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (5, 1), (5, 3), (5, 5);
-- 6. Funghi al Panna
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (6, 2), (6, 3), (6, 5), (6, 15);
-- 7. Gluten-free Primavera
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (7, 16), (7, 9), (7, 15);
-- 8. Hawaii Classic
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (8, 1), (8, 3), (8, 4), (8, 17);
-- 9. La Crema Bianca
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (9, 2), (9, 3), (9, 18);
-- 10. Margherita Classica
INSERT INTO Pizza_Ingrediencie (Pizza_ID, Ingrediencia_ID) VALUES (10, 1), (10, 3), (10, 18);

INSERT INTO Objednavka (Pouzivatel_ID, kuchar_id, kurier_id, stav, celkova_cena, adresa, poznamka) VALUES 
(1, 2, 3, 'DORUCENA', 24.10, 'Hlavná 15, Nitra', 'Doručiť k zadnému vchodu'),
(1, 2, 3, 'DORUCUJE_SA', 15.90, 'Štefánikova 2, Nitra', 'Nechajte pred dverami'),
(1, 2, NULL, 'PRIPRAVENA', 11.20, 'Akademická 1, Nitra', 'Bez cibule prosím'),
(1, 2, NULL, 'PRIPRAVUJE_SA', 31.80, 'Mostná 4, Nitra', NULL),
(1, NULL, NULL, 'CAKAJUCA', 8.50, 'Farská 10, Nitra', 'Volajte vopred');

INSERT INTO Polozka_Objednavky (Objednavka_ID, mnozstvo, archivny_nazov, archivna_cena) VALUES 
(1, 2, 'Capricciosa 33cm', 8.90), 
(1, 1, 'Margherita Classica 50cm', 13.00),
(2, 1, 'Carbonara 40cm', 12.00),
(3, 1, 'Diavola Piccante 33cm', 9.50),
(4, 2, 'Hawaii Classic 50cm', 15.60),
(5, 1, 'Funghi 33cm', 7.90);