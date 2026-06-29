package com.example.restapiuser.service;

import com.example.restapiuser.dto.UserCreateRequest;
import com.example.restapiuser.dto.UserResponse;
import com.example.restapiuser.dto.UserUpdateRequest;
import com.example.restapiuser.entity.UserEntity;
import com.example.restapiuser.exception.ApiException;
import com.example.restapiuser.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    // 회원추가
    @Transactional
    public UserResponse createUser(@Valid UserCreateRequest request) {
        if(userRepository.existsById(request.userid())) {
            throw new ApiException(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다" + request.userid());
        }
        UserEntity user = new UserEntity(
                request.userid(),
                request.passwd(),
                request.username(),
                request.email()
        );
        UserEntity savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    // 회원삭제
    @Transactional
    public void deleteUser(String userid) {
        UserEntity user = getUserEntity(userid);
        userRepository.delete(user); // 삭제
    }

    // userid로 검색
    private UserEntity getUserEntity(String userid) {
        return userRepository.findById(userid)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다" + userid )
        );
    }

    @Transactional
    public UserResponse updateUser(String userid, @Valid UserUpdateRequest request) {
        UserEntity user = getUserEntity(userid);
        if (request.passwd() != null && !request.passwd().isBlank()) {
            user.setPasswd(request.passwd());
        }
        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }
        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }

        return UserResponse.from(userRepository.save(user));
    }
}
