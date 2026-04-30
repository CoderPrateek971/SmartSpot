-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Apr 29, 2026 at 09:58 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart_parking`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `name`, `email`, `password`, `created_at`) VALUES
(1, 'Admin', 'admin@smartspot.com', 'admin123', '2026-04-25 00:31:48');

-- --------------------------------------------------------

--
-- Stand-in structure for view `admin_dashboard`
-- (See below for the actual view)
--
CREATE TABLE `admin_dashboard` (
`total_bookings` bigint(21)
,`total_revenue` decimal(30,2)
);

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `booking_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `slot_id` int(11) DEFAULT NULL,
  `vehicle_type_id` int(11) DEFAULT NULL,
  `vehicle_number` varchar(20) DEFAULT NULL,
  `start_time` datetime DEFAULT current_timestamp(),
  `end_time` datetime DEFAULT NULL,
  `total_hours` decimal(5,2) DEFAULT NULL,
  `total_amount` decimal(8,2) DEFAULT NULL,
  `booking_status` enum('active','completed','cancelled') DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`booking_id`, `user_id`, `slot_id`, `vehicle_type_id`, `vehicle_number`, `start_time`, `end_time`, `total_hours`, `total_amount`, `booking_status`) VALUES
(1, 1, 1, 1, 'HR26AB1234', '2026-04-26 10:00:00', '2026-04-26 14:00:00', 4.00, 80.00, 'active'),
(2, 1, 1, 1, 'HR26AB1234', '2026-04-26 10:00:00', '2026-04-26 14:00:00', 4.00, 80.00, 'completed'),
(3, 1, 2, 2, 'HR26AB1234', '2026-04-26 10:00:00', '2026-04-26 14:00:00', 4.00, 80.00, 'active'),
(5, 1, 1, 1, 'HR26AB1234', '2026-04-26 10:00:00', '2026-04-26 14:00:00', 2.00, 100.00, 'active'),
(13, 1, 7, 1, 'Hr26ac001', '2026-04-27 12:12:39', NULL, NULL, NULL, 'active'),
(14, 1, 1, 2, 'hr25ac2345', '2026-04-27 12:18:13', NULL, NULL, NULL, 'active'),
(15, 1, 1, 1, 'dfe2ccad', '2026-04-27 12:23:11', NULL, NULL, NULL, 'active'),
(16, 1, 1, 1, 'fwcvd', '2026-04-27 12:24:43', NULL, NULL, NULL, 'active'),
(17, 1, 1, 1, 'dcwcwec', '2026-04-27 12:24:51', NULL, NULL, NULL, 'active'),
(18, 1, 1, 1, 'dcdewcqw', '2026-04-27 12:26:45', NULL, NULL, NULL, 'active'),
(19, 1, 1, 1, 'dcsdc', '2026-04-27 12:28:53', NULL, NULL, NULL, 'active'),
(20, 1, 1, 1, 'Ccddcdew', '2026-04-27 12:29:01', NULL, NULL, NULL, 'active'),
(21, 1, 1, 1, 'gasrdvdsv', '2026-04-27 12:38:49', NULL, NULL, NULL, 'active'),
(22, 1, 7, 1, 'dfdsc', '2026-04-27 12:39:02', NULL, NULL, NULL, 'active'),
(23, 1, 7, 1, 'wcc', '2026-04-27 12:39:25', NULL, NULL, NULL, 'active'),
(24, 1, 8, 1, 'ge', '2026-04-27 13:02:24', NULL, NULL, NULL, 'active'),
(25, 1, 8, 1, 'fgtege', '2026-04-27 14:13:22', NULL, NULL, NULL, 'active'),
(32, 1, 1, 1, 'fvdcsd', '2026-04-27 14:19:56', NULL, NULL, NULL, 'active'),
(48, 1, 7, 1, 'dsc', '2026-04-27 18:03:20', NULL, NULL, NULL, 'active'),
(49, 1, 7, 1, 'fcsd', '2026-04-27 18:03:27', NULL, NULL, NULL, 'active'),
(50, 1, 8, 1, 'vcsdv', '2026-04-27 18:13:03', '2026-04-27 18:29:43', 1.00, 1.00, 'completed'),
(51, 1, 9, 1, 'dwdc', '2026-04-27 18:17:04', '2026-04-27 18:17:16', 1.00, 1.00, 'completed'),
(52, 1, 1, 1, 'dvds', '2026-04-27 18:24:08', '2026-04-27 18:24:27', 1.00, 1.00, 'completed'),
(53, 1, 1, 1, 'dgrf', '2026-04-27 18:25:21', '2026-04-27 18:25:32', 1.00, 1.00, 'completed'),
(54, 1, 7, 2, 'HR51BB2929', '2026-04-27 18:30:56', '2026-04-27 18:31:43', 1.00, 1.00, 'completed'),
(55, 1, 1, 1, 'JHFISE', '2026-04-27 18:33:43', '2026-04-27 18:34:22', 1.00, 1.00, 'completed'),
(56, 1, 7, 1, 'dcsdcc', '2026-04-28 12:05:20', '2026-04-28 12:05:31', 1.00, 1.00, 'completed'),
(57, 1, 1, 1, 'vvfddsv', '2026-04-28 12:05:40', '2026-04-28 12:05:48', 1.00, 1.00, 'completed'),
(58, 1, 1, 1, 'ffsdfs', '2026-04-28 14:08:00', '2026-04-28 14:08:58', 1.00, 1.00, 'completed'),
(59, 1, 1, 2, 'tufh', '2026-04-28 14:34:42', '2026-04-28 14:35:11', 1.00, 1.00, 'completed'),
(60, 1, 7, 1, 'HSHS', '2026-04-29 19:51:24', NULL, NULL, NULL, 'active'),
(61, 1, 8, 1, 'HR51BB0101', '2026-04-29 19:53:30', '2026-04-29 19:53:47', 1.00, 1.00, 'completed'),
(62, 2, 1, 1, 'BSHSY', '2026-04-29 20:05:08', '2026-04-29 20:05:13', 1.00, 0.01, 'completed'),
(63, 2, 1, 1, 'JWHA', '2026-04-29 20:05:28', '2026-04-29 20:06:01', 1.00, 0.01, 'completed'),
(64, 2, 1, 1, 'fewfqd', '2026-04-29 22:07:52', '2026-04-29 22:13:02', 1.00, 1.00, 'completed'),
(65, 2, 8, 2, '6969', '2026-04-29 23:12:35', '2026-04-29 23:13:46', 1.00, 1.00, 'completed'),
(66, 2, 1, 1, 'JSHS', '2026-04-30 00:33:03', '2026-04-30 01:22:27', 1.00, 1.00, 'completed');

