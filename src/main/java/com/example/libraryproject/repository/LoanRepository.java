package com.example.libraryproject.repository;

import com.example.libraryproject.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(Loan.Status status);
    List<Loan> findByDueDateBeforeAndStatusNotIn(LocalDate date, List<Loan.Status> statuses);
    List<Loan> findByUserId(Long userId);
}
