import React, { useEffect, useState } from "react";
import "./widgets.css";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import BookOutlinedIcon from "@mui/icons-material/BookOutlined";
import MonetizationOnOutlinedIcon from "@mui/icons-material/MonetizationOnOutlined";
import memberApi from "../../services/memberApi";
import bookApi from "../../services/bookApi";
import transactionsApi from "../../services/transactionsApi";

const Widget = ({ type, data }) => {
  const [member, setMember] = useState(0);
  const [books, setBooks] = useState(0);
  const [issued, setIssued] = useState(0);

  useEffect(() => {
    
    const searchM = async () => {
      try {
        const members = await memberApi.getAllMembers("ACTIVATED");
        setMember(members.length);
      } catch (e) {
        console.log(e);
      }
    };

    const searchB = async () => {
      try {
        const queryParams = {
          genre: null,
          available: true,
          author: null,
          isbn: null,
        };
        const books = await bookApi.searchBooks(queryParams);
        setBooks(books.length);
      } catch (e) {
        console.log(e);
      }
    };

    const searchIB = async () => {
      try {
        const queryParams = {
          cardId: null,
          bookId: null,
          status: "ISSUED",
        };
        const issuedBooks = await transactionsApi.searchTransactions(queryParams);
        setIssued(issuedBooks.length);
      } catch (e) {
        console.log(e);
      }
    };

    // Call the search functions
    searchM();
    searchB();
    searchIB();
  },[]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        switch (type) {
          case "user":
            //const members = await memberApi.getAllMembers("ACTIVATED");
            setMember(data.members);
            break;
          case "Books":
            // const booksData = await bookApi.searchBooks({
            //   genre: null,
            //   available: true,
            //   author: null,
            //   isbn: null,
            // });
            setBooks(data.books);
            break;
          case "Issued":
            // const issuedBooks = await transactionsApi.searchTransactions({
            //   cardId: null,
            //   bookId: null,
            //   status: "ISSUED",
            // });
            setIssued(data.issuedBooks);
            break;
          default:
            break;
        }
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, [type,data]);

  let widgetData;

  switch (type) {
    case "user":
      widgetData = {
        title: "Total Members",
        count: member,
        icon: (
          <PersonOutlinedIcon
            className="icon"
            style={{
              color: "crimson",
              backgroundColor: "rgba(255, 0, 0, 0.2)",
            }}
          />
        ),
      };
      break;
    case "Books":
      widgetData = {
        title: "Total Books",
        count: books,
        icon: (
          <BookOutlinedIcon
            className="icon"
            style={{
              backgroundColor: "rgba(218, 165, 32, 0.2)",
              color: "goldenrod",
            }}
          />
        ),
      };
      break;
    case "Issued":
      widgetData = {
        title: "Issued Books",
        count: issued,
        icon: (
          <MonetizationOnOutlinedIcon
            className="icon"
            style={{ backgroundColor: "rgba(0, 128, 0, 0.2)", color: "green" }}
          />
        ),
      };
      break;
    default:
      break;
  }

  return (
    <div className="widget">
      <div className="left">
        <span className="title">{widgetData.title}</span>
        <span className="counter">{widgetData.count}</span>
      </div>
      <div className="right">
        <div className="percentage positive">
          <KeyboardArrowUpIcon />
          {20} %
        </div>
        {widgetData.icon}
      </div>
    </div>
  );
};

export default Widget;
