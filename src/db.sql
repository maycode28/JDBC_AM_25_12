DROP DATABASE IF EXISTS `JDBC_AM_25_12`;
CREATE DATABASE `JDBC_AM_25_12`;
USE `JDBC_AM_25_12`;
drop table if exists member

CREATE TABLE article(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        title CHAR(100) NOT NULL,
                        `body` TEXT NOT NULL
);

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';


DESC article;

SELECT *
FROM article;

##===============================###################### 테스트

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

SELECT NOW();

SELECT '제목1';

SELECT CONCAT('제목','2');

SELECT SUBSTRING(RAND() * 1000 FROM 1 FOR 2);

UPDATE article
SET updateDate = NOW(),
    title = '',
    `body` = 'test1'
WHERE id = 1;

SELECT COUNT(*)
FROM article
WHERE id = 5;


UPDATE article
SET updateDate = NOW(),
    `body` = 'test3'
WHERE id = 3;


SELECT *
FROM article;




DROP DATABASE IF EXISTS `JDBC_AM_25_12`;
CREATE DATABASE `JDBC_AM_25_12`;
USE `JDBC_AM_25_12`;

create table article(
                        id int(10) unsigned not null primary key auto_increment,
                        regDate datetime not null,
                        updateDate datetime not null,
                        title char(100) not null,
                        `body` text not null
);

DROP TABLE article;

desc article;

select *
from article;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

select now();

select '제목1';

select concat('제목','2');

select substring(RAND() * 1000 from 1 for 2);