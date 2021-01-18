/* Init Manager */

INSERT INTO `profile` (`first_name`, `last_name`, `date_of_birth`, `gender`, `phone_number`) VALUES
('Manager', '001', '1980-04-04', 0, '0123456789'),
('Manager', '002', '1980-04-04', 0, '0123456789'),
('Manager', '003', '1980-04-04', 0, '0123456789'),
('Manager', '004', '1980-04-04', 0, '0123456789'),
('Manager', '005', '1980-04-04', 0, '0123456789'),
('Manager', '006', '1980-04-04', 0, '0123456789');

INSERT INTO `user` (`email`, `password`, `status`, `profile_id`) VALUES
('manager001@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 2),
('manager002@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 3),
('manager003@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 4),
('manager004@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 5),
('manager005@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 6),
('manager006@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 7);

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2);

/* Init Department & Team */

INSERT INTO `department` (`user_id`, `name`, `short_name`, `description`, `object_status`) VALUES
(2, 'Information Technology', 'IT', 'Information technology (IT) is the use of computers to store, retrieve, transmit, and manipulate data or information. IT is typically used within the context of business operations as opposed to personal or entertainment technologies.', 0),
(3, 'Sale', NULL, 'A sale is a transaction between two or more parties in which the buyer receives tangible or intangible goods, services, or assets in exchange for money.', 0),
(4, 'Human Resources', 'HR', 'Human resources (HR) is the division of a business that is charged with finding, screening, recruiting, and training job applicants, as well as administering employee-benefit programs.', 0),
(5, 'Accountant', NULL, 'Accountants use their education and experience to create or examine the accuracy of financial statements.', 0);

INSERT INTO `team` (`name`, `short_name`, `description`, `object_status`, `department_id`) VALUES
('JAVA Team', 'Java', 'Take care all Java projects.', 0, 1),
('.NET Team', '.NET', 'Take care all .NET projects.', 0, 1),
('PHP Team', 'PHP', 'Take care all PHP projects.', 0, 1),
('Python Team', 'Python', 'Take care all Python projects.', 0, 1),
('Sales development reps', NULL, 'Sales development reps (SDRs) are the traditional cold caller sales reps. They find new leads for account executives to track and are often the first point of contact a new lead has with your organization.', 0, 2),
('HR Team', NULL, 'Hire new member.', 0, 3),
('Accountant Team', NULL, 'Manage finance.', 0, 4);