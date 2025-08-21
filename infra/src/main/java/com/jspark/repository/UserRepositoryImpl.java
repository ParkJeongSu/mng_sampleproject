package com.jspark.repository;

import com.jspark.Entity.UserEntity;
import com.jspark.domain.User;
import com.jspark.persistence.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository의 JPA 기반 구현체.
 * 실제 DB 작업은 Spring Data JPA가 제공하는 JpaRepository에 위임합니다.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    // Spring Data JPA가 자동으로 구현해주는 JPA 리포지토리. UserEntity를 다룬다.
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        // 1. Domain -> Entity 변환
        UserEntity entity = userMapper.toEntity(user);
        // 2. JPA 리포지토리를 통해 DB에 저장
        UserEntity savedEntity = userJpaRepository.save(entity);
        // 3. 저장된 Entity -> Domain 변환 후 반환
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        // 1. JPA 리포지토리를 통해 ID로 Entity 조회
        Optional<UserEntity> entityOptional = userJpaRepository.findById(id);
        // 2. 조회된 Optional<Entity>를 Optional<Domain>으로 변환하여 반환
        return entityOptional.map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // 1. JPA 리포지토리를 통해 Email로 Entity 조회
        Optional<UserEntity> entityOptional = userJpaRepository.findByEmail(email);
        // 2. 조회된 Optional<Entity>를 Optional<Domain>으로 변환하여 반환
        return entityOptional.map(userMapper::toDomain);
    }
}

/**
 * Spring Data JPA가 UserEntity 객체를 위해 자동으로 구현할 인터페이스.
 * UserRepositoryImpl 내부에서만 사용됩니다.
 */
interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}