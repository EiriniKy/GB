CREATE TABLE User (
    User_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Name TEXT NOT NULL,
    Email TEXT UNIQUE NOT NULL,
    Role TEXT NOT NULL
);


CREATE TABLE Country (
    Country_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Country_Name TEXT NOT NULL
);


CREATE TABLE Budget (
    Budget_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Country_ID INTEGER NOT NULL,
    Year INTEGER NOT NULL,
    Total_Revenue DECIMAL(17,2),
    Total_Expenses DECIMAL(17,2),
    Balance TEXT,
    FOREIGN KEY (Country_ID) REFERENCES Country(Country_ID)
);

CREATE TABLE BudgetItemCategory (
    Category_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Category_Name TEXT NOT NULL,
    Type TEXT NOT NULL CHECK(Type IN ('Revenue', 'Expenditure'))
);


CREATE TABLE BudgetItem (
    Item_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Budget_ID INTEGER NOT NULL,
    Category_ID INTEGER NOT NULL,
    Amount DECIMAL(17,2) NOT NULL,
    FOREIGN KEY (Budget_ID) REFERENCES Budget(Budget_ID),
    FOREIGN KEY (Category_ID) REFERENCES BudgetItemCategory(Category_ID)
);


CREATE TABLE Changes (
    Change_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Item_ID INTEGER NOT NULL,
    User_ID INTEGER NOT NULL,
    Old_Amount DECIMAL(17,2),
    New_Amount DECIMAL(17,2) NOT NULL,
    Change_Date TEXT NOT NULL, 
    FOREIGN KEY (Item_ID) REFERENCES BudgetItem(Item_ID),
    FOREIGN KEY (User_ID) REFERENCES User(User_ID)
);


CREATE TABLE BudgetComparison (
    Comparison_ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Budget1_ID INTEGER NOT NULL,
    Budget2_ID INTEGER NOT NULL,
    User_ID INTEGER NOT NULL,
    FOREIGN KEY (Budget1_ID) REFERENCES Budget(Budget_ID),
    FOREIGN KEY (Budget2_ID) REFERENCES Budget(Budget_ID),
    FOREIGN KEY (User_ID) REFERENCES User(User_ID)
);
INSERT INTO Country (Country_ID,Country_Name) VALUES 
(1, 'Ελλάδα'),   
(2, 'Ολλανδία'),  
(3, 'Γερμανία'),
(4, 'Ισπανία'); 

INSERT INTO BudgetItemCategory (Category_Name, Type) VALUES 
('Γενικά Έσοδα Κράτους', 'Revenue'),      
('Γενικά Έξοδα & Δαπάνες', 'Expenditure');

INSERT INTO Budget (Country_ID, Year, Total_Revenue, Total_Expenses, Balance) VALUES 
(1, 2026, 0.00, 0.00, '0'),
(1, 2025, 1304827000000.00, 1307907506000.00,'-3080506000'),
(1, 2024, 1107649000000.00, 1108188270000.00, '-539270000'),
(1, 2023, 798039000000.00, 806878193000.00, '-8839.193000');;


INSERT INTO Budget (Country_ID, Year, Total_Revenue, Total_Expenses, Balance) 
VALUES (2, 2025, 487300000000.00, 497400000000.00, '-10100000000');

INSERT INTO Budget (Country_ID, Year, Total_Revenue, Total_Expenses, Balance) 
VALUES (3, 2025, 428200000000.00, 488900000000.00, '-60700000000');

INSERT INTO Budget (Country_ID, Year, Total_Revenue, Total_Expenses, Balance) 
VALUES (4, 2025, 648000000000.00, 694300000000.00, '-46300000000');

INSERT INTO BudgetItem (Budget_ID, Category_ID, Amount) VALUES 
(2, 1, 487300000000.00), 
(2, 2, 497400000000.00); 

INSERT INTO BudgetItem (Budget_ID, Category_ID, Amount) VALUES 
(3, 1, 428200000000.00),
(3, 2, 488900000000.00);

INSERT INTO BudgetItem (Budget_ID, Category_ID, Amount) VALUES 
(4, 1, 648000000000.00),
(4, 2, 694300000000.00);
