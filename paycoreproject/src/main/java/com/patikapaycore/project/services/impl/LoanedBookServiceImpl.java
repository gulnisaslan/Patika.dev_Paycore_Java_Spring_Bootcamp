package com.patikapaycore.project.services.impl;


import com.patikapaycore.project.dtos.response.LoanedBookResponseDto;
import com.patikapaycore.project.exception.NotFoundException;
import com.patikapaycore.project.models.entities.Book;
import com.patikapaycore.project.models.entities.LoanedBook;
import com.patikapaycore.project.models.entities.User;
import com.patikapaycore.project.repositories.LoanedBookRepository;
import com.patikapaycore.project.services.abstracts.BookService;
import com.patikapaycore.project.services.abstracts.LoanedBookService;
import com.patikapaycore.project.services.abstracts.UserService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;



@Service
public class LoanedBookServiceImpl implements LoanedBookService {
    private final LoanedBookRepository loanedBookRepository;
    private final BookService bookService;
    private  final UserService userService;
    

    public LoanedBookServiceImpl(BookService bookService,LoanedBookRepository loanedBookRepository, UserService userService) {
        this.loanedBookRepository = loanedBookRepository;
        this.userService = userService;
        this.bookService= bookService;
    }

    @Override
    public List<LoanedBook> getAllLoanedBooks() {
        return this.loanedBookRepository.findAll();

    }

    @Override
    public LoanedBookResponseDto getByLoanedBookId(Integer id) {
        LoanedBook byId = this.loanedBookRepository.getById(id);
        return  loanedBookResponseDto(byId) ;
    }

    @Override
    public LoanedBook addLoanedBook(LoanedBook loanedBook) {


        User byUserId = this.userService.getByUserId1(loanedBook.getUser().getId());

        List<Book> books=loanedBook.getBooks().stream().
        map(book -> bookService.getByBookId1(book.getId())).collect(Collectors.toList());
                               
   
        if(books.size()>3){
            throw new NotFoundException("En fazla üç kitap ekleyebilirsiniz");
        }
                                
        
       LoanedBook build = LoanedBook.builder()
               .books(books)
                .user(byUserId)
                .loanedDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(30))

        .build();
      return loanedBookRepository.save(build);
        
      





    }

    // ToDo: Update method was refactoring
    @Override
    public void updateLoanedBook(LoanedBook loanedBook) {
        this.loanedBookRepository.save(loanedBook);
    }

    @Override
    public boolean deleteLoanedBook(Integer id) {
        LoanedBook byId = this.loanedBookRepository.getById(id);
      this.loanedBookRepository.delete(byId);
      return true;
    }
    
    public  LoanedBookResponseDto loanedBookResponseDto(LoanedBook loanedBook){
        
        return LoanedBookResponseDto.builder()
                .userFullName(loanedBook.getUser().getFirstname()+" "+loanedBook.getUser().getSurname())
                .books(loanedBook.getBooks())
              
                .loanedDate(loanedBook.getLoanedDate())
                .loanedDate(loanedBook.getReturnDate())
                .build();
    }

}

