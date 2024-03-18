import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import { Navigate } from "react-router-dom";
import Navbar from "../../components/Navbar/navbar.js";
import Sidebar from "../../components/SideBar/sidebar.js";
import Widget from "../../components/widgets/widgets.js";
import './dashboard.css';

import Issuebookform from "../../components/Form/issuebookform.js";
import Returnbookform from "../../components/Form/returnbookform.js";
import memberApi from "../../services/memberApi.js";
import bookApi from "../../services/bookApi.js";
import transactionApi from "../../services/transactionsApi.js";

const Dashboard = () => {
  const navigate = useNavigate();
  const [widgetData, setWidgetData] = useState(null);

  const [authenticated, setAuthenticated] = useState(localStorage.getItem("authenticated") ? true : false);
  
  useEffect(() => {
    if (!authenticated) {
      navigate("/login");
    }
  }, [authenticated, navigate]);

  const updateWidgetData = async() => {
    // Perform any necessary logic to update the widget data
    const updatedData = await fetchData();// For example, you can fetch updated data from an API

    // Update the widgetData state variable with the new data
    setWidgetData(updatedData);
  };
  const fetchData = async () => {
    // Replace this with your logic for fetching the updated widget data from an API
    const members = await memberApi.getAllMembers("ACTIVATED");
    const books = await bookApi.searchBooks({
      genre: null,
      available: true,
      author: null,
      isbn: null,
    });
    const issuedBooks = await transactionApi.searchTransactions({
      cardId: null,
      bookId: null,
      status: "ISSUED",
    });

    return { members: members.length, books: books.length, issuedBooks: issuedBooks.length };
  };

  if (!authenticated) {
    return <Navigate replace to="/login" />;
  } else {
    return (
      <div className="home">
        <Sidebar />
        <div className="homeContainer">
          <Navbar />
          <div className="dashboard">
            <div className="widgets">
              <Widget type="user" data={widgetData} />
              <Widget type="Books" data={widgetData} />
              <Widget type="Issued" data={widgetData} />
            </div>
            <div className="button-container">
              <Issuebookform onUpdateWidgetData={updateWidgetData} />
              <Returnbookform onUpdateWidgetData={updateWidgetData}/>
            </div>
          </div>
        </div>
      </div>
    );
  }
};

export default Dashboard;
