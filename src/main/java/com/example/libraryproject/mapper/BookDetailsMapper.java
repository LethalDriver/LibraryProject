package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.BookDetails;
import com.example.libraryproject.dto.BookDetailsDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookDetailsMapper {
    BookDetailsDTO toDto(BookDetails bookDetails);
    BookDetails toEntity(BookDetailsDTO bookDetailsDTO);
}
