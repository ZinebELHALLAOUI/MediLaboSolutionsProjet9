INSERT INTO genre(name)
VALUES
('M'),
('F');
INSERT INTO patient (last_name,first_name,date_of_birth,fk_genre_id,patient_address,phone_number)
VALUES
('TestNone','Test','1966-12-31',2,'1 Brookside St','100-222-3333'),
('TestBorlerline','Test','1945-06-24',1,'2 High St','200-333-4444'),
('TestInDanger','Test','2004-06-18',1,'3 Club Road','300-444-5555'),
('TestEarlyOnset','Test','2002-06-28',2,'4 Valley Dr','400-555-6666');