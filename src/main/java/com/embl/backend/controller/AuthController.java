package com.embl.backend.controller;

import com.embl.backend.dto.AuthToken;
import com.embl.backend.dto.UserDto;
import com.embl.backend.entity.User;
import com.embl.backend.security.JwtTokenUtil;
import com.embl.backend.service.UserService;
import com.embl.backend.util.UrlKeys;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(value = "JWT Token", tags = "Generate Token API")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthController(final AuthenticationManager authenticationManager, final JwtTokenUtil jwtTokenUtil,
                          final UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = UrlKeys.TOKEN_GENERATE)
    @ApiOperation(value = "Generate JWT Token")
    public ResponseEntity<AuthToken> generateToken(@RequestBody final UserDto userDTO) {
        log.debug("Entered generateToken with userDto {}", userDTO);
        String password = StringUtils.defaultIfBlank(userDTO.getPassword(), StringUtils.EMPTY);
        password = StringUtils.toEncodedString(Base64.decodeBase64(password), Charsets.UTF_8);
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), password));
        final User user = userService.getUserByUsername(userDTO.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        log.debug("Token generated for user {} :", userDTO.getUsername());
        return ResponseEntity.ok(new AuthToken(user.getUsername(), token));
    }
}
