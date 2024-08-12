import React from 'react';
import axios from 'axios';

interface DeleteAccountModalProps {
    isOpen: boolean;
    onClose: () => void;
    accountNumber: string;
}

function DeleteAccountModal({ isOpen, onClose, accountNumber }: DeleteAccountModalProps) {
    const handleDelete = () => {
        axios.delete(`/accounts/${accountNumber}`)
            .then(response => {
                console.log('Account deleted:', response.data);
                onClose();
            })
            .catch(error => {
                console.error('Error deleting account:', error);
            });
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div className="modal">
            <div className="modal-content">
                <span className="close" onClick={onClose}>&times;</span>
                <h2>Delete Account</h2>
                <p>Are you sure you want to delete this account?</p>
                <button onClick={handleDelete}>Delete</button>
                <button onClick={onClose}>Cancel</button>
            </div>
        </div>
    );
}

export default DeleteAccountModal;