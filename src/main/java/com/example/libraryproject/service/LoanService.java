package com.example.libraryproject.service;

import com.example.libraryproject.domain.Loan;
import com.example.libraryproject.dto.LoanDTO;
import com.example.libraryproject.mapper.LoanMapper;
import com.example.libraryproject.repository.BookRepository;
import com.example.libraryproject.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final UserService userService;

    public LoanDTO approveLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        loan.setStatus(Loan.Status.APPROVED);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    public LoanDTO rejectLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        loan.setStatus(Loan.Status.REJECTED);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    @Transactional
    public LoanDTO requestBookLoan(Long bookId, Long userId) {
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
        var createdLoan = loanRepository.save(loan);
        return loanMapper.toDTO(createdLoan);
    }

    @Transactional
    public LoanDTO returnBook(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        var book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        loan.setStatus(Loan.Status.RETURNED);
        loanRepository.save(loan);
        bookRepository.save(book);
        return loanMapper.toDTO(loan);
    }

    public LoanDTO getLoanDetails(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new IllegalArgumentException("Loan with id " + loanId + " does not exist")
        );
        return loanMapper.toDTO(loan);
    }

    public List<LoanDTO> getLoansByUser(Long userId) {
        var loans = loanRepository.findByUserId(userId);
        return loans.stream().map(loanMapper::toDTO).toList();
    }

    public List<LoanDTO> getDelayedLoans() {
        return loanRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), Loan.Status.RETURNED).stream().map(loanMapper::toDTO).toList();
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream().map(loanMapper::toDTO).toList();
    }
}
