USE hotelSystem;

# CREATE TABLE Employee(
#     username VARCHAR(20) PRIMARY KEY,
#     password VARCHAR(20) NOT NULL,
#     role VARCHAR(20) NOT NULL
# );


# INSERT INTO Employee
# VALUES
#     ('Thidas', '1234', 'Admin'),
#     ('Jehan', '1234', 'Receptionist')
#     ('MKBHD', '1234', 'Admin'),
#     ('Kaveen', '1234', 'Receptionist'),
#     ('Neveen', '1234', 'Receptionist'),
#     ('Sheron', '1234', 'Receptionist'),
#     ('Tim', '1234', 'Receptionist'),
#     ('Adam', '1234', 'Receptionist'),
#     ('Elis', '1234', 'Receptionist'),
#     ('Mariah', '1234', 'Receptionist'),
#     ('David', '1234', 'Receptionist'),
#     ('Andrew', '1234', 'Receptionist')
# ;
#
# CREATE TABLE Room(
#     roomId INT AUTO_INCREMENT PRIMARY KEY,
#     space VARCHAR(20) NOT NULL,
#     tier VARCHAR(20) NOT NULL,
#     status VARCHAR(20) NOT NULL,
#     price DECIMAL(10,2) NOT NULL
# );
#
# CREATE TABLE CustomerRecord(
#      recordId INT AUTO_INCREMENT PRIMARY KEY,
#      customerName VARCHAR(20) NOT NULL,
#      roomId INT NOT NULL,
#      checkIn DATE NOT NULL,
#      checkOut DATE NOT NULL,
#      price DECIMAL(10,2) NOT NULL,
#      FOREIGN KEY (roomId) REFERENCES Room(roomId)
# );

#
# INSERT INTO Room (space, tier, status, price) VALUES
#     ('Single', 'Standard', 'Booked', 3000.00),        -- Room 1: Olivia Taylor
#     ('Double', 'Standard', 'Booked', 5000.00),        -- Room 2: John Doe, Michael Black
#     ('Single', 'Premium', 'Booked', 5000.00),         -- Room 3: Ava Thompson
#     ('Double', 'Premium', 'Booked', 8000.00),         -- Room 4: William Garcia
#     ('Single', 'Standard', 'Booked', 3000.00),        -- Room 5: Jane Smith, Sarah Davis
#     ('Double', 'Standard', 'Booked', 5000.00),        -- Room 6: Matthew Thomas
#     ('Single', 'Premium', 'Booked', 5000.00),         -- Room 7: Sophia Moore
#     ('Double', 'Premium', 'Booked', 8000.00),         -- Room 8: Alice Johnson, David Miller
#     ('Single', 'Standard', 'Maintenance', 3000.00),   -- Room 9: (Unbooked)
#     ('Double', 'Standard', 'Need Cleaning', 5000.00), -- Room 10: (Unbooked)
#     ('Single', 'Premium', 'Booked', 5000.00),         -- Room 11: Bob Brown, James Wilson
#     ('Double', 'Premium', 'Booked', 8000.00),         -- Room 12: Lucas Jackson
#     ('Single', 'Standard', 'Booked', 3000.00),        -- Room 13: Mia Martin
#     ('Double', 'Standard', 'Booked', 5000.00),        -- Room 14: Charlie Green, Emma Martinez
#     ('Single', 'Premium', 'Booked', 5000.00),         -- Room 15: Ethan Lee
#     ('Double', 'Premium', 'Need Cleaning', 8000.00), -- Room 16: (Unbooked)
#     ('Single', 'Standard', 'Ready', 3000.00),         -- Room 17: (Unbooked)
#     ('Double', 'Standard', 'Ready', 5000.00),         -- Room 18: (Unbooked)
#     ('Single', 'Premium', 'Maintenance', 5000.00),    -- Room 19: (Unbooked)
#     ('Double', 'Premium', 'Booked', 8000.00)          -- Room 20: Emily White, Daniel Anderson
# ;

