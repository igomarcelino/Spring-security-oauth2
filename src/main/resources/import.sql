INSERT INTO TB_USER(user_login, user_password)VALUES('admin', '$2a$12$C0vwo.6NB7Pzb9uv4c5QzOFntMQKm3lKgBBrbTN.AH9TCHIwVrF7W');

INSERT INTO TB_ROLES(role_name)VALUES('admin');
INSERT INTO TB_ROLES(role_name)VALUES('user');

INSERT INTO TB_USER_ROLES(user_id, role_id)VALUES(1,1);