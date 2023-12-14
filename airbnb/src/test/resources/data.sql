--userAPassword
INSERT INTO t_user (user_id, name, password, phone_num, email, email_verified)
VALUES ('1', 'userA', '$2a$10$iQhTJR1h.nNoslTjZiYkP.w9y4CaC3yMZwqIFH87SYW4yoXa/M/BC', '1234567890', 'userA@junit.com', true)
    ,('2', 'userB', '$2a$10$iQhTJR1h.nNoslTjZiYkP.w9y4CaC3yMZwqIFH87SYW4yoXa/M/BC', '1234567891', 'userB@junit.com', false);