import React from "react";
import Book from "./Book";
import {  gql, useQuery } from '@apollo/client';

export default ({books, onBookDelete}) => {
  
const GET_USER = gql`
  query user{
    me {
     id
      name
      email
      customerId
    } 
  }
`;

 const { data, loading, error } = useQuery(GET_USER);
  if (loading) return <p>Loading..</p>;
  if (error) return <p>ERROR: {error.message}</p>;
  if (!data) return <p>Not found</p>;


return ( <div className="bookList col-sm-6">
    
    {books.map((book, index) => (
      
      <Book book={book} key={index} onBookDelete={onBookDelete}/>
     
    ))}
   <div>User data from graphql:</div>   
  <div>{data && data.me && data.me.id }</div>
  <div>{data && data.me && data.me.name }</div>
  <div>{data && data.me && data.me.email }</div>
  </div>)
}
