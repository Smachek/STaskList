-- Create and populate test data for STaskList in H2 database
DROP TABLE IF EXISTS TASK;
DROP TABLE IF EXISTS FOLDER;

CREATE TABLE FOLDER (
  ID_FOLDER INT PRIMARY KEY AUTO_INCREMENT
, NAME_FOLDER VARCHAR(255) NOT NULL UNIQUE
, DESCRIPTION VARCHAR(1000)
, CREATE_DATE DATE NOT NULL
);

CREATE TABLE TASK (
  ID_TASK INT PRIMARY KEY AUTO_INCREMENT
, ID_FOLDER INT NOT NULL
, NAME_TASK VARCHAR(255) NOT NULL
, PRIORITY INT DEFAULT 0
, DESCRIPTION VARCHAR(1000)
, START_DATE DATE
, DUE_DATE DATE
, CREATE_DATE DATE NOT NULL
, DONE_MARK BOOLEAN DEFAULT FALSE
, DONE_DATE DATE
, CONSTRAINT FK_FOLDER FOREIGN KEY (id_folder) REFERENCES folder(id_folder)
);

-- folder
INSERT INTO FOLDER VALUES(0, 'INBOX', 'For new tasks', DATE '2020-01-01');
INSERT INTO FOLDER VALUES(1, 'Study', 'Learning something', DATE '2020-01-01');
INSERT INTO FOLDER VALUES(2, 'Personal', 'For personal tasks', DATE '2020-01-01');
INSERT INTO FOLDER VALUES(3, 'Work', 'For work tasks', DATE '2020-01-01');

-- task
INSERT INTO TASK (ID_TASK, ID_FOLDER, NAME_TASK, PRIORITY, DESCRIPTION, START_DATE, DUE_DATE, CREATE_DATE, DONE_MARK)
VALUES (1, 0, 'Do something till tomorrow', 0, 'Description do something.', DATE '2020-09-01',  DATE '2020-09-10',  DATE '2020-09-01', FALSE);
INSERT INTO TASK (ID_TASK, ID_FOLDER, NAME_TASK, PRIORITY, DESCRIPTION, START_DATE, DUE_DATE, CREATE_DATE, DONE_MARK, DONE_DATE)
VALUES (2, 2, 'Another task', 1, 'Another description', DATE '2020-09-02',  null,  DATE '2020-09-01', TRUE, DATE '2020-09-29');
INSERT INTO TASK (ID_TASK, ID_FOLDER, NAME_TASK, PRIORITY, DESCRIPTION, START_DATE, DUE_DATE, CREATE_DATE, DONE_MARK)
VALUES (3, 2, 'Develop mega soft', 1, 'Huge work', DATE '2020-09-03',  DATE '2021-09-05',  DATE '2020-09-01', FALSE);
INSERT INTO TASK (ID_TASK, ID_FOLDER, NAME_TASK, PRIORITY, DESCRIPTION, START_DATE, DUE_DATE, CREATE_DATE, DONE_MARK)
VALUES (4, 3, 'Hard task', 0, '', DATE '2020-09-01',  null,  DATE '2020-09-01', FALSE);

COMMIT;