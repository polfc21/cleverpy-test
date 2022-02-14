package com.cleverpy.api.controllers;

import com.cleverpy.api.dtos.TokenDTO;
import com.cleverpy.domain.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.USERS)
@Api(tags = "API Rest. User management.")
public class UserController {

    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String USERNAME = "/{username}";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = UserController.TOKEN + UserController.USERNAME)
    @ApiOperation(notes = "Login user with username passed by path", value = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Response created if the token was created"),
            @ApiResponse(code = 404, message = "Response not found if the username doesn't exists")
    })
    public ResponseEntity<TokenDTO> login(@PathVariable String username) {
        TokenDTO tokenDTO = new TokenDTO(this.userService.login(username));
        return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
    }

}
