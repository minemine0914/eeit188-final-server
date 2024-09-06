// package com.ispan.eeit188_final.service;

// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.configurationprocessor.json.JSONArray;
// import org.springframework.boot.configurationprocessor.json.JSONException;
// import org.springframework.boot.configurationprocessor.json.JSONObject;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;

// import com.ispan.eeit188_final.model.User;
// import com.ispan.eeit188_final.model.UserCollection;
// import com.ispan.eeit188_final.model.composite.UserCollectionId;
// import com.ispan.eeit188_final.repository.UserCollectionRepository;
// import com.ispan.eeit188_final.repository.UserRepository;

// @Service
// public class UserCollectionService {

// @Autowired
// private UserCollectionRepository userCollectionRepository;

// public ResponseEntity<String> findById(UserCollectionId id) {
// if (id != null && !id.toString().isEmpty()) {
// Optional<UserCollection> optional = userCollectionRepository.findById(id);

// if (optional.isPresent()) {
// UserCollection userCollection = optional.get();

// try {
// JSONObject obj = new JSONObject()
// .put("userId", userCollection.getUserCollectionId().getUserId())
// .put("houseId", userCollection.getUserCollectionId().getHouseId());

// return ResponseEntity.ok(obj.toString());
// } catch (JSONException e) {
// e.printStackTrace();
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// .body("{\"message\": \"Error creating JSON: " + e.getMessage() + "\"}");
// }
// } else {
// return ResponseEntity.status(HttpStatus.NOT_FOUND)
// .body("{\"message\": \"User not found\"}");
// }
// }

// return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
// }

// public ResponseEntity<String> getUsers(int pageNo, int pageSize) {
// Pageable pageable = PageRequest.of(pageNo, pageSize);
// Page<User> users = userRepository.findAll(pageable);

// try {
// JSONArray usersArray = new JSONArray();

// for (User user : users.getContent()) {
// JSONObject obj = new JSONObject()
// .put("name", user.getName())
// .put("gender", user.getGender())
// .put("birthday", user.getBirthday())
// .put("phone", user.getPhone())
// .put("mobilePhone", user.getMobilePhone())
// .put("address", user.getAddress())
// .put("email", user.getEmail())
// .put("about", user.getAbout())
// .put("createdAt", user.getCreatedAt())
// .put("updatedAt", user.getUpdatedAt())
// .put("headshotImageBase64", user.getHeadshotImageBase64())
// .put("backgroundImageBlob", user.getBackgroundImageBlob());

// usersArray.put(obj);
// }

// JSONObject response = new JSONObject()
// .put("users", usersArray)
// .put("currentPage", users.getNumber())
// .put("totalItems", users.getTotalElements())
// .put("totalPages", users.getTotalPages())
// .put("pageSize", users.getSize());

// return ResponseEntity.ok(response.toString());
// } catch (JSONException e) {
// e.printStackTrace();
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// .body("{\"message\": \"Error creating JSON response: " + e.getMessage() +
// "\"}");
// }
// }

// public ResponseEntity<String> createUser(String jsonRequest, byte[]
// backgroundImageBlob) {
// if (jsonRequest != null && !jsonRequest.isEmpty()) {
// try {
// JSONObject obj = new JSONObject(jsonRequest);

// String name = obj.isNull("name") ? null : obj.getString("name");
// String gender = obj.isNull("gender") ? null : obj.getString("gender");
// String phone = obj.isNull("phone") ? null : obj.getString("phone");
// String mobilePhone = obj.isNull("mobilePhone") ? null :
// obj.getString("mobilePhone");
// String address = obj.isNull("address") ? null : obj.getString("address");
// String email = obj.isNull("email") ? null : obj.getString("email");
// String password = obj.isNull("password") ? null : obj.getString("password");
// String about = obj.isNull("about") ? null : obj.getString("about");
// String headshotImageBase64 = obj.isNull("headshotImageBase64") ? null
// : obj.getString("headshotImageBase64");

// // Handle birthday parsing
// Date birthday = null;
// if (!obj.isNull("birthday") && !obj.getString("birthday").isEmpty()) {
// String birthdayStr = obj.getString("birthday");
// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// try {
// birthday = dateFormat.parse(birthdayStr);

// // Check if birthday is today or before
// if (birthday.after(new Date())) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Birthday cannot be in the future\"}");
// }
// } catch (ParseException e) {
// e.printStackTrace();
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Invalid date format for birthday\"}");
// }
// }

