USE MatchUpDB_Demo;
GO

DECLARE @CountryID UNIQUEIDENTIFIER = NEWID();
DECLARE @SportID UNIQUEIDENTIFIER = NEWID();
DECLARE @UserID UNIQUEIDENTIFIER = NEWID();
DECLARE @AddressID UNIQUEIDENTIFIER = NEWID();
DECLARE @EventID UNIQUEIDENTIFIER = NEWID();
DECLARE @EnrollmentID UNIQUEIDENTIFIER = NEWID();
DECLARE @ReportID UNIQUEIDENTIFIER = NEWID();

INSERT INTO Countries(cou_id, cou_name, cou_phoneCode, cou_icon)
VALUES
(@CountryID, N'Portugal', N'+351', null),
(NEWID(), N'Espanha', N'+34', null)


INSERT INTO Sports(spo_id, spo_name, spo_icon)
VALUES
(@SportID, N'Football', null),
(NEWID(), N'Paddle', null),
(NEWID(), N'Basketball', null),
(NEWID(), N'Futsal', null),
(NEWID(), N'Running', null)

INSERT INTO Addresses(adr_id, adr_street, adr_city, adr_zipCode, adr_latitude, adr_longitude)
VALUES
(@AddressID, N'Rua test test n5', N'Seixal', N'2886-502', 38.822262, -9.139347)

INSERT INTO Users(user_id, user_name, user_email, user_country_id, user_city, user_mobilePhone, user_passwordHash, user_gender, user_profilePicture, user_favSport_id, user_created_at)
VALUES
(@UserID, N'Tiago Lança', N'tiagotest@email.com', @CountryID, N'Seixal', N'912345678', N'b0e2bc09c7c121d6168d709644ddcb9c595119fd13f7e81e617f9079ee8addafbffab3e80f3c75a010f5278a017f07b002247e238ac93a5b226cf0fe10cec5ff', N'M', null, @SportID, GETDATE())

INSERT INTO Events(eve_id, eve_name, eve_date, eve_address_id, eve_cost, eve_duration, eve_gender, eve_sport_id, eve_maxMembers, eve_admin, eve_notes, eve_created_at, eve_status)
VALUES
(@EventID, N'Corrida de Teste', GETDATE(), @AddressID, 2, 30, N'Mix', @SportID, 2, @UserID, N'Trazer casaco', GETDATE(), N'OPEN')

INSERT INTO Enrollments(enr_id, enr_user_id, enr_eve_id, enr_created_at)
VALUES
(@EnrollmentID, @UserID, @EventID, GETDATE())

INSERT INTO Reports(rep_id, rep_user_id, rep_description, rep_date)
VALUES
(@ReportID, @UserID, N'Report de teste', GETDATE())