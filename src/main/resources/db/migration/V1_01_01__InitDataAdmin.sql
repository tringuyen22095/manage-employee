/* Init Admin account */

INSERT INTO `role` (`name`) VALUES
('CEO'),
('manager'),
('member');

INSERT INTO `profile` (`first_name`, `last_name`, `date_of_birth`, `gender`, `phone_number`) VALUES
('Tri', 'Nguyen', '1980-04-04', 0, '0123456789');

INSERT INTO `user` (`email`, `password`, `status`, `profile_id`) VALUES
('admin@gmail.com', '$2a$10$fXeu7YY/qxV2Wk8F0BMIMu7v4juS8P7NULaoM4xbAfEOohmAXzANq', 0, 1);

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1);