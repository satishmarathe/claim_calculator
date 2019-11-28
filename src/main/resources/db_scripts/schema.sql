CREATE TABLE IF NOT EXISTS `sales` (
  `product_code` VARCHAR(255) NOT NULL,
  `tx_date` datetime not NULL,
  `sale_amount` decimal not NULL,
  PRIMARY KEY (`product_code`,`tx_date`)
) ;

CREATE TABLE IF NOT EXISTS `merch` (
  `product_code` VARCHAR(255) NOT NULL,
  `tx_date` datetime not NULL,
  `purchase_amount` decimal not NULL,
  PRIMARY KEY (`product_code`,`tx_date`)
) ;

CREATE TABLE IF NOT EXISTS `discount_tiers` (
  `tier_id` INTEGER NOT NULL,
  `min_amount` INTEGER not NULL,
  `max_amount` INTEGER ,
  `discount_rate` FLOAT NOT null ,
  PRIMARY KEY (`tier_id`)
) ;