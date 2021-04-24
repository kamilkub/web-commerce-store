INSERT INTO category_entity (`created`, `updated`,`version`,`category_name`) VALUES (current_timestamp, current_timestamp, 0,'Watches');
INSERT INTO category_entity (`created`, `updated`,`version`,`category_name`) VALUES (current_timestamp, current_timestamp, 0,'TVs');
INSERT INTO category_entity (`created`, `updated`,`version`,`category_name`) VALUES (current_timestamp, current_timestamp, 0,'Mobiles');
INSERT INTO category_entity (`created`, `updated`,`version`,`category_name`) VALUES (current_timestamp, current_timestamp, 0,'Laptops');

INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Apple watch, really comfortable way to make your life easier within the day',
        'assets/products-images/apple_watch.png', 
        'Apple Watch 2015',
        100.00, 2, 1);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Redux watch, every elegant man should have one of those in their collection',
        'assets/products-images/indeks.jpg', 
        'Redux watch',
        150.00, 5, 1);

               INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Modern watch, specially created for young and energetic youths',
        'assets/products-images/watch2.jpg', 
        'Victorinox watch',
        173.99, 5, 1);

               INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Great watch for hard working guys',
        'assets/products-images/watch3.jpg', 
        'Leather watch',
        150.00, 5, 1);

INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Smart Television, now in a reach of your hand! Boring times will go away',
        'assets/products-images/tv.jpg', 
        'Hisense Smart TV',
        250.00, 10, 2);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Smart Television, now in a reach of your hand! Boring times will go away',
        'assets/products-images/akaitv.jpg', 
        'Akai TV',
        400.00, 20, 2);

            INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Smart Television, now in a reach of your hand! Boring times will go away',
        'assets/products-images/sam.jpg', 
        'Samsung TV',
        300.00, 15, 2);

           INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Smart Television, now in a reach of your hand! Boring times will go away',
        'assets/products-images/xiaomitv.jpg', 
        'Huawei Smart TV',
        535.33, 25, 2);

        

INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Great smartphone, with lot of features that can be at your hand',
        'assets/products-images/mobile.jpg', 
        'Samsung S9',
        160.00, 50, 3);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        '32Mpix front camera, great for people who are active on social media',
        'assets/products-images/huawei.png', 
        'Huawei',
        600.99, 120, 3);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Fast and optimize iphone 11, dont get tired with slow loading anymore, with 1.5 Ghz processor',
        'assets/products-images/iphone11.jpg', 
        'Iphone 11',
        1000.00, 5, 3);

         INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'New collections of Nokias, this time with beautiful designs',
        'assets/products-images/nokia.jpg', 
        'Nokia - new design',
        350.00, 10, 3);

INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Laptop, with great front camera, FULL HD screen and 1 GB SSD disk.',
        'assets/products-images/laptop.jpg', 
        'Dell X100-S',
        160.00, 10, 4);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Acer laptop, with 500 GB SSD disk, 8 GB ram and 2.5 Ghz processor',
        'assets/products-images/acer.jpg', 
        'Acer LMT1000',
        523.10, 53, 4);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'ZenBook, really handy and replacable laptop',
        'assets/products-images/asus zenbook.png',
        'Asus ZenBook',
        750.00, 67, 4);

        INSERT INTO product_entity (`created`, `updated`,`version`,`active`, `description`, `image_url`, `name`, `price`, `units_in_stock`, `category_id`)
VALUES (current_timestamp, current_timestamp, 0,true,
        'Vivobook version, comes with better quality of grahpics as well as  better processor',
        'assets/products-images/asusvivobook.jpg', 
        'Asus VivoBook',
        750.00, 67, 4);

/*`account_created`*/

INSERT INTO user_entity (`created`, `updated`,`version`,`email`, `is_account_enabled`, `password`, `role`, `username`)
        VALUES (current_timestamp, current_timestamp, 0,'admin@o2.pl', true, '$2y$12$XMYdR8UzGqwKfC5Q7EkImuDTR2xQwUoEXNjX.qxkwh3xotpdxOy8G', 'ROLE_ADMIN',
        'admin');

INSERT INTO cart_entity (`created`, `updated`,`version`,`cart_holder_email`) VALUES (current_timestamp, current_timestamp, 0,'user@o2.pl');
INSERT INTO user_entity (`created`, `updated`,`version`,`email`, `is_account_enabled`, `password`, `role`, `username`, `cart_entity_id`)
VALUES (current_timestamp, current_timestamp, 0,'user@o2.pl', true, '$2y$12$YS.KPX6TyXL2/TgWZEwsE.Mru2xzUPa3Cp6psAM5EH1LDA2VrD.xq', 'ROLE_USER',
        'user', 1);