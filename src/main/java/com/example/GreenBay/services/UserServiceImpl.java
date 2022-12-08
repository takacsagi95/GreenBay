package com.example.GreenBay.services;

import com.example.GreenBay.models.dtos.UserRegistrationDto;
import com.example.GreenBay.models.entities.Role;
import com.example.GreenBay.models.entities.User;
import com.example.GreenBay.repositories.RoleRepository;
import com.example.GreenBay.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public User saveReturnUser(User user) {
    User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
    Role role = roleRepository.findRoleByName("ROLE_USER");
    newUser.getRoles().add(role);
    return userRepository.save(newUser);
  }

  @Override
  public User findUserByUsername(String username) {
    return userRepository.findUserByUsername(username);
  }

  @Override
  public boolean userExists(String username) {
    return userRepository.findUserByUsername(username) != null;
  }

  @Override
  public boolean validUser(User user) {
    return !user.getUsername().isEmpty() && !user.getPassword().isEmpty();
  }

  @Override
  public boolean passwordIsCorrect(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  @Override
  public UserRegistrationDto convertUserToUserRegistrationDto(User user) {
    UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
    userRegistrationDto.setId(user.getId());
    userRegistrationDto.setUsername(user.getUsername());
    return userRegistrationDto;
  }
}
