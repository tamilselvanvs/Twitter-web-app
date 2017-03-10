Drop database mytwitter;

CREATE DATABASE `mytwitter` /*!40100 DEFAULT CHARACTER SET utf8 */;

use mytwitter;

CREATE TABLE `follow` (
  `followID` int(11) NOT NULL AUTO_INCREMENT,
  `followerUserID` int(11) DEFAULT NULL,
  `followedUserID` int(11) DEFAULT NULL,
  `followDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`followID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

CREATE TABLE `tweet` (
  `TweetID` int(11) NOT NULL AUTO_INCREMENT,
  `SenderEmail` varchar(50) DEFAULT NULL,
  `CurrentDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Tweet` varchar(200) DEFAULT NULL,
  `mentionedUserID` int(11) DEFAULT NULL,
  PRIMARY KEY (`TweetID`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) DEFAULT NULL,
  `FirstName` varchar(50) DEFAULT NULL,
  `LastName` varchar(50) DEFAULT NULL,
  `Birthday` varchar(50) DEFAULT NULL,
  `Nickname` varchar(50) DEFAULT NULL,
  `Password` varchar(300) DEFAULT NULL,
  `LastLogin` datetime DEFAULT NULL,
  `Salt` varchar(50) DEFAULT NULL,
  `CurrentLogin` datetime DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

select * from user;

select * from tweet;