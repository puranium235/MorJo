CREATE DATABASE morjo_db;
USE morjo_db;

CREATE TABLE `user` (
  `user_id` bigint PRIMARY KEY AUTO_INCREMENT,
  `kakao_id` bigint UNIQUE NOT NULL,
  `nickname` varchar(48) UNIQUE NOT NULL,
  `role` varchar(20) DEFAULT 'member',
  `created_at` timestamp DEFAULT now()
);

CREATE TABLE `quiz` (
  `quiz_id` bigint PRIMARY KEY AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `option1` varchar(100) NOT NULL,
  `option2` varchar(100) NOT NULL,
  `option3` varchar(100),
  `option4` varchar(100),
  `answer` integer NOT NULL,
  `created_user_id` bigint NOT NULL,
  `created_at` timestamp DEFAULT now(),
  FOREIGN KEY (`created_user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `quiz_user` (
  `quiz_user_id` bigint PRIMARY KEY AUTO_INCREMENT,
  `quiz_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `user_answer` integer NOT NULL,
  `is_common_sense` bit NOT NULL,
  FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `comment` (
  `comment_id` bigint PRIMARY KEY AUTO_INCREMENT,
  `quiz_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(512) NOT NULL,
  `created_at` timestamp DEFAULT now(),
  `deleted` bit DEFAULT false,
  FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);
