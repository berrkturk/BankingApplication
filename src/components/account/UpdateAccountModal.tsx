import React, { useState } from 'react';
import axios from 'axios';

interface UpdateAccountModalProps {
    isOpen: boolean;
    onClose: () => void;
    accountNumber: string;
    currentName: string;
}

function UpdateAccountModal({ isOpen, onClose, accountNumber, currentName }: UpdateAccountModalProps) {
    const [name, setName] = useState<string>(currentName);

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        axios.put(`/accounts/${accountNumber}`, { name })
            .then(response => {
                console.log('Account updated:', response.data);
                onClose();
            })
            .catch(error => {
                console.error('Error updating account:', error);
            });
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Update Account</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="accountName">Account Name:</label>
                        <input
                            type="text"
                            id="accountName"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit">Update Account</button>
                </form>
            </div>
        </div>
    );
}

export default UpdateAccountModal;