import java.util.ArrayList;
// mah537
public class BooksDB{


ArrayList<Book> books;

int removedBooks;

public BooksDB(){

books = new ArrayList();

removedBooks = 0;
//bookID = new ArrayList();


}


public String getBook(int bookID){


 int thisBookID = bookID - 1;



return books.get(thisBookID) + "," + title.get(bookID);
//return author.get(bookID) + "," + title.get(bookID);
} // end get Book


public int addBook(String title, String author){

this.title.add(title);
this.author.add(author);

if(this.author.size()==this.title.size()){
    int thisBookID = this.author.size();
   // bookID.add(thisBookID); 
}


//if(this.author.size()==this.bookID.size()){
  return this.author.size() - 1;  
//}else{
//return 999999;
//}
} // int addBook



public ArrayList searchBook(String keyword){

ArrayList<Integer> booksWeFound = new ArrayList<Integer>();

for(int i = 0; i < title.size(); i++){
if(title.get(i).contains(keyword) || author.get(i).contains(keyword)){
//booksWeFound.add(author.size()-1);
    booksWeFound.add(i);
}

}

// this should return an array of integers which are the array index of the author and title arrays
//System.out.println("boks we found size: " + booksWeFound.size());
return booksWeFound;

} // end searchBook





public void removeBook(int bookID){

//int thisBook = bookID - 1;

title.set(bookID, null);
author.set(bookID, null);
removedBooks++;
//this.bookID.remove(thisBook);

} // end removeBook




public int numBooks(){
    
 // if(this.author.size()==this.title.size()){
 // System.out.println("author and title arrays match in number");
//}  
  
    int tempSize = author.size() - removedBooks;
    
return tempSize;
}  // end numBooks

} // end class