DROP TABLE T_ORDER IF EXISTS;
CREATE TABLE T_ORDER (ORDER_ID BIGINT, ORDER_NAME VARCHAR(30));

DROP SEQUENCE S_ORDER IF EXISTS;
CREATE SEQUENCE S_ORDER START WITH 1;