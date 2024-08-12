import React, { useEffect, useState } from 'react';
import axios from 'axios';
import {Account} from "../../store/types";
import CreateAccountModal from "./CreateAccountModal";
import UpdateAccountModal from "./UpdateAccountModal";
import DeleteAccountModal from "./DeleteAccountModal";
import TransactionModal from "../transaction/TransactionModal";
import TransactionHistoryModal from "../transaction/TransactionHistoryModal";

function AccountPage() {
    const [account, setAccount] = useState<Account | null>(null);
    const [accountNumber, setAccountNumber] = useState<string>('');
    const [isCreateAccountModalOpen, setIsCreateAccountModalOpen] = useState<boolean>(false);
    const [isUpdateAccountModalOpen, setIsUpdateAccountModalOpen] = useState<boolean>(false);
    const [isDeleteAccountModalOpen, setIsDeleteAccountModalOpen] = useState<boolean>(false);
    const [isTransactionModalOpen, setIsTransactionModalOpen] = useState<boolean>(false);
    const [isTransactionHistoryModalOpen, setIsTransactionHistoryModalOpen] = useState<boolean>(false);

    useEffect(() => {
        if (accountNumber) {
            axios.get(`/accounts?accountNumber=${accountNumber}`)
                .then((response: { data: Account }) => {
                    setAccount(response.data);
                })
                .catch((error: any) => {
                    console.error('Error fetching account data:', error);
                });
        }
    }, [accountNumber]);

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setAccountNumber(event.target.value);
    };

    const openCreateAccountModal = () => {
        setIsCreateAccountModalOpen(true);
    };

    const closeCreateAccountModal = () => {
        setIsCreateAccountModalOpen(false);
    };

    const openUpdateAccountModal = () => {
        setIsUpdateAccountModalOpen(true);
    };

    const closeUpdateAccountModal = () => {
        setIsUpdateAccountModalOpen(false);
    };

    const openDeleteAccountModal = () => {
        setIsDeleteAccountModalOpen(true);
    };

    const closeDeleteAccountModal = () => {
        setIsDeleteAccountModalOpen(false);
    };

    const openTransactionModal = () => {
        setIsTransactionModalOpen(true);
    };

    const closeTransactionModal = () => {
        setIsTransactionModalOpen(false);
    };

    const openTransactionHistoryModal = () => {
        setIsTransactionHistoryModalOpen(true);
    };

    const closeTransactionHistoryModal = () => {
        setIsTransactionHistoryModalOpen(false);
    };

    return (
        <div>
            <h1>Account Details</h1>
            <input
                type="text"
                value={accountNumber}
                onChange={handleInputChange}
                placeholder="Enter account number"
            />
            {account && (
                <div>
                    <p>Account Number: {account.accountNumber}</p>
                    <p>Account Holder Name: {account.accountHolderName}</p>
                    <p>Balance: ${account.balance}</p>
                    <button onClick={openUpdateAccountModal}>Update Account</button>
                    <button onClick={openDeleteAccountModal}>Delete Account</button>
                    <button onClick={openTransactionModal}>Make Transaction</button>
                    <button onClick={openTransactionHistoryModal}>View Transaction History</button>
                </div>
            )}
            <button onClick={openCreateAccountModal}>Create Account</button>
            <CreateAccountModal isOpen={isCreateAccountModalOpen} onClose={closeCreateAccountModal} />
            {account && (
                <>
                    <UpdateAccountModal
                        isOpen={isUpdateAccountModalOpen}
                        onClose={closeUpdateAccountModal}
                        accountNumber={account.accountNumber}
                        currentName={account.accountHolderName}
                    />
                    <DeleteAccountModal
                        isOpen={isDeleteAccountModalOpen}
                        onClose={closeDeleteAccountModal}
                        accountNumber={account.accountNumber}
                    />
                    <TransactionModal
                        isOpen={isTransactionModalOpen}
                        onClose={closeTransactionModal}
                    />
                    <TransactionHistoryModal
                        isOpen={isTransactionHistoryModalOpen}
                        onClose={closeTransactionHistoryModal}
                        accountNumber={account.accountNumber}
                    />
                </>
            )}
        </div>
    );
}

export default AccountPage;