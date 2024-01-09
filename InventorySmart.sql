/*Categorias utilizamos un identificador con su nombre y na breve descripcion*/

CREATE TABLE Category (
    ID_Category INT PRIMARY KEY,
    Name_Category VARCHAR(255) NOT NULL,
    Description_Category VARCHAR(255) NOT NULL
);

/*creamos la secuencia auto incrementable para no crear la secuencia en el backend*/
CREATE SEQUENCE category_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER category_increment
BEFORE INSERT ON Category
FOR EACH ROW
BEGIN
  SELECT category_seq.NEXTVAL
  INTO   :new.ID_Category
  FROM   dual;
END;

/*creamos la tabla de los productos con datos generales y su identificador unico*/
CREATE TABLE Products (
    ID_Product INT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Amount INT NOT NULL,
    ID_Category INT,
    FOREIGN KEY (ID_Category) REFERENCES Category(ID_Category)
);

/*creamos la secuencia igual*/

CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER product_increment
BEFORE INSERT ON Products
FOR EACH ROW
BEGIN
  SELECT product_seq.NEXTVAL
  INTO   :new.ID_Product
  FROM   dual;
END;


/*agregar tabla usuarios para un mejor control*/
CREATE TABLE Users (
    ID_User INT PRIMARY KEY,
    Name_User VARCHAR(255) NOT NULL UNIQUE,
    Email VARCHAR(255) UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Rol VARCHAR(50),
    Date_Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Date_Update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

/*Agregamos los usuarios a las categorias y productos*/

ALTER TABLE Products
ADD (
 Created_By INT NOT NULL,
 Update_By INT NOT NULL,
 FOREIGN KEY (Created_By) REFERENCES Users(ID_User),
 FOREIGN KEY (Update_By) REFERENCES Users(ID_User)
 );

ALTER TABLE Category
ADD (
 Created_By INT NOT NULL,
 Update_By INT NOT NULL,
 FOREIGN KEY (Created_By) REFERENCES Users(ID_User),
 FOREIGN KEY (Update_By) REFERENCES Users(ID_User)
 );
