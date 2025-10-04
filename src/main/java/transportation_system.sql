DROP DATABASE IF EXISTS transportation_system;
CREATE DATABASE transportation_system;
USE transportation_system;

CREATE TABLE User (
    User_id INT AUTO_INCREMENT NOT NULL,
    Name VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(150) UNIQUE NOT NULL,
    User_Type VARCHAR(50) NOT NULL,
    CONSTRAINT User_idPK PRIMARY KEY (User_Id)
);

CREATE TABLE Break (
    Break_id INT AUTO_INCREMENT NOT NULL,
    User_id INT NOT NULL,
    Start_Time TIMESTAMP NOT NULL,
    End_Time TIMESTAMP NOT NULL,
    CONSTRAINT Break_idPK PRIMARY KEY(Break_id)
);

CREATE TABLE Station (
    Station_ID INT AUTO_INCREMENT NOT NULL,
    StationName VARCHAR(100) NOT NULL,
    CONSTRAINT Station_IDPK PRIMARY KEY (Station_ID)
);

CREATE TABLE Route (
    Route_ID INT AUTO_INCREMENT NOT NULL,
    Distance DECIMAL(10,2) NOT NULL,
    Expected_Duration INT NOT NULL,
    CONSTRAINT Route_IDPK PRIMARY KEY (Route_ID)
);

CREATE TABLE Vehicle (
    Vehicle_ID INT AUTO_INCREMENT NOT NULL,
    Vehicle_Name VARCHAR(100) NOT NULL,
    Vehicle_Type VARCHAR(50) NOT NULL,
    Max_capacity INT NOT NULL,
    Current_Route_ID INT,
    axle_bearings DECIMAL(5,2),
    fuel_type VARCHAR(50),
    consumption_rate DECIMAL(5,2),
    Status VARCHAR(30) NOT NULL,
    Wheels DECIMAL(5,2),
    Brakes DECIMAL(5,2),
    Fuel_level DECIMAL(8,2),
    CONSTRAINT Vehicle_IDPK PRIMARY KEY (Vehicle_ID),
    FOREIGN KEY (Current_Route_ID) REFERENCES Route(Route_ID)
);

CREATE TABLE Diesel_Train (
    Vehicle_ID INT PRIMARY KEY,
    Oil_life DECIMAL(5,2),
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
);

CREATE TABLE Electric_light_rail (
    Vehicle_ID INT PRIMARY KEY,
    Pantograph DECIMAL(5,2),
    Catenary DECIMAL(5,2),
    Circuit_breaker DECIMAL(5,2),
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
);

CREATE TABLE Buses (
    Vehicle_ID INT PRIMARY KEY,
    Emission_rate DECIMAL(5,2),
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
);

CREATE TABLE Vehicles_Station (
    Vehicle_Station_ID INT AUTO_INCREMENT NOT NULL,
    Vehicle_ID INT,
    Station_ID INT,
    Departure_Time TIMESTAMP,
    Arrival_Time TIMESTAMP,
    CONSTRAINT VehicleStation_IDPK PRIMARY KEY (Vehicle_Station_ID),
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID),
    FOREIGN KEY (Station_ID) REFERENCES Station(Station_ID),
    CONSTRAINT chk_time_order CHECK (Arrival_Time < Departure_Time)
);

CREATE TABLE Routes_Station (
    Route_Station_ID INT AUTO_INCREMENT NOT NULL,
    Route_ID INT,
    Station_ID INT,
    CONSTRAINT Route_Station_IDPK PRIMARY KEY (Route_Station_ID),
    FOREIGN KEY (Route_ID) REFERENCES Route(Route_ID),
    FOREIGN KEY (Station_ID) REFERENCES Station(Station_ID)
);

CREATE TABLE Maintenance (
    Maintenance_ID INT AUTO_INCREMENT NOT NULL,
    Vehicle_ID INT,
    Purpose varchar(50) NOT NULL,
    Cost DECIMAL(10,2),
    Start_Time TIMESTAMP NOT NULL,
    End_Time TIMESTAMP NULL,
    CONSTRAINT Maintenance_IDPK PRIMARY KEY (Maintenance_ID),
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID),
    CONSTRAINT chk_time_order_maintenance CHECK (Start_Time < End_Time)
);

CREATE TABLE Alert (
    Alert_ID INT AUTO_INCREMENT NOT NULL,
    Vehicle_ID INT,
    Alert_Type VARCHAR(100),
    Alert_Reason TEXT,
    Status VARCHAR(50),
    Alert_Time TIMESTAMP,
    CONSTRAINT Alert_IDPK PRIMARY KEY (Alert_ID),
    FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
);

