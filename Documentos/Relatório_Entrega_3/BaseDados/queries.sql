-- Procurar todos os utilizadores
SELECT * FROM Users;

-- Procurar todos os eventos
SELECT * FROM Events;

-- Procurar todos os desportos disponíveis
SELECT spo_name AS Sport
FROM Sports
ORDER BY spo_name;

-- Procurar todos os eventos ordenados por data
SELECT eve_name, eve_date
FROM Events
ORDER BY eve_date;

-- Procurar por eventos de um determinado desporto
SELECT e.eve_name, s.spo_name, e.eve_date
FROM Events e
JOIN Sports s ON e.eve_sport_id = s.spo_id
WHERE s.spo_name = N'Football';

-- Procurar eventos com nome do desporto e nome do administrador
SELECT e.eve_name AS Event,s.spo_name AS Sport, u.user_name AS Admin, e.eve_date AS StartDate
FROM Events e
JOIN Sports s ON e.eve_sport_id = s.spo_id
JOIN Users u ON e.eve_admin = u.user_id;

-- Obter utilizadores inscritos em cada evento
SELECT 
    e.eve_name AS Event,
    u.user_name AS Participant
FROM Enrollments en
JOIN Users u ON en.enr_user_id = u.user_id
JOIN Events e ON en.enr_eve_id = e.eve_id
ORDER BY e.eve_name, u.user_name;

-- Média de custo dos eventos
SELECT AVG(eve_cost) AS AvgEventCost FROM Events;

-- Obter numero de eventos existentes por desporto
SELECT s.spo_name AS Sport, COUNT(e.eve_id) AS TotalEvents
FROM Sports s
LEFT JOIN Events e ON s.spo_id = e.eve_sport_id
GROUP BY s.spo_name;

-- Obter eventos futuros
SELECT eve_name, eve_date
FROM Events
WHERE eve_date > GETDATE()
ORDER BY eve_date;

-- Procurar eventos que contenham 'Lisboa' na localização
SELECT eve_name, adr_city
FROM Events e
JOIN Addresses a ON e.eve_address_id = a.adr_id
WHERE adr_city LIKE N'%Lisboa';