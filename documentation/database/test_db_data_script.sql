-- Create and populate test data for STaskList in H2 database
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS folder;

CREATE TABLE folder (
  id_folder INT PRIMARY KEY AUTO_INCREMENT
, name_folder VARCHAR(255) NOT NULL UNIQUE
, description VARCHAR(1000)
, create_date DATE NOT NULL
);

CREATE TABLE task (
  id_task INT PRIMARY KEY AUTO_INCREMENT
, id_folder INT NOT NULL
, name_task VARCHAR(255) NOT NULL
, priority INT DEFAULT 0
, description VARCHAR(1000)
, start_date DATE
, due_date DATE
, create_date DATE NOT NULL
, done_mark BOOLEAN DEFAULT FALSE
, done_date DATE
, CONSTRAINT FK_FOLDER FOREIGN KEY (id_folder) REFERENCES folder(id_folder)
);

-- folder
INSERT INTO folder VALUES(0, 'INBOX', 'For new tasks', DATE '2020-01-01');
INSERT INTO folder VALUES(1, 'ALL', 'All tasks', DATE '2020-01-01');
INSERT INTO folder VALUES(2, 'Personal', 'For personal tasks', DATE '2020-01-01');
INSERT INTO folder VALUES(3, 'Work', 'For work tasks', DATE '2020-01-01');

-- task
INSERT INTO task (id_task, id_folder, name_task, priority, description, start_date, due_date, create_date, done_mark) 
VALUES (1, 0, 'Do something till tomorrow', 0, 'Description do something.', DATE '2020-09-01',  DATE '2020-09-10',  DATE '2020-09-01', FALSE);
INSERT INTO task (id_task, id_folder, name_task, priority, description, start_date, due_date, create_date, done_mark) 
VALUES (2, 2, 'Another task', 1, 'Another description', DATE '2020-09-02',  null,  DATE '2020-09-01', FALSE);
INSERT INTO task (id_task, id_folder, name_task, priority, description, start_date, due_date, create_date, done_mark) 
VALUES (3, 2, 'Develop mega soft', 1, 'Huge work', DATE '2020-09-03',  DATE '2021-09-05',  DATE '2020-09-01', FALSE);
INSERT INTO task (id_task, id_folder, name_task, priority, description, start_date, due_date, create_date, done_mark) 
VALUES (4, 3, 'Hard task', 0, '', DATE '2020-09-01',  null,  DATE '2020-09-01', FALSE);

COMMIT;