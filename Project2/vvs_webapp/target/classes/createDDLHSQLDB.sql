CREATE TABLE CUSTOMER (ID INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY NOT NULL,DESIGNATION VARCHAR(50) NOT NULL,PHONENUMBER INTEGER,VATNUMBER INTEGER NOT NULL UNIQUE)
INSERT INTO CUSTOMER (DESIGNATION, PHONENUMBER, VATNUMBER) VALUES ('JOSE FARIA', 914276732, 197672337)
INSERT INTO CUSTOMER (DESIGNATION, PHONENUMBER, VATNUMBER) VALUES ('LUIS SANTOS', 964294317,168027852)
CREATE TABLE SALE (ID INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY NOT NULL,DATE DATE,TOTAL DOUBLE,STATUS CHAR(1),CUSTOMER_VAT INTEGER, CONSTRAINT fk_customer_vat_sale FOREIGN KEY (CUSTOMER_VAT) REFERENCES CUSTOMER (VATNUMBER) ON DELETE CASCADE)
CREATE TABLE ADDRESS (ID INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY NOT NULL,ADDRESS CHAR(100),CUSTOMER_VAT INTEGER,CONSTRAINT fk_customer_vat_address FOREIGN KEY (CUSTOMER_VAT) REFERENCES CUSTOMER (VATNUMBER) ON DELETE CASCADE)
CREATE TABLE SALEDELIVERY (ID INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY NOT NULL,SALE_ID INTEGER,CUSTOMER_VAT INTEGER,ADDRESS_ID INTEGER,CONSTRAINT fk_sale_id FOREIGN KEY (SALE_ID) REFERENCES SALE (ID) ON DELETE CASCADE, CONSTRAINT fk_customer_vat_sale_delivery FOREIGN KEY (CUSTOMER_VAT) REFERENCES CUSTOMER (VATNUMBER) ON DELETE CASCADE, CONSTRAINT fk_address_id FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS (ID) ON DELETE CASCADE)