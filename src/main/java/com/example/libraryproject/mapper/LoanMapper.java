package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.Loan;
import com.example.libraryproject.dto.LoanDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {BookMapper.class, UserMapper.class})
public interface LoanMapper {

    @Mapping(target = "loanDate", source = "loanDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "returnDate", source = "returnDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "dueDate", source = "dueDate", dateFormat = "yyyy-MM-dd")
    LoanDTO toDTO(Loan loan);

    @Mapping(target = "loanDate", source = "loanDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "returnDate", source = "returnDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "dueDate", source = "dueDate", dateFormat = "yyyy-MM-dd")
    Loan toEntity(LoanDTO loanDTO);
}
