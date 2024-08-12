import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Transaction } from '../../store/types';

interface TransactionHistoryModalProps {
    isOpen: boolean;
    onClose: () => void;
    accountNumber: string;
}

function TransactionHistoryModal({ isOpen, onClose, accountNumber }: TransactionHistoryModalProps) {
    const [transactions, setTransactions] = useState<Transaction[]>([]);

    useEffect(() => {
        if (accountNumber && isOpen) {
            axios.get(`/transactions?accountNumber=${accountNumber}`)
                .then((response: { data: Transaction[] }) => {
                    setTransactions(response.data);
                })
                .catch((error: any) => {
                    console.error('Error fetching transaction history:', error);
                });
        }
    }, [accountNumber, isOpen]);

    if (!isOpen) {
        return null;
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Transaction History</h2>
                <table>
                    <thead>
                    <tr>
                        <th>Date</th>
                        <th>From Account</th>
                        <th>To Account</th>
                        <th>Amount</th>
                    </tr>
                    </thead>
                    <tbody>
                    {transactions.map(transaction => (
                        <tr key={transaction.id}>
                            <td>{transaction.date}</td>
                            <td>{transaction.fromAccountNumber}</td>
                            <td>{transaction.toAccountNumber}</td>
                            <td>${transaction.amount}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default TransactionHistoryModal;