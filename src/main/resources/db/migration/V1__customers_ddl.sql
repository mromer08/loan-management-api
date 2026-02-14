CREATE TABLE customers (
    id UNIQUEIDENTIFIER NOT NULL 
        CONSTRAINT PK_Customers PRIMARY KEY 
        DEFAULT NEWSEQUENTIALID(),

    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,

    identification_number CHAR(13) NOT NULL,
    birth_date DATE NOT NULL,

    address NVARCHAR(255) NOT NULL,
    email NVARCHAR(150) NOT NULL,
    phone CHAR(8) NOT NULL,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT UQ_Customers_Identification UNIQUE (identification_number),
    CONSTRAINT UQ_Customers_Email UNIQUE (email)
);