-- --------------------------------------------------------

--
-- Table structure for table `customer_support`
--

CREATE TABLE `customer_support` (
  `ticket_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `subject` varchar(150) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `status` enum('open','in_progress','resolved') DEFAULT 'open',
  `created_at` datetime DEFAULT current_timestamp(),
  `resolved_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer_support`
--

INSERT INTO `customer_support` (`ticket_id`, `user_id`, `subject`, `message`, `status`, `created_at`, `resolved_by`) VALUES
(2, 1, 'Technical Support', 'dfwfdefefqfs', 'open', '2026-04-26 22:44:45', NULL),
(3, 1, 'Technical Support', 'sasdcdvsvsdvds', 'open', '2026-04-26 22:54:49', NULL),
(4, 1, 'Technical Support', 'dsv', 'open', '2026-04-27 14:35:13', NULL),
(5, 1, 'Technical Support', 'revfd', 'open', '2026-04-28 12:06:26', NULL),
(6, 1, 'Lost Item', 'vsga', 'open', '2026-04-29 20:07:34', NULL),
(7, 2, 'Lost Item', 'gsgs', 'open', '2026-04-29 20:14:37', NULL),
(8, 2, 'Technical Support', 'khtm', 'open', '2026-04-29 23:15:58', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `comments` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parking_slots`
--

CREATE TABLE `parking_slots` (
  `slot_id` int(11) NOT NULL,
  `slot_number` varchar(10) DEFAULT NULL,
  `vehicle_type_id` int(11) DEFAULT NULL,
  `status` enum('available','occupied') DEFAULT 'available',
  `parking_status` enum('open','closed') DEFAULT 'open',
  `latitude` decimal(10,6) DEFAULT NULL,
  `longitude` decimal(10,6) DEFAULT NULL,
  `last_updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_slots`
--

INSERT INTO `parking_slots` (`slot_id`, `slot_number`, `vehicle_type_id`, `status`, `parking_status`, `latitude`, `longitude`, `last_updated_by`) VALUES
(1, 'A1', NULL, 'available', 'open', 28.459500, 77.026600, NULL),
(2, 'A2', NULL, 'available', 'open', 28.459600, 77.026700, NULL),
(7, 'A3', NULL, 'occupied', 'open', 28.459700, 77.026800, NULL),
(8, 'A4', NULL, 'available', 'open', 28.459700, 77.026800, NULL),
(9, 'A5', NULL, 'available', 'open', 28.459500, 77.026600, NULL),
(10, 'A6', NULL, 'available', 'open', 28.459600, 77.026700, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `amount_paid` decimal(8,2) DEFAULT NULL,
  `payment_method` varchar(50) DEFAULT NULL,
  `payment_status` enum('pending','success','failed') DEFAULT 'pending',
  `transaction_time` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `dark_mode` tinyint(1) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `email`, `password`, `phone`, `profile_image`, `dark_mode`, `created_at`) VALUES
(1, 'riwan', 'abcd@gmail.com', '12345', '1234567891', 'dsf', 0, '2026-04-25 00:50:00'),
(2, 'riwan', 'a@gmail.com', '1', '8595599446', NULL, 0, '2026-04-29 20:00:34');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_type`
--

CREATE TABLE `vehicle_type` (
  `vehicle_type_id` int(11) NOT NULL,
  `type_name` varchar(20) DEFAULT NULL,
  `price_per_hour` decimal(6,2) DEFAULT NULL,
  `last_updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle_type`
--

INSERT INTO `vehicle_type` (`vehicle_type_id`, `type_name`, `price_per_hour`, `last_updated_by`, `updated_at`) VALUES
(1, 'Bike', 50.00, 1, '2026-04-29 23:14:55'),
(2, 'Car', 70.00, 1, '2026-04-29 23:14:55');

-- --------------------------------------------------------

--
-- Structure for view `admin_dashboard`
--
DROP TABLE IF EXISTS `admin_dashboard`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `admin_dashboard`  AS SELECT count(0) AS `total_bookings`, sum(`bookings`.`total_amount`) AS `total_revenue` FROM `bookings` WHERE `bookings`.`booking_status` = 'completed' ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `vehicle_type_id` (`vehicle_type_id`),
  ADD KEY `idx_user_booking` (`user_id`),
  ADD KEY `idx_slot_booking` (`slot_id`);

--
-- Indexes for table `customer_support`
--
ALTER TABLE `customer_support`
  ADD PRIMARY KEY (`ticket_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `resolved_by` (`resolved_by`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedback_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `booking_id` (`booking_id`);

--
-- Indexes for table `parking_slots`
--
ALTER TABLE `parking_slots`
  ADD PRIMARY KEY (`slot_id`),
  ADD UNIQUE KEY `slot_number` (`slot_number`),
  ADD KEY `vehicle_type_id` (`vehicle_type_id`),
  ADD KEY `last_updated_by` (`last_updated_by`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `idx_transaction_booking` (`booking_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  ADD PRIMARY KEY (`vehicle_type_id`),
  ADD KEY `last_updated_by` (`last_updated_by`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `customer_support`
--
ALTER TABLE `customer_support`
  MODIFY `ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `parking_slots`
--
ALTER TABLE `parking_slots`
  MODIFY `slot_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  MODIFY `vehicle_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`slot_id`) REFERENCES `parking_slots` (`slot_id`),
  ADD CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`vehicle_type_id`);

--
-- Constraints for table `customer_support`
--
ALTER TABLE `customer_support`
  ADD CONSTRAINT `customer_support_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `customer_support_ibfk_2` FOREIGN KEY (`resolved_by`) REFERENCES `admin` (`admin_id`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`);

--
-- Constraints for table `parking_slots`
--
ALTER TABLE `parking_slots`
  ADD CONSTRAINT `parking_slots_ibfk_1` FOREIGN KEY (`vehicle_type_id`) REFERENCES `vehicle_type` (`vehicle_type_id`),
  ADD CONSTRAINT `parking_slots_ibfk_2` FOREIGN KEY (`last_updated_by`) REFERENCES `admin` (`admin_id`);

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`);

--
-- Constraints for table `vehicle_type`
--
ALTER TABLE `vehicle_type`
  ADD CONSTRAINT `vehicle_type_ibfk_1` FOREIGN KEY (`last_updated_by`) REFERENCES `admin` (`admin_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
