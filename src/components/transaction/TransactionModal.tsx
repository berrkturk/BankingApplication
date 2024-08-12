import React, { useState } from 'react';
import axios from 'axios';

interface TransactionModalProps {
    isOpen: boolean;
    onClose: () => void;
}

function TransactionModal({ isOpen, onClose }: TransactionModalProps) {
    const [fromAccount, setFromAccount] = useState<string>('');
    const [toAccount, setToAccount] = useState<string>('');
    const [amount, setAmount] = useState<number>(0);

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        axios.post('/transactions', {
            fromAccount,
            toAccount,
            amount
        })
            .then(response => {
                console.log('Transaction successful:', response.data);
                onClose();
            })
            .catch(error => {
                console.error('Error making transaction:', error);
            });
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Make a Transaction</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="fromAccount">From Account:</label>
                        <input
                            type="text"
                            id="fromAccount"
                            value={fromAccount}
                            onChange={(e) => setFromAccount(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="toAccount">To Account:</label>
                        <input
                            type="text"
                            id="toAccount"
                            value={toAccount}
                            onChange={(e) => setToAccount(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="amount">Amount:</label>
                        <input
                            type="number"
                            id="amount"
                            value={amount}
                            onChange={(e) => setAmount(Number(e.target.value))}
                            required
                        />
                    </div>
                    <button type="submit">Make Transaction</button>
                </form>
            </div>
        </div>
    );
}

export default TransactionModal;