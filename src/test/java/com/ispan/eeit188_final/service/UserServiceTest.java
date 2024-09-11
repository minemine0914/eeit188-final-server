package com.ispan.eeit188_final.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.UserRepository;
import com.jayway.jsonpath.spi.json.JsonProvider;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserService userService;

        @Mock
        private JsonProvider jwtTokenProvider;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @SuppressWarnings("null")
        @Test
        void testFindById_UserExists() {
                UUID id = UUID.randomUUID();
                User user = new User();
                user.setId(id);
                user.setName("John Doe");
                user.setEmail("john@example.com");

                when(userRepository.findById(id)).thenReturn(Optional.of(user));

                ResponseEntity<String> response = userService.findById(id);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertTrue(response.getBody().contains("\"id\":\"" + id + "\""));
                assertTrue(response.getBody().contains("\"name\":\"John Doe\""));
                assertTrue(response.getBody().contains("\"email\":\"john@example.com\""));
        }

        @Test
        void testFindById_UserNotFound() {
                UUID id = UUID.randomUUID();
                when(userRepository.findById(id)).thenReturn(Optional.empty());

                ResponseEntity<String> response = userService.findById(id);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                assertEquals("{\"message\": \"User not found\"}", response.getBody());
        }

        @Test
        void testCreateUser_Success() {
                String jsonRequest = "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"password\":\"password123\"}";

                when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

                ResponseEntity<String> response = userService.createUser(jsonRequest);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals("{\"message\": \"Successfully created user\"}", response.getBody());
                verify(userRepository).save(any(User.class));
        }

        @Test
        void testCreateUser_InvalidInput() {
                String jsonRequest = "{\"name\":\"\",\"email\":\"john@example.com\",\"password\":\"password123\"}";

                ResponseEntity<String> response = userService.createUser(jsonRequest);

                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals("{\"message\": \"Name can't be null or empty string\"}", response.getBody());
                verify(userRepository, never()).save(any(User.class));
        }

        // @Test
        // void testLogin_Success() throws Exception {
        // String email = "john@example.com";
        // String password = "password123";
        // String jsonRequest = "{\"email\":\"" + email + "\",\"password\":\"" +
        // password + "\"}";

        // User user = new User();
        // user.setId(UUID.randomUUID());
        // user.setEmail(email);
        // user.setPassword("hashedPassword");

        // when(userRepository.findByEmail(email)).thenReturn(user);
        // when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        // when(jwtTokenProvider.).thenReturn("mockToken");

        // ResponseEntity<String> response = userService.login(jsonRequest);

        // assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertTrue(response.getBody().contains("\"token\":\"mockToken\""));
        // }

        @Test
        void testLogin_InvalidCredentials() throws Exception {
                String email = "john@example.com";
                String password = "wrongPassword";
                String jsonRequest = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";

                User user = new User();
                user.setEmail(email);
                user.setPassword("hashedPassword");

                when(userRepository.findByEmail(email)).thenReturn(user);
                when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

                ResponseEntity<String> response = userService.login(jsonRequest);

                assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
                assertEquals("{\"message\": \"Invalid password\"}", response.getBody());
        }

        @Test
        void testUpdate_Success() {
                UUID id = UUID.randomUUID();
                String jsonRequest = "{\"name\":\"John Updated\",\"email\":\"john.updated@example.com\"}";

                User existingUser = new User();
                existingUser.setId(id);
                existingUser.setName("John");
                existingUser.setEmail("john@example.com");

                when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

                ResponseEntity<String> response = userService.update(id, jsonRequest);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals("{\"message\": \"Successfully updated user\"}", response.getBody());
                verify(userRepository).save(argThat(user -> user.getName().equals("John Updated") &&
                                user.getEmail().equals("john.updated@example.com")));
        }

        @Test
        void testUpdate_UserNotFound() {
                UUID id = UUID.randomUUID();
                String jsonRequest = "{\"name\":\"John Updated\",\"email\":\"john.updated@example.com\"}";

                when(userRepository.findById(id)).thenReturn(Optional.empty());

                ResponseEntity<String> response = userService.update(id, jsonRequest);

                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                assertEquals("{\"message\": \"User not found\"}", response.getBody());
        }

        @Test
        void testDeleteById_Success() {
                UUID id = UUID.randomUUID();
                User user = new User();
                user.setId(id);

                when(userRepository.findById(id)).thenReturn(Optional.of(user));

                ResponseEntity<String> response = userService.deleteById(id);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals("{\"message\": \"User deleted successfully\"}", response.getBody());
                verify(userRepository).deleteById(id);
        }

        @Test
        void testDeleteById_UserNotFound() {
                UUID id = UUID.randomUUID();

                when(userRepository.findById(id)).thenReturn(Optional.empty());

                ResponseEntity<String> response = userService.deleteById(id);

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
                assertEquals("{\"message\": \"User not found\"}", response.getBody());
                verify(userRepository, never()).deleteById(any());
        }
}