CREATE TABLE loan_payments (
    id UNIQUEIDENTIFIER NOT NULL
        CONSTRAINT PK_LoanPayments PRIMARY KEY
        DEFAULT NEWSEQUENTIALID(),

    loan_id UNIQUEIDENTIFIER NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    payment_date DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    notes NVARCHAR(300) NULL,
    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    updated_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),

    CONSTRAINT FK_LoanPayments_Loans
        FOREIGN KEY (loan_id) REFERENCES loans(id)
        ON DELETE CASCADE,
);

CREATE INDEX IX_LoanPayments_LoanId_PaymentDate
    ON loan_payments(loan_id, payment_date DESC);
