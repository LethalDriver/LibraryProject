package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {BookDetailsMapper.class})
public interface BookMapper {
    BookDTO toDto(Book book);
    Book toEntity(BookDTO bookDto);
}