#
# INSERT INTO CustomerRecord (customerName, roomId, checkIn, checkOut, price) VALUES
#     ('John Doe', 2, '2026-06-01', '2026-06-03', 10000.00),     -- 2 nights x 5000
#     ('Jane Smith', 5, '2026-06-01', '2026-06-02', 3000.00),    -- 1 night x 3000
#     ('Alice Johnson', 8, '2026-06-02', '2026-06-05', 24000.00), -- 3 nights x 8000
#     ('Bob Brown', 11, '2026-06-03', '2026-06-06', 15000.00),   -- 3 nights x 5000
#     ('Charlie Green', 14, '2026-06-04', '2026-06-05', 5000.00), -- 1 night x 5000
#     ('Emily White', 20, '2026-06-05', '2026-06-07', 16000.00),  -- 2 nights x 8000
#     ('Michael Black', 2, '2026-06-10', '2026-06-12', 10000.00), -- 2 nights x 5000
#     ('Sarah Davis', 5, '2026-06-12', '2026-06-15', 9000.00),    -- 3 nights x 3000
#     ('David Miller', 8, '2026-06-12', '2026-06-13', 8000.00),   -- 1 night x 8000
#     ('James Wilson', 11, '2026-06-14', '2026-06-18', 20000.00), -- 4 nights x 5000
#     ('Emma Martinez', 14, '2026-06-15', '2026-06-17', 10000.00),-- 2 nights x 5000
#     ('Daniel Anderson', 20, '2026-06-18', '2026-06-20', 16000.00),-- 2 nights x 8000
#     ('Olivia Taylor', 1, '2026-06-01', '2026-06-04', 9000.00),  -- 3 nights x 3000
#     ('Matthew Thomas', 6, '2026-06-02', '2026-06-03', 5000.00), -- 1 night x 5000
#     ('Sophia Moore', 7, '2026-06-05', '2026-06-08', 15000.00),  -- 3 nights x 5000
#     ('Lucas Jackson', 12, '2026-06-07', '2026-06-09', 16000.00),-- 2 nights x 8000
#     ('Mia Martin', 13, '2026-06-10', '2026-06-11', 3000.00),   -- 1 night x 3000
#     ('Ethan Lee', 15, '2026-06-11', '2026-06-14', 15000.00),   -- 3 nights x 5000
#     ('Ava Thompson', 3, '2026-06-15', '2026-06-16', 5000.00),   -- 1 night x 5000
#     ('William Garcia', 4, '2026-06-20', '2026-06-25', 40000.00) -- 5 nights x 8000
# ;


# SELECT * FROM Employee;

# SELECT * FROM CustomerRecord;
#
# SELECT * FROM Room;

#  SELECT  SUM(price) AS total FROM CustomerRecord WHERE checkIn = CURRENT_DATE();
#  SELECT SUM(price) AS total FROM CustomerRecord WHERE MONTH(checkIn) = MONTH(CURRENT_DATE()) AND YEAR(checkIn) = YEAR(CURRENT_DATE());

# SELECT
#     SUM(c.price) AS total,
#     SUM(CASE WHEN r.tier = 'Standard' AND r.space = 'Single' THEN 1 ELSE 0 END) AS standardSingle,
#     SUM(CASE WHEN r.tier = 'Standard' AND r.space = 'Double' THEN 1 ELSE 0 END) AS standardDouble,
#     SUM(CASE WHEN r.tier = 'Premium' AND r.space = 'Single' THEN 1 ELSE 0 END) AS premiumSingle,
#     SUM(CASE WHEN r.tier = 'Premium' AND r.space = 'Double' THEN 1 ELSE 0 END) AS premiumDouble
#
# FROM CustomerRecord c
#          JOIN Room r ON c.roomId = r.roomId
# WHERE MONTH(checkIn) = MONTH(CURRENT_DATE()) AND YEAR(checkIn) = YEAR(CURRENT_DATE());

SHOW TABLES ;