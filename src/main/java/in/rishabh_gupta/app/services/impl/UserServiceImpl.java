package in.rishabh_gupta.app.services.impl;

import in.rishabh_gupta.app.dto.request.UserRequest;
import in.rishabh_gupta.app.dto.response.UserResponse;
import in.rishabh_gupta.app.entity.AppUser;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.enums.UserSortFields;
import in.rishabh_gupta.app.exception.DuplicateEmailException;
import in.rishabh_gupta.app.exception.ResourceNotFoundException;
import in.rishabh_gupta.app.mapper.UserMapper;
import in.rishabh_gupta.app.repository.UserRepository;
import in.rishabh_gupta.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponse> index(String keyword, int pageNumber, int pageSize, UserSortFields sortBy, SortDirection sortDir) {
        Sort sort = sortDir.equals(SortDirection.ASC) ? Sort.by(sortBy.name()).ascending() : Sort.by(sortBy.name()).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<AppUser> users;

        if (keyword == null || keyword.trim().isEmpty()) {
            users = this.userRepository.findAll(pageable);
        } else {
            users = this.userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, keyword, pageable);
        }

        return users.map(this.userMapper::toResponse);
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        AppUser checkExistingUser = this.userRepository.findByEmail(userRequest.getEmail());
        if (checkExistingUser != null) {
            throw new DuplicateEmailException(DUPLICATE_EMAIL_MESSAGE);
        }
        AppUser user = this.userMapper.toEntity(userRequest);
        user.setPassword(this.passwordEncoder.encode(userRequest.getPassword()));
        AppUser userSaved = this.userRepository.save(user);
        return this.userMapper.toResponse(userSaved);
    }

    @Override
    public UserResponse show(Long id) {
        AppUser user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));

        return this.userMapper.toResponse(user);
    }

    @Override
    public UserResponse update(Long id, UserRequest userRequest) {
        AppUser user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
        AppUser checkExistingUser = this.userRepository.findByEmail(userRequest.getEmail());
        if (checkExistingUser != null && !checkExistingUser.getId().equals(user.getId())) {
            throw new DuplicateEmailException(DUPLICATE_EMAIL_MESSAGE);
        }

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());

        user = this.userRepository.save(user);

        return this.userMapper.toResponse(user);
    }

    @Override
    public UserResponse delete(Long id) {
        AppUser user = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
        user.setDeletedAt(LocalDateTime.now());
        this.userRepository.save(user);

        return this.userMapper.toResponse(user);
    }

    @Override
    public UserResponse restore(Long id) {
        AppUser user = this.userRepository.findByIdAndDeletedAtIsNotNull(id);
        user.setDeletedAt(null);
        return this.userMapper.toResponse(this.userRepository.save(user));
    }
}