CREATE TABLE Trip (
    Trip_ID INT AUTO_INCREMENT NOT NULL,
    User_ID INT NOT NULL,
    Vehicle_ID INT NOT NULL,
    Route_ID INT NOT NULL,
    Start_Time TIMESTAMP NOT NULL,
    End_Time TIMESTAMP NULL,
    Fuel_Used DECIMAL(5,2) NULL,
    Fuel_Efficiency DECIMAL(5,2) NULL,
    On_Time BOOLEAN NOT NULL,
    CONSTRAINT Trip_IDPK PRIMARY KEY (Trip_ID),
    CONSTRAINT Vehicle_IDFK FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID),
    CONSTRAINT Route_IDFK FOREIGN KEY (Route_ID) REFERENCES Route(Route_ID),
    CONSTRAINT chk_time_Trip CHECK (Start_Time < End_Time)
);

CREATE TABLE Location (
    Location_ID INT AUTO_INCREMENT NOT NULL,
    Vehicle_ID INT NOT NULL,
    Latitude DECIMAL(5,2),
    Longitude DECIMAL(5,2),
    Updated TIMESTAMP NOT NULL,
    Most_Recent BOOLEAN NOT NULL,
    CONSTRAINT Location_IDPK PRIMARY KEY (Location_ID),
    CONSTRAINT Vehicle_IDFKey FOREIGN KEY (Vehicle_ID) REFERENCES Vehicle(Vehicle_ID)
);

-- Insert into User
INSERT INTO User (Name, Password, Email, User_Type) VALUES
('Simon', 'simon123', 'simon@example.com', 'OPERATOR'),
('Ace', 'ace123', 'ace@example.com', 'TRANSIT MANAGER'),
('Santiago', 'santiago123', 'santiago@example.com', 'OPERATOR'),
('Thuva', 'thuva123', 'thuva@example.com', 'OPERATOR'),
('Felix', 'felix123', 'felix@example.com', 'TRANSIT MANAGER'),
('Julie', 'julie123', 'julie@example.com', 'OPERATOR'),
('Wei', 'wei123', 'wei@example.com', 'OPERATOR');

-- Insert into Station
INSERT INTO Station (StationName) VALUES
('Woodroffe / David Station'),
('Baseline Station'),
('Fallowfield Station'),
('Tunneys Pasture Station'),
('Westboro Station'),
('Kanata Station');

-- Insert into Route
INSERT INTO Route (Distance, Expected_Duration) VALUES
(10.5, 15),
(20.0, 30),
(30.25, 45),
(15.75, 20),
(12.0, 18),
(22.8, 35);

-- Insert into Vehicle
INSERT INTO Vehicle (Vehicle_Name, Vehicle_Type, Max_capacity, Current_Route_ID, axle_bearings, fuel_type, consumption_rate, Wheels, Brakes, Fuel_level, Status) VALUES
('Train A', 'DIESEL TRAIN', 300, 1, 30.2, 'Diesel', 50.5, 80.0, 40.0, 60.00, 'ACTIVE'),
('Train B', 'ELECTRIC LIGHT RAIL', 650, 2, 20.5, 'Electric', 30.8, 60.0, 20.5, 80.00, 'ACTIVE'),
('Bus A', 'BUS', 50, 3, 10.8, 'Diesel', 40.5, 40.0, 20.5, 40.00, 'ACTIVE'),
('Train C', 'DIESEL TRAIN', 280, 4, 30.5, 'Diesel', 50.0, 80.0, 40.2, 55.00, 'ACTIVE'),
('Bus B', 'BUS', 60, 5, 70.9, 'Diesel', 40.3, 29.0, 20.3, 42.00, 'OUT OF SERVICE'),
('Train D', 'ELECTRIC LIGHT RAIL', 460, 6, 80.7, 'Electric', 29.9, 60.0, 30.8, 75.00, 'ACTIVE');

-- Insert into Diesel_Train
INSERT INTO Diesel_Train (Vehicle_ID, Oil_life) VALUES
(1, 25.5),
(4, 90.0);

-- Insert into Electric_light_rail
INSERT INTO Electric_light_rail (Vehicle_ID, Pantograph, Catenary, Circuit_breaker) VALUES
(2, 30.2, 20.3, 30.4),
(6, 70.1, 68.2, 30.3);

-- Insert into Buses
INSERT INTO Buses (Vehicle_ID, Emission_rate) VALUES
(3, 30.75),
(5, 29.60);

