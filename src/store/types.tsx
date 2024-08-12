export interface Account {
    accountNumber: string;
    accountHolderName: string;
    balance: number;
}

export interface Transaction {
    id: number;
    fromAccountNumber: string;
    toAccountNumber: string;
    amount: number;
    date: string;
}