// if (name == null || name.length() == 0) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Name can't be null or empty string\"}");
// }

// if (email == null || email.length() == 0) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Email can't be null or empty string\"}");
// }

// if (password == null || password.length() == 0) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Password can't be null or empty string\"}");
// }

// User newUser = new User();
// newUser.setName(name);
// newUser.setGender(gender);
// newUser.setBirthday(birthday);
// newUser.setPhone(phone);
// newUser.setMobilePhone(mobilePhone);
// newUser.setAddress(address);
// newUser.setEmail(email);
// newUser.setPassword(password);
// newUser.setAbout(about);
// newUser.setHeadshotImageBase64(headshotImageBase64);
// newUser.setBackgroundImageBlob(backgroundImageBlob);

// userRepository.save(newUser);

// return ResponseEntity.ok("{\"message\": \"Successfully created user\"}");
// } catch (JSONException e) {
// e.printStackTrace();
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
// }
// }

// return ResponseEntity.badRequest()
// .body("{\"message\": \"Invalid JSON request\"}");
// }

// public ResponseEntity<String> deleteById(UUID id) {
// if (id != null && !id.toString().isEmpty()) {
// Optional<User> optional = userRepository.findById(id);

// if (optional.isPresent()) {
// userRepository.deleteById(id);
// return ResponseEntity.ok("{\"message\": \"User deleted successfully\"}");
// } else {
// return ResponseEntity.status(HttpStatus.NOT_FOUND)
// .body("{\"message\": \"User not found\"}");
// }
// }

// return ResponseEntity.badRequest().body("{\"message\": \"Invalid ID\"}");
// }

// public ResponseEntity<String> update(UUID id, String jsonRequest, byte[]
// backgroundImageBlob) {
// if (id != null && !id.toString().isEmpty()) {
// Optional<User> optional = userRepository.findById(id);

// if (optional.isPresent()) {
// User user = optional.get();

// try {
// JSONObject obj = new JSONObject(jsonRequest);

// String name = obj.isNull("name") ? null : obj.getString("name");
// String gender = obj.isNull("gender") ? null : obj.getString("gender");
// String phone = obj.isNull("phone") ? null : obj.getString("phone");
// String mobilePhone = obj.isNull("mobilePhone") ? null :
// obj.getString("mobilePhone");
// String address = obj.isNull("address") ? null : obj.getString("address");
// String email = obj.isNull("email") ? null : obj.getString("email");
// String password = obj.isNull("password") ? null : obj.getString("password");
// String about = obj.isNull("about") ? null : obj.getString("about");
// String headshotImageBase64 = obj.isNull("headshotImageBase64") ? null
// : obj.getString("headshotImageBase64");

// // Handle birthday parsing
// Date birthday = null;
// if (!obj.isNull("birthday") && !obj.getString("birthday").isEmpty()) {
// String birthdayStr = obj.getString("birthday");
// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
// try {
// birthday = dateFormat.parse(birthdayStr);

// // Check if birthday is today or before
// if (birthday.after(new Date())) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Birthday cannot be in the future\"}");
// }
// } catch (ParseException e) {
// e.printStackTrace();
// return ResponseEntity.badRequest()
// .body("{\"message\": \"Invalid date format for birthday\"}");
// }
// }

// if (name == null || name.length() == 0) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"name can't be null or empty string\"}");
// }

// if (email == null || email.length() == 0) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"email can't be null or empty string\"}");
// }

// if (password == null || password.length() == 0) {
// return ResponseEntity.badRequest()
// .body("{\"message\": \"password can't be null or empty string\"}");
// }

// user.setName(name);
// user.setGender(gender);
// user.setBirthday(birthday);
// user.setPhone(phone);
// user.setMobilePhone(mobilePhone);
// user.setAddress(address);
// user.setEmail(email);
// user.setPassword(password);
// user.setAbout(about);
// user.setHeadshotImageBase64(headshotImageBase64);
// user.setBackgroundImageBlob(backgroundImageBlob);

// userRepository.save(user);

// return ResponseEntity.ok("{\"message\": \"Successfully updated user\"}");
// } catch (JSONException e) {
// e.printStackTrace();
// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
// .body("{\"message\": \"Error parsing JSON: " + e.getMessage() + "\"}");
// }
// }
// }

// return ResponseEntity.badRequest()
// .body("{\"message\": \"Invalid ID\"}");
// }
// }