-- Insert into Break
INSERT INTO Break (User_id, Start_Time, End_Time) VALUES
(1, '2025-08-02 10:00:00', '2025-08-02 10:30:00'),
(2, '2025-08-03 11:00:00', '2025-08-03 11:30:00'),
(3, '2025-08-04 12:00:00', '2025-08-04 12:30:00'),
(4, '2025-08-01 13:00:00', '2025-08-01 13:30:00'),
(5, '2025-08-02 14:00:00', '2025-08-02 14:30:00'),
(6, '2025-08-03 15:00:00', '2025-08-03 15:30:00');

-- Insert into Vehicles_Station
INSERT INTO Vehicles_Station (Vehicle_ID, Station_ID, Departure_Time, Arrival_Time) VALUES
(1, 1, '2025-08-05 09:00:00', '2025-08-05 08:50:00'),
(2, 2, '2025-08-01 13:15:00', '2025-08-01 13:00:00'),
(3, 3, '2025-08-02 10:10:00', '2025-08-02 10:00:00'),
(4, 4, '2025-08-03 12:30:00', '2025-08-03 12:15:00'),
(5, 5, '2025-08-04 11:20:00', '2025-08-04 11:05:00'),
(6, 6, '2025-08-05 14:45:00', '2025-08-05 14:30:00');

-- Insert into Routes_Station
INSERT INTO Routes_Station (Route_ID, Station_ID) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 5),
(5, 6);

-- Insert into Maintenance
INSERT INTO Maintenance (Vehicle_ID, Purpose, Cost, Start_Time, End_Time) VALUES
(1, 'REPAIRS', 500.00, '2025-08-05 10:10:00', '2025-08-05 18:30:00'),
(2, 'REPAIRS', 450.00, '2025-08-05 13:00:00', '2025-08-05 17:00:00'),
(3, 'CLEANING', 300.00, '2025-08-05 09:00:00', '2025-08-05 12:10:00'),
(4, 'REFUEL', 520.00, '2025-08-05 08:00:00', CURRENT_TIMESTAMP),
(5, 'INSPECTIONS', 280.00, '2025-08-05 10:00:00', CURRENT_TIMESTAMP),
(6, 'REPAIRS', 610.00, '2025-08-05 11:10:00', '2025-08-05 22:00:00');


-- Insert into Alert
INSERT INTO Alert (Vehicle_ID, Alert_Type, Alert_Reason, Status, Alert_Time) VALUES
(1, 'Oil Low', 'Oil level below threshold', 'Pending', '2025-08-05 10:10:00'),
(2, 'Power Surge', 'Unexpected input detected', 'Resolved', '2025-08-03 13:00:00'),
(3, 'Brake Issue', 'Brake wear above limit', 'Pending', '2025-08-05 09:10:00'),
(4, 'Fuel Leak', 'Fuel level dropping fast', 'Critical', '2025-08-05 08:00:00'),
(5, 'Overheating', 'Engine overheating', 'Warning', '2025-08-04 10:00:00'),
(6, 'Battery Low', 'Electric charge low', 'Pending', '2025-08-05 22:00:00');

-- Insert into Trip
INSERT INTO Trip (Vehicle_ID, User_ID, Route_ID, Start_Time, End_Time, Fuel_Used, Fuel_Efficiency, On_Time) VALUES
(1, 1, 1, '2025-08-05 08:00:00', '2025-08-05 17:15:00', 20.0, 3.0, TRUE),
(2, 2, 2, '2025-08-05 08:00:00', '2025-08-05 12:20:00', 15.0, 4.0, TRUE),
(3, 3, 3, '2025-08-05 13:00:00', '2025-08-05 16:10:00', 10.0, 5.0, FALSE),
(4, 4, 4, '2025-08-05 10:00:00', '2025-08-05 12:00:00', 22.0, 2.8, FALSE),
(5, 5, 5, '2025-08-05 06:00:00', '2025-08-05 10:10:00', 12.0, 4.5, TRUE),
(6, 6, 6, '2025-08-05 10:10:00', '2025-08-05 13:10:00', 18.0, 3.2, TRUE);

-- Insert into Location
INSERT INTO Location (Vehicle_ID, Latitude, Longitude, Updated, Most_Recent) VALUES
(1, 45.42, -75.69, CURRENT_TIMESTAMP, TRUE),
(2, 45.43, -75.68, CURRENT_TIMESTAMP, TRUE),
(3, 45.44, -75.67, CURRENT_TIMESTAMP, TRUE),
(4, 45.45, -75.66, CURRENT_TIMESTAMP, TRUE),
(5, 45.46, -75.65, CURRENT_TIMESTAMP, TRUE),
(6, 45.47, -75.64, CURRENT_TIMESTAMP, TRUE);