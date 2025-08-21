package com.jspark.persistence;

import com.jspark.Entity.UserEntity;
import com.jspark.domain.User;
import org.mapstruct.Mapper;

/**
 * Domain 객체(User)와 Persistence 객체(UserEntity)를 서로 변환하는 매퍼 클래스.
 */
@Mapper(componentModel = "spring")
public interface  UserMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);
}