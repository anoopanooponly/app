import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";
import {allBooks} from '../modules/books';
import {  gql, useQuery } from '@apollo/client';



export default function BookDetails({match}) {

  const dispatch = useDispatch();
  const books = useSelector((state) => state.books);

  const book = books.find(book => book.id === parseInt(match.params.bookId, 10));

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



  React.useEffect(() => {
    dispatch(allBooks())
  }, [dispatch]);

  const { data, loading, error } = useQuery(GET_USER);
  if (loading) return <p>Loading..</p>;
  if (error) return <p>ERROR: {error.message}</p>;
  if (!data) return <p>Not found</p>;

  return book ? (
    
    <div className="bookDetails row">
      <h1>Details for Book ID {book.id}</h1>
      <hr/>
      <h3>Author</h3>
      <p className="lead">{book.title}</p>
      <h3>Title</h3>
      <p className="lead">{book.author}</p>
      <hr/>
      <p>
        <Link to="/">&laquo; back to list</Link>
      </p>

      <div>User data from graphql:</div>   
  <div>{data && data.me && data.me.id }</div>
  <div>{data && data.me && data.me.name }</div>
  <div>{data && data.me && data.me.email }</div>
    </div>
  ) : null
}
