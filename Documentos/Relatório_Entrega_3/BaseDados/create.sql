CREATE DATABASE MatchUpDB_Demo;
GO

USE MatchUpDB_Demo;
GO

CREATE TABLE Countries (
    cou_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    cou_name        NVARCHAR(MAX) NULL,
    cou_phoneCode   NVARCHAR(50) NULL,
    cou_icon        VARBINARY(50) NULL
);
GO

CREATE TABLE Addresses (
    adr_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    adr_street      NVARCHAR(MAX) NULL,
    adr_city        NVARCHAR(MAX) NULL,
    adr_zipCode     NVARCHAR(50) NULL,
    adr_latitude    FLOAT NULL,
    adr_longitude   FLOAT NULL
);
GO

CREATE TABLE Sports (
    spo_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    spo_name        NVARCHAR(MAX)  NULL,
    spo_icon        VARBINARY(MAX) NULL
);
GO

CREATE TABLE Users (
    user_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    user_name        NVARCHAR(MAX) NULL,
    user_email       NVARCHAR(MAX) NULL,
    user_country_id  UNIQUEIDENTIFIER NULL,
    user_city        NVARCHAR(MAX) NULL,
    user_mobilePhone NVARCHAR(MAX) NULL,
    user_passwordHash NVARCHAR(MAX) NULL,
    user_gender      NVARCHAR(50) NULL,
    user_profilePicture VARBINARY(MAX) NULL,
    user_favSport_id UNIQUEIDENTIFIER NULL,    
    user_created_at   DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_country_id) REFERENCES Countries(cou_id),
    FOREIGN KEY (user_favSport_id) REFERENCES Sports(spo_id)
);
GO

CREATE TABLE Events (
    eve_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    eve_name        NVARCHAR(MAX) NULL,
    eve_date        DATETIME NULL,
    eve_address_id  UNIQUEIDENTIFIER NULL,
    eve_cost        FLOAT NULL,
    eve_duration    INT NULL,
    eve_gender      NVARCHAR(50) NULL,
    eve_sport_id    UNIQUEIDENTIFIER NULL,
    eve_maxMembers  INT NULL,
    eve_admin       UNIQUEIDENTIFIER NULL,
    eve_notes       NVARCHAR(MAX) NULL,
    eve_created_at  DATETIME NULL,
    eve_status      NVARCHAR(50) NULL
    FOREIGN KEY (eve_address_id) REFERENCES Addresses(adr_id),
    FOREIGN KEY (eve_sport_id) REFERENCES Sports(spo_id),
    FOREIGN KEY (eve_admin) REFERENCES Users(user_id)
);
GO

CREATE TABLE Enrollments (
    enr_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    enr_user_id     UNIQUEIDENTIFIER NULL,
    enr_eve_id      UNIQUEIDENTIFIER NULL,
    enr_created_at  DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (enr_user_id) REFERENCES Users(user_id),
    FOREIGN KEY (enr_eve_id) REFERENCES Events(eve_id),
    CONSTRAINT UQ_Enrollments UNIQUE (enr_user_id, enr_eve_id)
);
GO

CREATE TABLE Reports (
    rep_id          UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    rep_user_id     UNIQUEIDENTIFIER NULL,
    rep_description NVARCHAR(MAX) NULL,
    rep_date        DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (rep_user_id) REFERENCES Users(user_id)
);
GO

CREATE VIEW EventDetailsView AS
SELECT
    e.eve_id        AS ID,
    e.eve_name      AS Name,
    e.eve_date      AS Date,
    a.adr_street    AS Street,
    a.adr_city      AS City,
    a.adr_zipCode   AS ZipCode,
    a.adr_latitude  AS Latitude,
    a.adr_longitude AS Longitude,
    e.eve_cost      AS Cost,
    e.eve_duration  AS Duration,
    e.eve_gender    AS Gender,
    s.spo_name      AS Sport,
    u.user_name     AS Admin
FROM dbo.Events AS e
INNER JOIN dbo.Users AS u ON e.eve_admin = u.user_id
INNER JOIN dbo.Sports AS s ON e.eve_sport_id = s.spo_id
INNER JOIN dbo.Addresses AS a ON e.eve_address_id = a.adr_id;
GO
