package com.example.libraryproject.service;

import com.example.libraryproject.domain.Loan;
import com.example.libraryproject.dto.LoanDTO;
import com.example.libraryproject.exception.NoBookInStockException;
import com.example.libraryproject.mapper.LoanMapper;
import com.example.libraryproject.repository.BookRepository;
import com.example.libraryproject.repository.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final BookRepository bookRepository;
    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private final UserService userService;
    @Value("${loan.duration}")
    private int loanDuration;

    public LoanDTO approveLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new EntityNotFoundException("Loan with id " + loanId + " does not exist")
        );
        loan.setStatus(Loan.Status.APPROVED);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    public LoanDTO rejectLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new EntityNotFoundException("Loan with id " + loanId + " does not exist")
        );
        loan.setStatus(Loan.Status.REJECTED);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    public LoanDTO acceptReturnedLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new EntityNotFoundException("Loan with id " + loanId + " does not exist")
        );
        if (loan.getStatus() != Loan.Status.RETURNED) {
            throw new IllegalStateException("Loan with id " + loanId + " is not in returned status");
        }
        loan.setStatus(Loan.Status.RETURNED_ACCEPTED);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    public LoanDTO rejectReturnedLoan(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new EntityNotFoundException("Loan with id " + loanId + " does not exist")
        );
        if (loan.getStatus() != Loan.Status.RETURNED) {
            throw new IllegalStateException("Loan with id " + loanId + " is not in returned status");
        }
        loan.setStatus(Loan.Status.RETURNED_REJECTED);
        loanRepository.save(loan);
        return loanMapper.toDTO(loan);
    }

    @Transactional
    public LoanDTO requestBookLoan(Long bookId, Long userId) {
        var book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + bookId + " does not exist")
        );
        if (book.getAvailableCopies() == 0) {
            throw new NoBookInStockException("Book with id " + bookId + " is not available");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
        var loan = Loan.builder()
                .book(book)
                .user(userService.getUserById(userId))
                .status(Loan.Status.PENDING_APPROVAL)
                .loanDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(loanDuration))
                .build();
        var createdLoan = loanRepository.save(loan);
        return loanMapper.toDTO(createdLoan);
    }

    @Transactional
    public LoanDTO returnBook(Long loanId) {
        var loan = loanRepository.findById(loanId).orElseThrow(
                () -> new EntityNotFoundException("Loan with id " + loanId + " does not exist")
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
                () -> new EntityNotFoundException("Loan with id " + loanId + " does not exist")
        );
        return loanMapper.toDTO(loan);
    }

    public List<LoanDTO> getLoansByUser(Long userId) {
        var loans = loanRepository.findByUserId(userId);
        return loans.stream().map(loanMapper::toDTO).toList();
    }

    public List<LoanDTO> getOverdueLoans() {
        return loanRepository.findByDueDateBeforeAndStatusNotIn(
                LocalDate.now(), List.of(Loan.Status.RETURNED, Loan.Status.RETURNED_ACCEPTED, Loan.Status.RETURNED_REJECTED)
        ).stream().map(loanMapper::toDTO).toList();
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream().map(loanMapper::toDTO).toList();
    }

    public void deleteLoan(Long loanId) {
        loanRepository.deleteById(loanId);
    }

    public LoanDTO updateLoan(LoanDTO loanDTO) {
        var updatedLoan = loanMapper.toEntity(loanDTO);
        var existingLoan = loanRepository.findById(updatedLoan
                .getId()).orElseThrow(
                () -> new EntityNotFoundException("Loan with id " + updatedLoan.getId() + " does not exist")
        );
        existingLoan.setBook(updatedLoan.getBook());
        existingLoan.setUser(updatedLoan.getUser());
        existingLoan.setLoanDate(updatedLoan.getLoanDate());
        existingLoan.setReturnDate(updatedLoan.getReturnDate());
        existingLoan.setDueDate(updatedLoan.getDueDate());
        existingLoan.setStatus(updatedLoan.getStatus());
        loanRepository.save(existingLoan);
        return loanMapper.toDTO(existingLoan);
    }
}
