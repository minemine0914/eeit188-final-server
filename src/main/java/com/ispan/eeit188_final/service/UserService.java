package com.ispan.eeit188_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import com.ispan.eeit188_final.model.User;
import com.ispan.eeit188_final.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String findById(UUID id) {
        if (id != null && id.toString() != "") {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject()
                            .put("name", user.getName())
                            .put("gender", user.getGender())
                            .put("birthday", user.getBirthday())
                            .put("phone", user.getPhone())
                            .put("mobilePhone", user.getMobilePhone())
                            .put("address", user.getAddress())
                            .put("email", user.getEmail())
                            .put("password", user.getPassword())
                            .put("about", user.getAbout())
                            .put("createdAt", user.getCreatedAt())
                            .put("updatetedAt", user.getUpdatetedAt())
                            .put("headshotImageBase64", user.getHeadshotImageBase64())
                            .put("backgroundImageBlob", user.getBackgroundImageBlob());

                    return obj.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public Page<User> getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(pageable);
    }

    public String createUser(String jsonRequest, byte[] backgroundImageBlob) {
        if (jsonRequest != null && !jsonRequest.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(jsonRequest);

                String name = obj.isNull("name") ? null : obj.getString("name");
                String gender = obj.isNull("gender") ? null : obj.getString("gender");
                String phone = obj.isNull("phone") ? null : obj.getString("phone");
                String mobilePhone = obj.isNull("mobilePhone") ? null : obj.getString("mobilePhone");
                String address = obj.isNull("address") ? null : obj.getString("address");
                String email = obj.isNull("email") ? null : obj.getString("email");
                String password = obj.isNull("password") ? null : obj.getString("password");
                String about = obj.isNull("about") ? null : obj.getString("about");
                String headshotImageBase64 = obj.isNull("headshotImageBase64") ? null
                        : obj.getString("headshotImageBase64");

                // Handle birthday parsing
                Date birthday = null;
                if (!obj.isNull("birthday")) {
                    String birthdayStr = obj.getString("birthday");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        birthday = dateFormat.parse(birthdayStr);

                        // Check if birthday is today or before
                        if (birthday.after(new Date())) {
                            return "Birthday cannot be in the future";
                        }
                    } catch (ParseException e) {
                        return "Invalid date format for birthday";
                    }
                }

                if (name == null || name.length() == 0) {
                    return "name can't be null or empty string";
                }

                if (email == null || email.length() == 0) {
                    return "email can't be null or empty string";
                }

                if (password == null || password.length() == 0) {
                    return "password can't be null or empty string";
                }

                User newUser = new User();
                newUser.setName(name);
                newUser.setGender(gender);
                newUser.setBirthday(birthday);
                newUser.setPhone(phone);
                newUser.setMobilePhone(mobilePhone);
                newUser.setAddress(address);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setAbout(about);
                newUser.setHeadshotImageBase64(headshotImageBase64);
                newUser.setBackgroundImageBlob(backgroundImageBlob);

                userRepository.save(newUser);

                return "Successfully created user";
            } catch (JSONException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        return "Invalid JSON request";
    }

    public void deleteById(UUID id) {
        if (id != null && id.toString() != "") {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                userRepository.deleteById(id);
            }
        }
    }

    public String update(UUID id, String jsonRequest, byte[] backgroundImageBlob) {
        if (id != null && id.toString() != "") {
            Optional<User> optional = userRepository.findById(id);

            if (optional.isPresent()) {
                User user = optional.get();

                try {
                    JSONObject obj = new JSONObject(jsonRequest);

                    String name = obj.isNull("name") ? null : obj.getString("name");
                    String gender = obj.isNull("gender") ? null : obj.getString("gender");
                    String phone = obj.isNull("phone") ? null : obj.getString("phone");
                    String mobilePhone = obj.isNull("mobilePhone") ? null : obj.getString("mobilePhone");
                    String address = obj.isNull("address") ? null : obj.getString("address");
                    String email = obj.isNull("email") ? null : obj.getString("email");
                    String password = obj.isNull("password") ? null : obj.getString("password");
                    String about = obj.isNull("about") ? null : obj.getString("about");
                    String headshotImageBase64 = obj.isNull("headshotImageBase64") ? null
                            : obj.getString("headshotImageBase64");

                    // Handle birthday parsing
                    Date birthday = null;
                    if (!obj.isNull("birthday")) {
                        String birthdayStr = obj.getString("birthday");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            birthday = dateFormat.parse(birthdayStr);

                            // Check if birthday is today or before
                            if (birthday.after(new Date())) {
                                return "Birthday cannot be in the future";
                            }
                        } catch (ParseException e) {
                            return "Invalid date format for birthday";
                        }
                    }

                    if (name == null || name.length() == 0) {
                        return "name can't be null or empty string";
                    }

                    if (email == null || email.length() == 0) {
                        return "email can't be null or empty string";
                    }

                    if (password == null || password.length() == 0) {
                        return "password can't be null or empty string";
                    }

                    user.setName(name);
                    user.setGender(gender);
                    user.setBirthday(birthday);
                    user.setPhone(phone);
                    user.setMobilePhone(mobilePhone);
                    user.setAddress(address);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setAbout(about);
                    user.setHeadshotImageBase64(headshotImageBase64);
                    user.setBackgroundImageBlob(backgroundImageBlob);

                    userRepository.save(user);

                    return "Successfully updated user";
                } catch (JSONException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            }
        }

        return "Invalid ID";
    }
}
