
#IAdapter

***

This tool is the result of the doctoral research of Francisco Nauber Bernardo Gois in the doctoral program in applied informatics of the university of fortaleza (UNIFOR)

[Doutorado em Informática Aplicada](http://www.unifor.br/index.php?option=com_content&view=article&id=481&Itemid=879)

***

---
Conference-Papers:
- Improving stress search based testing using a hybrid metaheuristic approach
  Francisco Nauber Bernardo Gois ;  Pedro Porfírio Muniz de Farias ;  André L. V. Coelho ;  Thiago Monteiro Barbosa
  DOI: 10.1109/CLEI.2016.7833374  
  Computing Conference (CLEI), 2016 XLII Latin American  
  October, 2016

---


***
IAdapter is a JMeter plugin designed to perform search-based stress tests.

Bellow, a video showing how the iadapter works

[![IMAGE ALT TEXT](video11.png)](http://www.youtube.com/watch?v=CneugJQT_3w "How IAdapter Works")

Bellow, a video showing how the install iadapter

[![IMAGE ALT TEXT](video2.png)](http://www.youtube.com/watch?v=XSZJmsJZSJk "How install IAdapter")


Download the IAdapter file in the link bellow

[IAdapter Download](https://drive.google.com/file/d/0B6ynMokoY-JzWWlDcnQ3UVRQaHM/view?usp=sharing)

Bellow, it is the sql to install the database

```
CREATE TABLE `agent` (
  `name` varchar(45) DEFAULT NULL,
  `running` varchar(45) DEFAULT NULL,
  `ip` varchar(145) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4414 DEFAULT CHARSET=latin1;

CREATE TABLE `log` (
  `log` varchar(200) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=886 DEFAULT CHARSET=latin1;

CREATE TABLE `Q` (
  `responsetime` varchar(245) DEFAULT NULL,
  `testplan` varchar(245) DEFAULT NULL,
  `Qvalue` float DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33730 DEFAULT CHARSET=latin1;

CREATE TABLE `samples` (
  `LABEL` varchar(100) DEFAULT NULL,
  `RESPONSETIME` varchar(45) DEFAULT NULL,
  `MESSAGE` varchar(300) DEFAULT NULL,
  `INDIVIDUAL` varchar(345) DEFAULT NULL,
  `GENERATION` varchar(45) DEFAULT NULL,
  `TESTPLAN` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `workload` (
  `NAME` varchar(330) DEFAULT NULL,
  `RESPONSETIME` varchar(30) DEFAULT NULL,
  `TYPE` varchar(300) DEFAULT NULL,
  `USERS` varchar(30) DEFAULT NULL,
  `ERROR` varchar(30) DEFAULT NULL,
  `FIT` varchar(400) DEFAULT NULL,
  `FUNCTION1` varchar(30) DEFAULT NULL,
  `FUNCTION2` varchar(30) DEFAULT NULL,
  `FUNCTION3` varchar(30) DEFAULT NULL,
  `FUNCTION4` varchar(30) DEFAULT NULL,
  `FUNCTION5` varchar(30) DEFAULT NULL,
  `FUNCTION6` varchar(30) DEFAULT NULL,
  `FUNCTION7` varchar(30) DEFAULT NULL,
  `FUNCTION8` varchar(30) DEFAULT NULL,
  `FUNCTION9` varchar(30) DEFAULT NULL,
  `FUNCTION10` varchar(30) DEFAULT NULL,
  `TESTPLAN` varchar(40) DEFAULT NULL,
  `GENERATION` varchar(45) DEFAULT NULL,
  `ACTIVE` varchar(45) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `PERCENT90` varchar(45) DEFAULT NULL,
  `PERCENT80` varchar(45) DEFAULT NULL,
  `PERCENT70` varchar(45) DEFAULT NULL,
  `TOTALERROR` varchar(45) DEFAULT NULL,
  `SEARCHMETHOD` varchar(345) DEFAULT NULL,
  `USER1` varchar(45) DEFAULT NULL,
  `USER2` varchar(45) DEFAULT NULL,
  `USER3` varchar(45) DEFAULT NULL,
  `USER4` varchar(45) DEFAULT NULL,
  `USER5` varchar(45) DEFAULT NULL,
  `USER6` varchar(45) DEFAULT NULL,
  `USER7` varchar(45) DEFAULT NULL,
  `USER8` varchar(45) DEFAULT NULL,
  `USER9` varchar(45) DEFAULT NULL,
  `USER10` varchar(45) DEFAULT NULL,
  `MEMORY` varchar(45) DEFAULT NULL,
  `CPUSHARE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33730 DEFAULT CHARSET=latin1;


```

