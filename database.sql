
CREATE TABLE User (
    User_ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Role VARCHAR(50) NOT NULL
);


CREATE TABLE Country (
    Country_ID INT AUTO_INCREMENT PRIMARY KEY,
    Country_Name VARCHAR(100) NOT NULL
);


CREATE TABLE Budget (
    Budget_ID INT AUTO_INCREMENT PRIMARY KEY,
    Country_ID INT NOT NULL,
    Year INT NOT NULL,
    Total_Revenue DECIMAL(15,2),
    Total_Expenses DECIMAL(15,2),
    Balance VARCHAR(30),
    
    FOREIGN KEY (Country_ID) REFERENCES Country(Country_ID)
);


CREATE TABLE BudgetItemCategory (
    Category_ID INT AUTO_INCREMENT PRIMARY KEY,
    Category_Name VARCHAR(100) NOT NULL,
    Type ENUM('Revenue', 'Expenditure') NOT NULL
);


CREATE TABLE BudgetItem (
    Item_ID INT AUTO_INCREMENT PRIMARY KEY,
    Budget_ID INT NOT NULL,
    Category_ID INT NOT NULL,
    Amount DECIMAL(15,2) NOT NULL,

    FOREIGN KEY (Budget_ID) REFERENCES Budget(Budget_ID),
    FOREIGN KEY (Category_ID) REFERENCES BudgetItemCategory(Category_ID)
);


CREATE TABLE Changes (
    Change_ID INT AUTO_INCREMENT PRIMARY KEY,
    Item_ID INT NOT NULL,
    User_ID INT NOT NULL,
    Old_Amount DECIMAL(15,2),
    New_Amount DECIMAL(15,2) NOT NULL,
    Change_Date DATETIME NOT NULL,

    FOREIGN KEY (Item_ID) REFERENCES BudgetItem(Item_ID),
    FOREIGN KEY (User_ID) REFERENCES User(User_ID)
);


CREATE TABLE BudgetComparison (
    Comparison_ID INT AUTO_INCREMENT PRIMARY KEY,
    Budget1_ID INT NOT NULL,
    Budget2_ID INT NOT NULL,
    User_ID INT NOT NULL,

    FOREIGN KEY (Budget1_ID) REFERENCES Budget(Budget_ID),
    FOREIGN KEY (Budget2_ID) REFERENCES Budget(Budget_ID),
    FOREIGN KEY (User_ID) REFERENCES User(User_ID)
);
