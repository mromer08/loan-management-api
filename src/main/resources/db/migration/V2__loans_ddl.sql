CREATE TABLE loans (
    id UNIQUEIDENTIFIER NOT NULL
        CONSTRAINT PK_Loans PRIMARY KEY
        DEFAULT NEWSEQUENTIALID(),

    customer_id UNIQUEIDENTIFIER NOT NULL,
    loan_date DATETIME2 DEFAULT SYSDATETIME(),
    amount DECIMAL(18,2) NOT NULL,
    term_months INT NOT NULL,

    purpose NVARCHAR(200) NULL,
    annual_interest_rate DECIMAL(5,2) NULL,
    status VARCHAR(20) NOT NULL,
    total_payable DECIMAL(18,2) NULL,
    outstanding_balance DECIMAL(18,2) NULL,
    payment_status VARCHAR(20) NULL,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT FK_Loans_Customers
        FOREIGN KEY (customer_id) REFERENCES customers(id)
        ON DELETE CASCADE,

    CONSTRAINT CK_Loans_Amount CHECK (amount > 0),
    CONSTRAINT CK_Loans_Term CHECK (term_months > 0)
);

CREATE INDEX IX_Loans_Customer_Status ON loans(customer_id, status);

CREATE TABLE loan_status_history (
    id UNIQUEIDENTIFIER NOT NULL
        CONSTRAINT PK_LoanStatusHistory PRIMARY KEY
        DEFAULT NEWSEQUENTIALID(),

    loan_id UNIQUEIDENTIFIER NOT NULL,

    status VARCHAR(20) NOT NULL,
    notes NVARCHAR(200) NULL,

    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT FK_LoanStatusHistory_Loans
        FOREIGN KEY (loan_id) REFERENCES loans(id)
        ON DELETE CASCADE
);

CREATE INDEX IX_LoanStatusHistory_LoanId_CreatedAt
    ON loan_status_history(loan_id, created_at DESC);
