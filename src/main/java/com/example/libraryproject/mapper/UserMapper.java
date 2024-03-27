package com.example.libraryproject.mapper;

import com.example.libraryproject.domain.User;
import com.example.libraryproject.dto.UserDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
