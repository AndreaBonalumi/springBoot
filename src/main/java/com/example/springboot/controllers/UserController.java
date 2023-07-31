package com.example.springboot.controllers;

import com.example.springboot.dto.BookingDTO;
import com.example.springboot.dto.PasswordRequest;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.dto.mapper.UserMapper;
import com.example.springboot.dto.security.AuthenticationRequest;
import com.example.springboot.dto.security.AuthenticationResponse;
import com.example.springboot.exceptions.ItemNotFoundException;
import com.example.springboot.exceptions.NoAuthException;
import com.example.springboot.security.config.JwtUtils;
import com.example.springboot.services.BookingService;
import com.example.springboot.services.UserService;
import com.example.springboot.services.impl.JpaUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BookingService bookingService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtils jwtUtils;
    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> allCars() {
        return userService.getAll();
    }
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable("id") long id,
                                @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException {

        UserDTO userResponse = userService.getById(id);

        checkAuthorities(userResponse.getUsername(), userDetails);

        return userResponse;


    }
    @GetMapping("detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDTO> getBookingsByUser (@PathVariable("id") long id,
                                               @AuthenticationPrincipal UserDetails userDetails)
            throws ItemNotFoundException {

        UserDTO userRequest = userService.getRequestFromIdResponse(id);

        checkAuthorities(userRequest.getUsername(), userDetails);

        return bookingService.getByUser(userMapper.userDTOToUser(userRequest));

    }
    @GetMapping("user")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getByUser(@AuthenticationPrincipal UserDetails userDetails) throws ItemNotFoundException {
        return userService.getByUsername(userDetails.getUsername());
    }

    @PostMapping(value = "insert")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO insertUser(@Valid @RequestBody UserDTO request,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        checkAuthorities(request.getUsername(), userDetails);

        return userService.insUser(request);


    }
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") long id) throws ItemNotFoundException {
        userService.delUser(id);
    }

    @PostMapping("newPassword")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody PasswordRequest passwordRequest,
                               @AuthenticationPrincipal UserDetails userDetails) {

        if (userService.checkAuth(userDetails.getUsername(), passwordRequest.getOldPassword())) {
            UserDTO userRequest = userService.getRequestFromIdResponse(
                    userService.getByUsername(userDetails.getUsername()).getIdUser());
            userRequest.setPassword(passwordRequest.getNewPassword());
            userService.insUser(userRequest);
        } else {
            throw new NoAuthException("la vecchia password non corrisponde");
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword(),
                            new ArrayList<>()));
            final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getUsername());
            if (user != null) {
                String jwt = jwtUtils.generateToken(user);
                return ResponseEntity.ok(AuthenticationResponse.builder()
                        .jwt(jwt)
                        .build());
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }
    @PostMapping("/upload/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    private void saveImageToFileSystem(@RequestParam MultipartFile file,
                                       @PathVariable("idUser") long id,
                                       @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        UserDTO userRequest = userService.getRequestFromIdResponse(id);

        checkAuthorities(userRequest.getUsername(), userDetails);

        String uploadDir = "C:/Users/Si2001/Desktop/file";
        String filePath = uploadDir + "/" + fileName;

        file.transferTo(new File(filePath));

        userRequest.setProfilePhoto(filePath);
        userService.insUser(userRequest);
    }

    @GetMapping(value = "image/{idUser}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Resource getImage(@PathVariable("idUser") long id,
                             @AuthenticationPrincipal UserDetails userDetails) throws MalformedURLException {

        UserDTO userResponse = userService.getById(id);

        checkAuthorities(userResponse.getUsername(), userDetails);

        Path path = Paths.get(userResponse.getProfilePhoto());

        Resource resource = new UrlResource(path.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        }
        return null;
    }

    @PostMapping("checkUser")
    @ResponseStatus(HttpStatus.OK)
    public void checkUser(@RequestBody AuthenticationRequest auth,
                          @AuthenticationPrincipal UserDetails userDetails) {

        checkAuthorities(auth.getUsername(), userDetails);

        userService.checkUser(auth.getUsername(), auth.getPassword());
    }

    private void checkAuthorities(String username,
                                  UserDetails userDetails) throws NoAuthException {
        if (!(userDetails.getAuthorities().contains(
                new SimpleGrantedAuthority("ROLE_ADMIN")) || userDetails.getUsername().equals(username))) {
            throw new NoAuthException("non possiedi le autorizzazioni per effettuare questa operazione, riautenticati");
        }

    }
}
