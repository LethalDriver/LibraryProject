package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {BookDetailsMapper.class})
public interface BookMapper {
    @Mapping(target = "bookDetails", source = "bookDetails")
    BookDTO toDto(Book book);
    @Mapping(target = "bookDetails", source = "bookDetails")
    Book toEntity(BookDTO bookDto);
}
