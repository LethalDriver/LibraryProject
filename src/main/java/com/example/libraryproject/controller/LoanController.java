package com.example.libraryproject.controller;

import com.example.libraryproject.dto.LoanDTO;
import com.example.libraryproject.service.LoanService;
import com.example.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @Secured("ROLE_LIBRARIAN")
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable Long id) {
        var loan = loanService.getLoanDetails(id);
        return ResponseEntity.ok(loan);
    }

    @PostMapping
    public ResponseEntity<LoanDTO> requestLoan(@RequestParam Long bookId) {
        LoanDTO loanDTO = loanService.requestBookLoan(bookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanDTO);
    }

    @Secured("ROLE_LIBRARIAN")
    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@RequestBody LoanDTO loanDTO, @PathVariable String id) {
        if (!Objects.equals(loanDTO.id(), Long.parseLong(id))) {
            throw new IllegalArgumentException("ID in path must match ID in request body");
        }
        return ResponseEntity.ok(loanService.updateLoan(loanDTO));
    }

    @Secured("ROLE_LIBRARIAN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/overdue")
    public ResponseEntity<List<LoanDTO>> getOverdueLoans() {
        return ResponseEntity.ok(loanService.getOverdueLoans());
    }

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<LoanDTO>> getCurrentUserLoans() {
        return ResponseEntity.ok(loanService.getCurrentUserLoans());
    }

    @Secured("ROLE_LIBRARIAN")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<LoanDTO> approveLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.approveLoan(id));
    }

    @Secured("ROLE_LIBRARIAN")
    @PatchMapping("/{id}/reject")
    public ResponseEntity<LoanDTO> rejectLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.rejectLoan(id));
    }

    @Secured("ROLE_LIBRARIAN")
    @PatchMapping("/{id}/acceptReturn")
    public ResponseEntity<LoanDTO> acceptReturn(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.acceptReturnedLoan(id));
    }

    @Secured("ROLE_LIBRARIAN")
    @PatchMapping("/{id}/rejectReturn")
    public ResponseEntity<LoanDTO> rejectReturn(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.rejectReturnedLoan(id));
    }
}
