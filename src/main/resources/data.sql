DELETE FROM _user WHERE username='admin';
INSERT INTO _user (connect,email,last_name,name,password,role,username) VALUES (false,'admin@email.com', 'admin',
                                                                                'admin','admin',0,'admin');