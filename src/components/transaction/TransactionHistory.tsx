import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Transaction} from "../../store/types";

function TransactionHistory() {
    const [transactions, setTransactions] = useState<Transaction[]>([]);
    const [accountNumber, setAccountNumber] = useState<string>('');

    useEffect(() => {
        if (accountNumber) {
            axios.get(`/transactions?accountNumber=${accountNumber}`)
                .then((response: { data: Transaction[] }) => {
                    setTransactions(response.data);
                })
                .catch((error: any) => {
                    console.error('Error fetching transaction history:', error);
                });
        }
    }, [accountNumber]);

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setAccountNumber(event.target.value);
    };

    return (
        <div>
            <h1>Transaction History</h1>
            <input
                type="text"
                value={accountNumber}
                onChange={handleInputChange}
                placeholder="Enter account number"
            />
            <ul>
                {transactions.map(transaction => (
                    <li key={transaction.id}>
                        {transaction.date}: {transaction.fromAccountNumber} &rarr;
                        {transaction.toAccountNumber} : ${transaction.amount}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default TransactionHistory;