package dev.zendal.etlsetup.controller.rest;

import dev.zendal.etlsetup.dto.request.UserRequest;
import dev.zendal.etlsetup.dto.response.UserDto;
import dev.zendal.etlsetup.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zendal.backend.configuration.WebConstants;

@RestController
@RequestMapping({
        WebConstants.FULL_PRIVATE + "/users",
        WebConstants.FULL_WEB + "/users"
})
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    void create(@Valid @RequestBody UserRequest userRequest) {
        this.userService.createUser(userRequest.getLogin(), userRequest.getPassword());
    }

    @PostMapping("/authorize")
    UserDto authorize(@Valid @RequestBody UserRequest userRequest) {
        return this.userService.getUserDto(userRequest.getLogin(), userRequest.getPassword());
    }
}
