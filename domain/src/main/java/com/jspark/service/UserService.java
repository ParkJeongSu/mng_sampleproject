package com.jspark.service;

import com.jspark.domain.User;
import com.jspark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 * '애플리케이션 서비스'라고도 부르며, 도메인 객체와 리포지토리를 사용해
 * 실제 사용자의 요청(Use Case)을 처리합니다.
 */
@Service
@RequiredArgsConstructor // final 필드에 대한 생성자를 자동으로 만들어줍니다. (DI)
public class UserService {

    private final UserRepository userRepository; // 구현체(Infra)가 아닌 인터페이스(Domain)에 의존

    /**
     * 사용자의 이름을 변경합니다.
     * @param userId 사용자의 ID
     * @param newUsername 새로운 사용자 이름
     * @return 이름이 변경된 사용자 도메인 객체
     */
    @Transactional // 이 메소드가 하나의 트랜잭션으로 동작하도록 보장합니다.
    public User changeUsername(Long userId, String newUsername) {
        // 1. Repository를 통해 Domain 객체를 가져온다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. ID: " + userId));

        // 2. Domain 객체에 작업을 위임한다. (가장 중요한 부분!)
        // 서비스가 직접 user.setUsername(..)을 호출하는 것이 아니라,
        // User 도메인 객체가 스스로 자신의 상태를 바꾸도록 메시지를 보냅니다.
        user.changeUsername(newUsername);

        // 3. Repository를 통해 변경된 Domain 객체를 저장한다.
        // @Transactional 어노테이션의 '변경 감지(Dirty Checking)' 기능 덕분에
        // 이 save 호출은 사실 생략 가능할 때도 있지만, 명시적으로 호출하는 것이 좋습니다.
        return userRepository.save(user);
    }
}