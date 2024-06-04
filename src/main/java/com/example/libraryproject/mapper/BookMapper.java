package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {BookDetailsMapper.class})
public interface BookMapper {
    @Mapping(target = "bookDetails", source = "bookDetails")
    BookDTO toDto(Book book);
    @Mapping(target = "bookDetails", source = "bookDetails")
    Book toEntity(BookDTO bookDto);

    @AfterMapping
    default void setBookDetails(@MappingTarget Book book) {
        book.getBookDetails().setBook(book);
    }
}
