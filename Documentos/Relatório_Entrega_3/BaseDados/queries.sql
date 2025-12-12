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

-- Valor médio por desporto
SELECT spo_name, avg(eve_cost) as ValorMedio, count(*) as TotalEventos
FROM Events
inner join Sports on eve_sport_id = spo_id
group by spo_name

-- Percetagem de eventos pagos x gratuis
SELECT 
    CAST(ROUND(100.0 * SUM(CASE WHEN eve_cost > 0 THEN 1 END) / COUNT(*), 1) AS DECIMAL(5,1)) AS PercentagemPago,
    CAST(ROUND(100.0 * SUM(CASE WHEN eve_cost = 0 THEN 1 END) / COUNT(*), 1) AS DECIMAL(5,1)) AS PercentagemGratuito
FROM Events;

-- Média de custo de eventos por localidade
SELECT adr_city, avg(eve_cost) as ValorMedio, count(*) as TotalEventos
FROM Events
inner join Addresses on eve_address_id = adr_id
group by adr_city

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

-- Quantidade de eventos por género no 'Seixal'
SELECT eve_gender, count(*)
FROM Events
inner join Addresses on eve_address_id = adr_id
where adr_city = 'Seixal'
group by eve_gender

-- Receita total de eventos por cidade
SELECT adr_city, sum(eve_cost * eve_maxMembers) as ReceitaTotal
FROM Events
inner join Addresses on eve_address_id = adr_id
group by adr_city
order by ReceitaTotal desc

-- Receita total de eventos por desporto
SELECT spo_name, sum(eve_cost * eve_maxMembers) as ReceitaTotal
FROM Events
inner join Sports on eve_sport_id =spo_id
group by spo_name
order by ReceitaTotal desc