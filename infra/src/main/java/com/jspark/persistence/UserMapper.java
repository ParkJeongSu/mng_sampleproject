package com.jspark.persistence;

import com.jspark.Entity.UserEntity;
import com.jspark.domain.User;
import org.springframework.stereotype.Component;

/**
 * Domain 객체(User)와 Persistence 객체(UserEntity)를 서로 변환하는 매퍼 클래스.
 */
@Component
public class UserMapper {

    /**
     * User Entity를 User Domain 객체로 변환합니다.
     * @param entity DB에서 조회한 UserEntity 객체
     * @return User 도메인 객체
     */
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(entity.getId(), entity.getUsername(), entity.getEmail());
    }

    /**
     * User Domain 객체를 User Entity로 변환합니다.
     * @param domain 서비스 계층에서 사용하는 User 도메인 객체
     * @return UserEntity 객체
     */
    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        // User 객체의 ID 유무에 따라 새 Entity를 만들거나 기존 Entity를 찾아 업데이트 할 수 있으나,
        // 여기서는 간단하게 새 Entity를 만드는 로직으로 작성합니다.
        return new UserEntity(domain.getId(), domain.getUsername(), domain.getEmail());
    }
}