package com.example.restapiuser.controller;

import com.example.restapiuser.dto.DeleteResponse;
import com.example.restapiuser.dto.UserCreateRequest;
import com.example.restapiuser.dto.UserResponse;
import com.example.restapiuser.entity.UserEntity;
import com.example.restapiuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController         // @Controller + @ResponseBody
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    public UserRestController (UserService userService) {
        this.userService = userService;
    }

    // GET http://localhost:8080/api/users
    // GET http://localhost:8080/api/users?keyword=user
    @GetMapping
    public List<UserResponse> list(@RequestParam(required = false) String keyword) {
        return userService.findUsers(keyword);
    }

    // 회원가입 : create : insert
    // POST http://localhost:8080/api/users
    // @RequestBody : javascript에서 넘어오는 파라미터는 json 이다 라는 의미
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @Valid @RequestBody UserCreateRequest request) {
        UserResponse response = userService.createUser(request);
        // URI location = URI.create("http://localhost:8080/api/users/admin");
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()   // http://localhost:8080/api/users/
                .path("/{userid}")      // admin
                .buildAndExpand(response.userid())  // record 는 getUserid()가 아니라 response.userid()
                .toUri();
        return ResponseEntity.created(location).body(response);
    /* ResponseEntity : 저장된 data와 location, 상태 코드를 반환해줌
    HTTP/1.1 201 Created                                // 201 : insert가 성공했다는 뜻
    Location : http://localhost:8080/api/users/admin   // .create(location)
    Content-Type : application/json
    // .body(response) : 생성된 사용자 정보를 JSON으로 응답 (user 전체를)
     */
    }

    // 회원 삭제 DELETE DELETE(SQL)
    // DELETE http://localhost:8080/api/users/test01
    @DeleteMapping("/{userid}")
    public DeleteResponse delete (@PathVariable String userid) {
        userService.deleteUser(userid);
        return new DeleteResponse(userid, true);
    }

}
