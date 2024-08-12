import React, { useState } from 'react';
import axios from 'axios';

interface CreateAccountModalProps {
    isOpen: boolean;
    onClose: () => void;
}

function CreateAccountModal({ isOpen, onClose }: CreateAccountModalProps) {
    const [accountHolderName, setAccountHolderName] = useState<string>('');
    const [initialDeposit, setInitialDeposit] = useState<number>(0);

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        axios.post('/accounts', {
            accountHolderName,
            initialDeposit
        })
            .then(response => {
                console.log('Account created:', response.data);
                onClose();
            })
            .catch(error => {
                console.error('Error creating account:', error);
            });
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Create Account</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="accountHolderName">Account Holder Name:</label>
                        <input
                            type="text"
                            id="accountHolderName"
                            value={accountHolderName}
                            onChange={(e) => setAccountHolderName(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label htmlFor="initialDeposit">Initial Deposit:</label>
                        <input
                            type="number"
                            id="initialDeposit"
                            value={initialDeposit}
                            onChange={(e) => setInitialDeposit(Number(e.target.value))}
                            required
                        />
                    </div>
                    <button type="submit">Create Account</button>
                </form>
            </div>
        </div>
    );
}

export default CreateAccountModal;