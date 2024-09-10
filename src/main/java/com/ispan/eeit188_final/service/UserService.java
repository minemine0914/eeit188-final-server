package com.ispan.eeit188_final.service;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public ResponseEntity<String> findById(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject()
                            .put("id", user.getId())
                            .put("name", user.getName())
                            .put("role", user.getRole())
                            .put("gender", user.getGender())
                            .put("birthday", user.getBirthday())
                            .put("phone", user.getPhone())
                            .put("mobilePhone", user.getMobilePhone())
                            .put("address", user.getAddress())
                            .put("email", user.getEmail())
                            .put("about", user.getAbout())
                            .put("createdAt", user.getCreatedAt())
                            .put("updatedAt", user.getUpdatedAt())
                            .put("avatarBase64", user.getAvatarBase64());

                    return ResponseEntity.ok(obj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error creating JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"User not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);

        try {
            JSONArray usersArray = new JSONArray();

            for (User user : users.getContent()) {
                JSONObject obj = new JSONObject()
                        .put("id", user.getId())
                        .put("name", user.getName())
                        .put("role", user.getRole())
                        .put("gender", user.getGender())
                        .put("birthday", user.getBirthday())
                        .put("phone", user.getPhone())
                        .put("mobilePhone", user.getMobilePhone())
                        .put("address", user.getAddress())
                        .put("email", user.getEmail())
                        .put("about", user.getAbout())
                        .put("createdAt", user.getCreatedAt())
                        .put("updatedAt", user.getUpdatedAt())
                        .put("avatarBase64", user.getAvatarBase64());

                usersArray.put(obj);
            }

            JSONObject response = new JSONObject()
                    .put("users", usersArray);

            return ResponseEntity.ok(response.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Error creating JSON response: " + e.getMessage() + "\"}");
        }
    }

    public ResponseEntity<String> createUser(String jsonRequest) {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(jsonRequest);

                String name = obj.isNull("name") ? null : obj.getString("name");
                String role = obj.isNull("role") ? null : obj.getString("role");
                String gender = obj.isNull("gender") ? null : obj.getString("gender");
                String phone = obj.isNull("phone") ? null : obj.getString("phone");
                String mobilePhone = obj.isNull("mobilePhone") ? null : obj.getString("mobilePhone");
                String address = obj.isNull("address") ? null : obj.getString("address");
                String email = obj.isNull("email") ? null : obj.getString("email");
                String password = obj.isNull("password") ? null : obj.getString("password");
                String about = obj.isNull("about") ? null : obj.getString("about");

                // Handle birthday parsing
                Date birthday = null;
                if (!obj.isNull("birthday") && !obj.getString("birthday").isEmpty()) {
                    String birthdayStr = obj.getString("birthday");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        birthday = dateFormat.parse(birthdayStr);

                        // Check if birthday is today or before
                        if (birthday.after(new Date())) {
                            return ResponseEntity.badRequest()
                                    .body("{\"message\": \"Birthday cannot be in the future\"}");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return ResponseEntity.badRequest()
                                .body("{\"message\": \"Invalid date format for birthday\"}");
                    }
                }

                if (name == null || name.length() == 0) {
                    return ResponseEntity.badRequest()
                            .body("{\"message\": \"Name can't be null or empty string\"}");
                }

                if (email == null || email.length() == 0) {
                    return ResponseEntity.badRequest()
                            .body("{\"message\": \"Email can't be null or empty string\"}");
                }

                if (password == null || password.length() == 0) {
                    return ResponseEntity.badRequest()
                            .body("{\"message\": \"Password can't be null or empty string\"}");
                }

                // Password hashed
                String hashedPassword = passwordEncoder.encode(password);

                User newUser = new User();
                newUser.setName(name);
                newUser.setRole(role);
                newUser.setGender(gender);
                newUser.setBirthday(birthday);
                newUser.setPhone(phone);
                newUser.setMobilePhone(mobilePhone);
                newUser.setAddress(address);
                newUser.setEmail(email);
                newUser.setPassword(hashedPassword);
                newUser.setAbout(about);
                newUser.setAvatarBase64(null);
                newUser.setBackgroundImageBlob(null);

                userRepository.save(newUser);

                return ResponseEntity.ok("{\"message\": \"Successfully created user\"}");
            } catch (JSONException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid JSON request\"}");
    }

    public ResponseEntity<String> login(String jsonRequest) throws JSONException {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            JSONObject obj = new JSONObject(jsonRequest);

            String email = obj.isNull("email") ? null : obj.getString("email");
            String password = obj.isNull("password") ? null : obj.getString("password");

            if (email == null || email.length() == 0) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Email can not be null or empty string\"}");
            }

            if (password == null || password.length() == 0) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Password can not be null or empty string\"}");
            }

            // Fetch the user by email
            User user = userRepository.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"message\": \"Email not found\"}");
            }

            // Verify the password
            boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
            if (!passwordMatches) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("{\"message\": \"Invalid password\"}");
            }

            // Generate JWT token
            String token = Jwts.builder()
                    .setSubject(user.getId().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 864_000_00)) // 1 day
                    .signWith(SignatureAlgorithm.HS256, secretKey) // Use a secure key in production
                    .compact();

            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid JSON request\"}");
    }

    public ResponseEntity<String> deleteById(UUID id) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("{\"message\": \"User deleted successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"message\": \"User not found\"}");
            }
        }

        return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> update(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);

                    String name = obj.isNull("name") ? null : obj.getString("name");
                    String role = obj.isNull("role") ? null : obj.getString("role");
                    String gender = obj.isNull("gender") ? null : obj.getString("gender");
                    String phone = obj.isNull("phone") ? null : obj.getString("phone");
                    String mobilePhone = obj.isNull("mobilePhone") ? null : obj.getString("mobilePhone");
                    String address = obj.isNull("address") ? null : obj.getString("address");
                    String email = obj.isNull("email") ? null : obj.getString("email");
                    String about = obj.isNull("about") ? null : obj.getString("about");

                    // Handle birthday parsing
                    Date birthday = null;
                    if (!obj.isNull("birthday") && !obj.getString("birthday").isEmpty()) {
                        String birthdayStr = obj.getString("birthday");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            birthday = dateFormat.parse(birthdayStr);

                            // Check if birthday is today or before
                            if (birthday.after(new Date())) {
                                return ResponseEntity.badRequest()
                                        .body("{\"message\": \"Birthday cannot be in the future\"}");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return ResponseEntity.badRequest()
                                    .body("{\"message\": \"Invalid date format for birthday\"}");
                        }
                    }

                    if (name == null || name.length() == 0) {
                        return ResponseEntity.badRequest()
                                .body("{\"message\": \"name can't be null or empty string\"}");
                    }

                    if (email == null || email.length() == 0) {
                        return ResponseEntity.badRequest()
                                .body("{\"message\": \"email can't be null or empty string\"}");
                    }

                    user.setName(name);
                    user.setRole(role);
                    user.setGender(gender);
                    user.setBirthday(birthday);
                    user.setPhone(phone);
                    user.setMobilePhone(mobilePhone);
                    user.setAddress(address);
                    user.setEmail(email);
                    user.setAbout(about);

                    userRepository.save(user);

                    return ResponseEntity.ok("{\"message\": \"Successfully updated user\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"User not found\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> checkPassword(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);
                    String oldPassword = obj.isNull("oldPassword") ? null : obj.getString("oldPassword");

                    if (oldPassword == null || oldPassword.length() == 0) {
                        return ResponseEntity.badRequest()
                                .body("{\"message\": \"password can't be null or empty string\"}");
                    }

                    // Verify the password
                    boolean passwordMatches = passwordEncoder.matches(oldPassword, user.getPassword());
                    if (!passwordMatches) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("{\"message\": \"Invalid password\"}");
                    }

                    String resetPasswordUrl = "http://localhost:5173/user/reset-password";
                    return ResponseEntity.ok("{\"message\": \"" + resetPasswordUrl + "\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"User not found\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> forgotPassword(String jsonRequest) {
        try {
            JSONObject obj = new JSONObject(jsonRequest);
            String email = obj.isNull("email") ? null : obj.getString("email");

            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"Email can't be null or empty\"}");
            }

            User user = userRepository.findByEmail(email);

            if (user == null) {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"User not found\"}");
            }

            String resetLink = "http://localhost:5173/user/reset-password/";

            String htmlContent = "<p>Click the following link to reset your password:</p>" +
                    "<a href=\"" + resetLink + "\">Reset Password</a>";

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper;

            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("Nomad@example.com");
                messageHelper.setTo(email);
                messageHelper.setSubject("Password Reset Request");
                messageHelper.setText(htmlContent, true);
                mailSender.send(mimeMessage);

                JSONObject response = new JSONObject()
                        .put("id", user.getId());

                return ResponseEntity.ok(response.toString());
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"message\": \"Error seding email: " + e.getMessage() + "\"}");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
        }
    }

    public ResponseEntity<String> setNewPassword(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);
                    String newPassword = obj.isNull("newPassword") ? null : obj.getString("newPassword");

                    if (newPassword == null || newPassword.length() == 0) {
                        return ResponseEntity.badRequest()
                                .body("{\"message\": \"password can't be null or empty string\"}");
                    }

                    // Password hashed
                    String hashedPassword = passwordEncoder.encode(newPassword);

                    user.setPassword(hashedPassword);
                    userRepository.save(user);

                    return ResponseEntity.ok("{\"message\": \"Successfully updated password\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"User not found\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> uploadAvater(UUID id, String jsonRequest) {
        if (id != null && !id.toString().isEmpty()) {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);
                    String avatar = obj.isNull("avatarBase64") ? null : obj.getString("avatarBase64");

                    user.setAvatarBase64(avatar);
                    userRepository.save(user);

                    return ResponseEntity.ok("{\"message\": \"Successfully upload avatar\"}");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
                }
            } else {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"User not found\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"Invalid ID\"}");
    }

    public ResponseEntity<String> uploadBackgroundImage(UUID id, MultipartFile backgroundImageBlobFile) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Invalid ID\"}");
        }

        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            User user = optional.get();

            if (backgroundImageBlobFile != null && !backgroundImageBlobFile.isEmpty()) {

                // Get the byte[] from the uploaded file
                byte[] backgroundImageBlobFileBytes;
                try {
                    backgroundImageBlobFileBytes = backgroundImageBlobFile.getBytes();
                    user.setBackgroundImageBlob(backgroundImageBlobFileBytes);
                    userRepository.save(user);

                    return ResponseEntity.ok("{\"message\": \"Successfully uploaded background image\"}");
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest()
                            .body("{\"message\": \"Parse byte[] error\"}");
                }
            } else {
                return ResponseEntity.badRequest()
                        .body("{\"message\": \"backgroundImageBlob file is empty\"}");
            }
        }

        return ResponseEntity.badRequest()
                .body("{\"message\": \"User not found\"}");
    }

    public ResponseEntity<String> deleteAvatar(UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Invalid ID\"}");
        }

        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            User user = optional.get();
            user.setAvatarBase64(null);
            userRepository.save(user);

            return ResponseEntity.ok("{\"message\": \"Successfully delete avatar\"}");
        } else {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"User not found\"}");
        }
    }

    public ResponseEntity<Resource> getBackgroundImage(UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(new ByteArrayResource("{\"message\": \"Invalid ID\"}".getBytes()));
        }

        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            User user = optional.get();
            byte[] backgroundImageBlob = user.getBackgroundImageBlob();

            if (backgroundImageBlob != null && backgroundImageBlob.length > 0) {
                ByteArrayResource resource = new ByteArrayResource(backgroundImageBlob);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"backgroundImage.jpg\"")
                        .contentLength(backgroundImageBlob.length)
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ByteArrayResource("{\"message\": \"Background image not found\"}".getBytes()));
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ByteArrayResource("{\"message\": \"User not found\"}".getBytes()));
    }

    public ResponseEntity<String> deleteBackgroundImage(UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"Invalid ID\"}");
        }

        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            User user = optional.get();
            user.setBackgroundImageBlob(null);
            userRepository.save(user);

            return ResponseEntity.ok("{\"message\": \"Successfully delete background image\"}");
        } else {
            return ResponseEntity.badRequest()
                    .body("{\"message\": \"User not found\"}");
        }
    }
}
