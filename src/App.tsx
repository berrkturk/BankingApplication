import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/user/Login';
import AccountPage from "./components/account/AccountPage";
import TransactionHistory from "./components/transaction/TransactionHistory";

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/account" element={<AccountPage />} />
          <Route path="/transactions" element={<TransactionHistory />} />
        </Routes>
      </Router>
  );
}

export default App;
