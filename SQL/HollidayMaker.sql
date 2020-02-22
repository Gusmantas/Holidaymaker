-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.29-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for holidaymaker
CREATE DATABASE IF NOT EXISTS `holidaymaker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `holidaymaker`;

-- Dumping structure for table holidaymaker.accommodations
CREATE TABLE IF NOT EXISTS `accommodations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `location_id` int(11) unsigned NOT NULL DEFAULT '0',
  `asset_profile_id` int(11) unsigned NOT NULL DEFAULT '0',
  `extra_bed_price` double(11,2) unsigned NOT NULL,
  `half_board_price` double(11,2) unsigned NOT NULL,
  `full_board_price` double(11,2) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_accommodations_asset_profile` (`asset_profile_id`),
  KEY `FK_accommodations_locations` (`location_id`),
  CONSTRAINT `FK_accommodations_asset_profile` FOREIGN KEY (`asset_profile_id`) REFERENCES `asset_profile` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_accommodations_locations` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for view holidaymaker.accommodations_with_reviews
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `accommodations_with_reviews` (
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`street` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`extra_bed_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`half_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`full_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`pool` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`evening_events` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`child_activities` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`restaurant` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`distance_to_beach` DOUBLE(11,2) NULL,
	`distance_to_centrum` DOUBLE(11,2) NULL,
	`room_id` INT(11) UNSIGNED NOT NULL,
	`room_type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`max_persons_per_room` INT(1) UNSIGNED NOT NULL,
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`room_description` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`room_booked_checkin` DATETIME NULL,
	`room_booked_checkout` DATETIME NULL,
	`stars` DOUBLE(18,1) NULL,
	`review` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.accommodation_info
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `accommodation_info` (
	`id` INT(11) UNSIGNED NOT NULL,
	`extra_bed_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`half_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`full_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`street` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`house_number` VARCHAR(20) NULL COLLATE 'latin1_swedish_ci',
	`zip_code` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`distance_to_beach` DOUBLE(11,2) NULL,
	`distance_to_centrum` DOUBLE(11,2) NULL,
	`pool` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`evening_events` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`child_activities` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`restaurant` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.accommodation_info_and_rooms
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `accommodation_info_and_rooms` (
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`street` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`house_number` VARCHAR(20) NULL COLLATE 'latin1_swedish_ci',
	`zip_code` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`room_id` INT(11) UNSIGNED NOT NULL,
	`room_type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`room_description` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`max_persons_per_room` INT(1) UNSIGNED NOT NULL,
	`extra_bed_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`half_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`full_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`distance_to_beach` DOUBLE(11,2) NULL,
	`distance_to_centrum` DOUBLE(11,2) NULL,
	`pool` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`evening_events` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`child_activities` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`restaurant` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.accommodation_rooms
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `accommodation_rooms` (
	`room_id` INT(11) UNSIGNED NOT NULL,
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`description` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`max_persons_per_room` INT(1) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.all_rooms
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `all_rooms` (
	`id` INT(11) UNSIGNED NOT NULL,
	`room_type_id` INT(11) UNSIGNED NOT NULL,
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`guest_id` INT(11) UNSIGNED NULL,
	`checkin` DATETIME NULL,
	`checkout` DATETIME NULL,
	`room_id` INT(11) UNSIGNED NULL,
	`booking_id` INT(11) UNSIGNED NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.asset_profile
CREATE TABLE IF NOT EXISTS `asset_profile` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `pool` enum('yes','no') NOT NULL,
  `evening_events` enum('yes','no') NOT NULL,
  `child_activities` enum('yes','no') NOT NULL,
  `restaurant` enum('yes','no') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table holidaymaker.booked_rooms
CREATE TABLE IF NOT EXISTS `booked_rooms` (
  `room_id` int(11) unsigned NOT NULL,
  `booking_id` int(11) unsigned NOT NULL,
  `extra_bed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `meals` enum('half-board','full-board','none') NOT NULL,
  KEY `FK_booked_rooms_rooms` (`room_id`),
  KEY `FK_booked_rooms_bookings` (`booking_id`),
  CONSTRAINT `FK_booked_rooms_bookings` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_booked_rooms_rooms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table holidaymaker.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `guest_id` int(11) unsigned NOT NULL DEFAULT '0',
  `checkin_date` datetime NOT NULL,
  `checkout_date` datetime NOT NULL,
  `order_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bookings_guests` (`guest_id`),
  CONSTRAINT `FK_bookings_guests` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for view holidaymaker.filtered_accommodations_and_booked_rooms_info
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `filtered_accommodations_and_booked_rooms_info` (
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`street` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`extra_bed_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`half_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`full_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`pool` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`evening_events` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`child_activities` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`restaurant` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`distance_to_beach` DOUBLE(11,2) NULL,
	`distance_to_centrum` DOUBLE(11,2) NULL,
	`room_id` INT(11) UNSIGNED NOT NULL,
	`room_type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`max_persons_per_room` INT(1) UNSIGNED NOT NULL,
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`room_description` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`room_booked_chekin` DATETIME NULL,
	`room_booked_checkout` DATETIME NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.full_accommodation_and_rooms_info
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `full_accommodation_and_rooms_info` (
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`room` INT(11) UNSIGNED NOT NULL,
	`extra_bed_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`half_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`full_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`pool` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`evening_events` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`child_activities` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`restaurant` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`street` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`distance_to_beach` DOUBLE(11,2) NULL,
	`distance_to_centrum` DOUBLE(11,2) NULL,
	`room_type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`room_description` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`max_persons_per_room` INT(1) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.full_accommodation_info_with_booked_rooms
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `full_accommodation_info_with_booked_rooms` (
	`accommodation_id` INT(11) UNSIGNED NOT NULL,
	`room` INT(11) UNSIGNED NOT NULL,
	`extra_bed_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`half_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`full_board_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`pool` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`evening_events` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`child_activities` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`restaurant` ENUM('yes','no') NOT NULL COLLATE 'latin1_swedish_ci',
	`street` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`country` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`distance_to_beach` DOUBLE(11,2) NULL,
	`distance_to_centrum` DOUBLE(11,2) NULL,
	`room_type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`room_description` LONGTEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`max_persons_per_room` INT(1) UNSIGNED NOT NULL,
	`guest_id` INT(11) UNSIGNED NULL,
	`checkin` DATETIME NULL,
	`checkout` DATETIME NULL,
	`room_id` INT(11) UNSIGNED NULL,
	`booking_id` INT(11) UNSIGNED NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.guests
CREATE TABLE IF NOT EXISTS `guests` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL DEFAULT '0',
  `last_name` varchar(255) NOT NULL DEFAULT '0',
  `phone_number` varchar(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for view holidaymaker.guest_bookings
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `guest_bookings` (
	`booking_id` INT(11) UNSIGNED NOT NULL,
	`order_datetime` DATETIME NOT NULL,
	`checkin_date` DATETIME NOT NULL,
	`checkout_date` DATETIME NOT NULL,
	`type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`extra_bed` TINYINT(1) UNSIGNED NOT NULL,
	`meals` ENUM('half-board','full-board','none') NOT NULL COLLATE 'latin1_swedish_ci',
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`guest_id` INT(11) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for view holidaymaker.guest_info_and_bookings
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `guest_info_and_bookings` (
	`booking_id` INT(11) UNSIGNED NOT NULL,
	`order_datetime` DATETIME NOT NULL,
	`checkin_date` DATETIME NOT NULL,
	`checkout_date` DATETIME NOT NULL,
	`type` ENUM('single-room','double-room','suite') NOT NULL COLLATE 'latin1_swedish_ci',
	`extra_bed` TINYINT(1) UNSIGNED NOT NULL,
	`meals` ENUM('half-board','full-board','none') NOT NULL COLLATE 'latin1_swedish_ci',
	`room_price` DOUBLE(11,2) UNSIGNED NOT NULL,
	`first_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`last_name` VARCHAR(255) NOT NULL COLLATE 'latin1_swedish_ci',
	`phone_number` VARCHAR(20) NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.locations
CREATE TABLE IF NOT EXISTS `locations` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `street` varchar(255) NOT NULL,
  `house_number` varchar(20),
  `zip_code` varchar(50) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `distance_to_beach` double(11,2),
  `distance_to_centrum` double(11,2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table holidaymaker.reviews
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `accommodation_id` int(11) unsigned NOT NULL DEFAULT '0',
  `stars` enum('1','2','3','4','5') NOT NULL,
  `review` longtext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_reviews_accommodations` (`accommodation_id`),
  CONSTRAINT `FK_reviews_accommodations` FOREIGN KEY (`accommodation_id`) REFERENCES `accommodations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table holidaymaker.rooms
CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `room_type_id` int(11) unsigned NOT NULL DEFAULT '0',
  `accommodation_id` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_rooms_room_types` (`room_type_id`),
  KEY `FK_rooms_accommodations` (`accommodation_id`),
  CONSTRAINT `FK_rooms_accommodations` FOREIGN KEY (`accommodation_id`) REFERENCES `accommodations` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_rooms_room_types` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for view holidaymaker.rooms_in_bookings
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `rooms_in_bookings` (
	`guest_id` INT(11) UNSIGNED NOT NULL,
	`checkin` DATETIME NOT NULL,
	`checkout` DATETIME NOT NULL,
	`room_id` INT(11) UNSIGNED NOT NULL,
	`booking_id` INT(11) UNSIGNED NOT NULL
) ENGINE=MyISAM;

-- Dumping structure for table holidaymaker.room_types
CREATE TABLE IF NOT EXISTS `room_types` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` enum('single-room','double-room','suite') NOT NULL,
  `description` longtext NOT NULL,
  `room_price` double(11,2) unsigned NOT NULL DEFAULT '0.00',
  `max_persons_per_room` int(1) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for view holidaymaker.accommodations_with_reviews
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `accommodations_with_reviews`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `accommodations_with_reviews` AS select `b`.`accommodation_id` AS `accommodation_id`,`b`.`street` AS `street`,`b`.`city` AS `city`,`b`.`country` AS `country`,`b`.`extra_bed_price` AS `extra_bed_price`,`b`.`half_board_price` AS `half_board_price`,`b`.`full_board_price` AS `full_board_price`,`b`.`pool` AS `pool`,`b`.`evening_events` AS `evening_events`,`b`.`child_activities` AS `child_activities`,`b`.`restaurant` AS `restaurant`,`b`.`distance_to_beach` AS `distance_to_beach`,`b`.`distance_to_centrum` AS `distance_to_centrum`,`b`.`room_id` AS `room_id`,`b`.`room_type` AS `room_type`,`b`.`max_persons_per_room` AS `max_persons_per_room`,`b`.`room_price` AS `room_price`,`b`.`room_description` AS `room_description`,`b`.`room_booked_chekin` AS `room_booked_checkin`,`b`.`room_booked_checkout` AS `room_booked_checkout`,round(avg(`reviews`.`stars`),1) AS `stars`,`reviews`.`review` AS `review` from (`filtered_accommodations_and_booked_rooms_info` `b` join `reviews`) where (`b`.`accommodation_id` = `reviews`.`accommodation_id`) group by `b`.`room_id`;

-- Dumping structure for view holidaymaker.accommodation_info
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `accommodation_info`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `accommodation_info` AS select `accommodations`.`id` AS `id`,`accommodations`.`extra_bed_price` AS `extra_bed_price`,`accommodations`.`half_board_price` AS `half_board_price`,`accommodations`.`full_board_price` AS `full_board_price`,`locations`.`street` AS `street`,`locations`.`house_number` AS `house_number`,`locations`.`zip_code` AS `zip_code`,`locations`.`city` AS `city`,`locations`.`country` AS `country`,`locations`.`distance_to_beach` AS `distance_to_beach`,`locations`.`distance_to_centrum` AS `distance_to_centrum`,`asset_profile`.`pool` AS `pool`,`asset_profile`.`evening_events` AS `evening_events`,`asset_profile`.`child_activities` AS `child_activities`,`asset_profile`.`restaurant` AS `restaurant` from ((`accommodations` join `locations`) join `asset_profile`) where ((`accommodations`.`location_id` = `locations`.`id`) and (`accommodations`.`asset_profile_id` = `asset_profile`.`id`));

-- Dumping structure for view holidaymaker.accommodation_info_and_rooms
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `accommodation_info_and_rooms`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `accommodation_info_and_rooms` AS select `accommodation_rooms`.`accommodation_id` AS `accommodation_id`,`accommodation_info`.`street` AS `street`,`accommodation_info`.`house_number` AS `house_number`,`accommodation_info`.`zip_code` AS `zip_code`,`accommodation_info`.`city` AS `city`,`accommodation_info`.`country` AS `country`,`accommodation_rooms`.`room_id` AS `room_id`,`accommodation_rooms`.`type` AS `room_type`,`accommodation_rooms`.`description` AS `room_description`,`accommodation_rooms`.`room_price` AS `room_price`,`accommodation_rooms`.`max_persons_per_room` AS `max_persons_per_room`,`accommodation_info`.`extra_bed_price` AS `extra_bed_price`,`accommodation_info`.`half_board_price` AS `half_board_price`,`accommodation_info`.`full_board_price` AS `full_board_price`,`accommodation_info`.`distance_to_beach` AS `distance_to_beach`,`accommodation_info`.`distance_to_centrum` AS `distance_to_centrum`,`accommodation_info`.`pool` AS `pool`,`accommodation_info`.`evening_events` AS `evening_events`,`accommodation_info`.`child_activities` AS `child_activities`,`accommodation_info`.`restaurant` AS `restaurant` from (`accommodation_info` join `accommodation_rooms`) where (`accommodation_info`.`id` = `accommodation_rooms`.`accommodation_id`);

-- Dumping structure for view holidaymaker.accommodation_rooms
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `accommodation_rooms`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `accommodation_rooms` AS select `rooms`.`id` AS `room_id`,`rooms`.`accommodation_id` AS `accommodation_id`,`room_types`.`type` AS `type`,`room_types`.`description` AS `description`,`room_types`.`room_price` AS `room_price`,`room_types`.`max_persons_per_room` AS `max_persons_per_room` from (`rooms` join `room_types`) where (`rooms`.`room_type_id` = `room_types`.`id`);

-- Dumping structure for view holidaymaker.all_rooms
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `all_rooms`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `all_rooms` AS select `rooms`.`id` AS `id`,`rooms`.`room_type_id` AS `room_type_id`,`rooms`.`accommodation_id` AS `accommodation_id`,`rooms_in_bookings`.`guest_id` AS `guest_id`,`rooms_in_bookings`.`checkin` AS `checkin`,`rooms_in_bookings`.`checkout` AS `checkout`,`rooms_in_bookings`.`room_id` AS `room_id`,`rooms_in_bookings`.`booking_id` AS `booking_id` from (`rooms` left join `rooms_in_bookings` on((`rooms`.`id` = `rooms_in_bookings`.`room_id`)));

-- Dumping structure for view holidaymaker.filtered_accommodations_and_booked_rooms_info
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `filtered_accommodations_and_booked_rooms_info`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `filtered_accommodations_and_booked_rooms_info` AS select `full_accommodation_info_with_booked_rooms`.`accommodation_id` AS `accommodation_id`,`full_accommodation_info_with_booked_rooms`.`street` AS `street`,`full_accommodation_info_with_booked_rooms`.`city` AS `city`,`full_accommodation_info_with_booked_rooms`.`country` AS `country`,`full_accommodation_info_with_booked_rooms`.`extra_bed_price` AS `extra_bed_price`,`full_accommodation_info_with_booked_rooms`.`half_board_price` AS `half_board_price`,`full_accommodation_info_with_booked_rooms`.`full_board_price` AS `full_board_price`,`full_accommodation_info_with_booked_rooms`.`pool` AS `pool`,`full_accommodation_info_with_booked_rooms`.`evening_events` AS `evening_events`,`full_accommodation_info_with_booked_rooms`.`child_activities` AS `child_activities`,`full_accommodation_info_with_booked_rooms`.`restaurant` AS `restaurant`,`full_accommodation_info_with_booked_rooms`.`distance_to_beach` AS `distance_to_beach`,`full_accommodation_info_with_booked_rooms`.`distance_to_centrum` AS `distance_to_centrum`,`full_accommodation_info_with_booked_rooms`.`room` AS `room_id`,`full_accommodation_info_with_booked_rooms`.`room_type` AS `room_type`,`full_accommodation_info_with_booked_rooms`.`max_persons_per_room` AS `max_persons_per_room`,`full_accommodation_info_with_booked_rooms`.`room_price` AS `room_price`,`full_accommodation_info_with_booked_rooms`.`room_description` AS `room_description`,`full_accommodation_info_with_booked_rooms`.`checkin` AS `room_booked_chekin`,`full_accommodation_info_with_booked_rooms`.`checkout` AS `room_booked_checkout` from `full_accommodation_info_with_booked_rooms`;

-- Dumping structure for view holidaymaker.full_accommodation_and_rooms_info
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `full_accommodation_and_rooms_info`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `full_accommodation_and_rooms_info` AS select distinct `accommodations`.`id` AS `accommodation_id`,`rooms`.`id` AS `room`,`accommodations`.`extra_bed_price` AS `extra_bed_price`,`accommodations`.`half_board_price` AS `half_board_price`,`accommodations`.`full_board_price` AS `full_board_price`,`asset_profile`.`pool` AS `pool`,`asset_profile`.`evening_events` AS `evening_events`,`asset_profile`.`child_activities` AS `child_activities`,`asset_profile`.`restaurant` AS `restaurant`,`locations`.`street` AS `street`,`locations`.`city` AS `city`,`locations`.`country` AS `country`,`locations`.`distance_to_beach` AS `distance_to_beach`,`locations`.`distance_to_centrum` AS `distance_to_centrum`,`room_types`.`type` AS `room_type`,`room_types`.`description` AS `room_description`,`room_types`.`room_price` AS `room_price`,`room_types`.`max_persons_per_room` AS `max_persons_per_room` from ((((`accommodations` join `asset_profile`) join `locations`) join `room_types`) join `rooms`) where ((`accommodations`.`asset_profile_id` = `asset_profile`.`id`) and (`accommodations`.`location_id` = `locations`.`id`) and (`rooms`.`room_type_id` = `room_types`.`id`) and (`rooms`.`accommodation_id` = `accommodations`.`id`));

-- Dumping structure for view holidaymaker.full_accommodation_info_with_booked_rooms
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `full_accommodation_info_with_booked_rooms`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `full_accommodation_info_with_booked_rooms` AS select `full_accommodation_and_rooms_info`.`accommodation_id` AS `accommodation_id`,`full_accommodation_and_rooms_info`.`room` AS `room`,`full_accommodation_and_rooms_info`.`extra_bed_price` AS `extra_bed_price`,`full_accommodation_and_rooms_info`.`half_board_price` AS `half_board_price`,`full_accommodation_and_rooms_info`.`full_board_price` AS `full_board_price`,`full_accommodation_and_rooms_info`.`pool` AS `pool`,`full_accommodation_and_rooms_info`.`evening_events` AS `evening_events`,`full_accommodation_and_rooms_info`.`child_activities` AS `child_activities`,`full_accommodation_and_rooms_info`.`restaurant` AS `restaurant`,`full_accommodation_and_rooms_info`.`street` AS `street`,`full_accommodation_and_rooms_info`.`city` AS `city`,`full_accommodation_and_rooms_info`.`country` AS `country`,`full_accommodation_and_rooms_info`.`distance_to_beach` AS `distance_to_beach`,`full_accommodation_and_rooms_info`.`distance_to_centrum` AS `distance_to_centrum`,`full_accommodation_and_rooms_info`.`room_type` AS `room_type`,`full_accommodation_and_rooms_info`.`room_description` AS `room_description`,`full_accommodation_and_rooms_info`.`room_price` AS `room_price`,`full_accommodation_and_rooms_info`.`max_persons_per_room` AS `max_persons_per_room`,`rooms_in_bookings`.`guest_id` AS `guest_id`,`rooms_in_bookings`.`checkin` AS `checkin`,`rooms_in_bookings`.`checkout` AS `checkout`,`rooms_in_bookings`.`room_id` AS `room_id`,`rooms_in_bookings`.`booking_id` AS `booking_id` from (`full_accommodation_and_rooms_info` left join `rooms_in_bookings` on((`rooms_in_bookings`.`room_id` = `full_accommodation_and_rooms_info`.`room`)));

-- Dumping structure for view holidaymaker.guest_bookings
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `guest_bookings`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `guest_bookings` AS select `booked_rooms`.`booking_id` AS `booking_id`,`bookings`.`order_datetime` AS `order_datetime`,`bookings`.`checkin_date` AS `checkin_date`,`bookings`.`checkout_date` AS `checkout_date`,`room_types`.`type` AS `type`,`booked_rooms`.`extra_bed` AS `extra_bed`,`booked_rooms`.`meals` AS `meals`,`room_types`.`room_price` AS `room_price`,`bookings`.`guest_id` AS `guest_id` from (((`bookings` join `booked_rooms`) join `rooms`) join `room_types`) where ((`booked_rooms`.`booking_id` = `bookings`.`id`) and (`booked_rooms`.`room_id` = `rooms`.`id`) and (`rooms`.`room_type_id` = `room_types`.`id`));

-- Dumping structure for view holidaymaker.guest_info_and_bookings
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `guest_info_and_bookings`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `guest_info_and_bookings` AS select `guest_bookings`.`booking_id` AS `booking_id`,`guest_bookings`.`order_datetime` AS `order_datetime`,`guest_bookings`.`checkin_date` AS `checkin_date`,`guest_bookings`.`checkout_date` AS `checkout_date`,`guest_bookings`.`type` AS `type`,`guest_bookings`.`extra_bed` AS `extra_bed`,`guest_bookings`.`meals` AS `meals`,`guest_bookings`.`room_price` AS `room_price`,`guests`.`first_name` AS `first_name`,`guests`.`last_name` AS `last_name`,`guests`.`phone_number` AS `phone_number` from (`guest_bookings` join `guests`) where (`guest_bookings`.`guest_id` = `guests`.`id`);

-- Dumping structure for view holidaymaker.rooms_in_bookings
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `rooms_in_bookings`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `rooms_in_bookings` AS select `bookings`.`guest_id` AS `guest_id`,`bookings`.`checkin_date` AS `checkin`,`bookings`.`checkout_date` AS `checkout`,`booked_rooms`.`room_id` AS `room_id`,`booked_rooms`.`booking_id` AS `booking_id` from (`bookings` join `booked_rooms`) where (`bookings`.`id` = `booked_rooms`.`booking_id`);

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
