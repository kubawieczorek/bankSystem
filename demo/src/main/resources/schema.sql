CREATE TABLE Role (
    roleId INTEGER NOT NULL AUTO_INCREMENT,
    roleName VARCHAR(64) NOT NULL,
    PRIMARY KEY (roleId)
);

CREATE TABLE Client (
    clientId INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY (clientId)
);

CREATE TABLE ClientRole (
    clientId INTEGER NOT NULL,
    roleId INTEGER NOT NULL,
    CONSTRAINT PK_ClientRole PRIMARY KEY (clientId, roleId),
    FOREIGN KEY (clientId) REFERENCES Client(clientId),
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);

CREATE TABLE Account (
    accountId INTEGER NOT NULL AUTO_INCREMENT,
    money INTEGER NOT NULL,
    clientId INTEGER NOT NULL,
    dateCreated DATE NOT NULL,
    type VARCHAR(64) NOT NULL,
    number VARCHAR(64) NOT NULL,
    FOREIGN KEY (clientId) REFERENCES Client(clientId),
    PRIMARY KEY (accountId)
);

CREATE TABLE oauth_access_token (
    token_id VARCHAR(255),
    token LONGVARBINARY,
    authentication_id VARCHAR(255),
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication LONGVARBINARY,
    refresh_token VARCHAR(255),
    PRIMARY KEY(authentication_id)
);

CREATE TABLE oauth_refresh_token (
    token_id VARCHAR(255),
    token LONGVARBINARY,
    authentication LONGVARBINARY
);
