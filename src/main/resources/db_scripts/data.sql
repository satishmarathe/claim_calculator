delete from sales;

INSERT INTO sales SET tx_date = '2019-07-07',product_code = 'A',sale_amount = 800;
INSERT INTO sales SET tx_date = '2019-07-10',product_code = 'A',sale_amount = 4300;
INSERT INTO sales SET tx_date = '2019-08-15',product_code = 'A',sale_amount = 900;
INSERT INTO sales SET tx_date = '2019-07-23',product_code = 'B',sale_amount = 1200;
INSERT INTO sales SET tx_date = '2018-07-02',product_code = 'A',sale_amount = 300;
INSERT INTO sales SET tx_date = '2018-07-02 18:00:00',product_code = 'A',sale_amount = 100;

delete from merch;

INSERT INTO merch SET tx_date = '2019-07-07',product_code = 'A',purchase_amount = 800;
INSERT INTO merch SET tx_date = '2019-07-10',product_code = 'A',purchase_amount = 4300;
INSERT INTO merch SET tx_date = '2019-08-15',product_code = 'A',purchase_amount = 900;
INSERT INTO merch SET tx_date = '2019-07-23',product_code = 'B',purchase_amount = 1200;
INSERT INTO merch SET tx_date = '2018-07-02',product_code = 'A',purchase_amount = 300;
INSERT INTO merch SET tx_date = '2018-07-02 18:00:00',product_code = 'A',purchase_amount = 100;


delete from discount_tiers;

INSERT INTO discount_tiers SET tier_id = 1 , min_amount = 0,max_amount = 1000,discount_rate = 2;
INSERT INTO discount_tiers SET tier_id = 2 , min_amount = 1001,max_amount = 5000,discount_rate = 5;
INSERT INTO discount_tiers SET tier_id = 3 , min_amount = 5001,discount_rate = 7.5;