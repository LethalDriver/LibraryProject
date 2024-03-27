package com.example.libraryproject.service;

import com.example.libraryproject.domain.Loan;
import com.example.libraryproject.repository.BookRepository;
import com.example.libraryproject.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserService userService;

    public void approveLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        loan.setStatus(Loan.Status.APPROVED);
        loanRepository.save(loan);
    }

    public void rejectLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        loan.setStatus(Loan.Status.REJECTED);
        loanRepository.save(loan);
    }

    @Transactional
    public void requestBookLoan(Long bookId, Long userId) {
        var book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("Book with id " + bookId + " does not exist")
        );
        if (book.getAvailableCopies() == 0) {
            throw new IllegalArgumentException("Book with id " + bookId + " is not available");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        var loan = Loan.builder()
                .book(book)
                .user(userService.getUserById(userId))
                .status(Loan.Status.PENDING_APPROVAL)
                .build();
        loanRepository.save(loan);
    }

    @Transactional
    public void returnBook(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        var book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        loan.setStatus(Loan.Status.RETURNED);
        loanRepository.save(loan);
        bookRepository.save(book);
    }

    public void getLoanDetails(Long loanId) {
        loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
    }

    public void getLoansByUser(Long userId) {
        loanRepository.findByUserId(userId);
    }

    public void getDelayedLoans() {
        loanRepository.findByReturnDateBeforeAndStatusNot(LocalDate.now(), Loan.Status.RETURNED);
    }
}
