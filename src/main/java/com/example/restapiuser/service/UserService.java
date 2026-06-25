package com.example.restapiuser.service;

import com.example.restapiuser.dto.UserResponse;
import com.example.restapiuser.entity.UserEntity;
import com.example.restapiuser.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // UserEntity(table, passwd포함되어 있음) -> UserResponse(화면 출력을 위한, passwd 빼고)
    public List<UserResponse> findUsers(String keyword) {
        List<UserEntity> users;
        if (keyword == null || keyword.isEmpty()) {
            // 검색조건이 없다면 모두다 검색하는데 Indate Asc으로 정렬을 해라
            users = userRepository.findAllByOrderByIndateAsc();
        } else {
            // keyword가 있으면 userid로 찾아라 대소문자 무시한 keyword.trim() 포함되어 있는, Indate Asc로 정렬해서
            users = userRepository.findByUseridContainingIgnoreCaseOrderByIndateAsc(keyword.trim());
        }
        // 가지고온 users를 stream으로 한번더 가공을 한다
        return users.stream()
                .map(UserResponse::from)    // 모든 요소에 함수를 적용시켜라(.map()), forEach의 역할을 하는데(forEach는 원본이 안 바뀜) UserResponse::from 함수를 적용해서 값 들을 바꾸어라임
                // 여기까지 하면 stream 형태임
                // 함수를 적용해서 새로운 stream을 만들어라
                .toList(); // stream 형태를 List의 형태로 바꾸어라임
                // stream() -> ArrayList로 변경
                // list.stream() -> ArrayList로 변경
    }
}
