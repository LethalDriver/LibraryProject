package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.Review;
import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.dto.ReviewPostRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ReviewMapper {
    @Mapping(target = "reviewDate", source = "reviewDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "username", source = "user.username")
    ReviewDTO toDTO(Review review);
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "user.id", source = "userId")
    Review toEntity(ReviewPostRequest reviewPostRequest);
}
