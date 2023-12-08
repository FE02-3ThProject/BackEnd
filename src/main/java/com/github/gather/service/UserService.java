package com.github.gather.service;

import com.github.gather.dto.UserDTO;
import com.github.gather.dto.request.UserEditRequest;
import com.github.gather.dto.request.UserLoginRequest;
import com.github.gather.dto.request.UserSignupRequest;
import com.github.gather.dto.response.UserLoginResponse;
import com.github.gather.entity.Location;
import com.github.gather.entity.Role.UserRole;
import com.github.gather.exception.UserRuntimeException;
import com.github.gather.repositroy.ProfileRepository;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.github.gather.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public User signup(UserSignupRequest userData) {
        if(!checkUser(userData.getEmail())){
            throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
        }
        return User.builder()
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .locationId(userData.getLocation())
                .nickname(userData.getNickname())
                .phoneNumber(userData.getPhoneNumber())
                .image(userData.getImage())
                .userRole(userData.getUserRole())
                .isDeleted(false)
                .isLocked(false)
                .build();
    }

    public UserLoginResponse login(UserLoginRequest user) {
        User loginUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new BadCredentialsException("이메일을 다시 확인해주세요.")
        );

        if (loginUser.getIsLocked()) {
            throw new LockedException("계정이 잠겨있습니다.");
        }

        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            // 비밀번호 오류 처리
            loginUser.setLockCount(loginUser.getLockCount() + 1);
            if (loginUser.getLockCount() >= 5) {
                loginUser.setIsLocked(true);
                userRepository.save(loginUser);
                throw new LockedException("비밀번호 5회 이상 틀려 계정이 잠겼습니다.");
            }
            userRepository.save(loginUser);
            throw new BadCredentialsException("비밀번호를 다시 확인해주세요.");
        }

        // 정상 로그인 처리
        loginUser.setLockCount(0);
        loginUser.setIsLocked(false);
        userRepository.save(loginUser);

        String newToken = jwtTokenProvider.createToken(loginUser);

        return UserLoginResponse.builder()
                .email(loginUser.getEmail())
                .nickname(loginUser.getNickname())
                .phoneNumber(loginUser.getPhoneNumber())
                .location(loginUser.getLocationId())
                .userRole(loginUser.getUserRole())
                .image(loginUser.getImage())
                .token(newToken)
                .build();
    }


    public Boolean checkUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isEmpty();
    }

    public Boolean checkEmail(String email) {
        return !userRepository.existsByEmail(email);
    }

    public Boolean checkNickname(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }


    @Transactional
    public UserDTO getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserRuntimeException("회원 정보를 찾을 수 없습니다."));
        return mapUserToDTO(user);
    }


    // 회원 정보 수정
//    public UserDTO editInfo(UserEditRequest userEditRequest) {
//        User user = userRepository.findByEmail(userEditRequest.getEmail())
//                .orElseThrow(() -> new UserRuntimeException("회원 정보를 찾을 수 없습니다."));
//
//        // 사용자 정보 업데이트
//        user.setNickname(userEditRequest.getEditnickname());
//        user.setEmail(userEditRequest.getEditemail());
//        user.setPassword(userEditRequest.getEditpassword());
//        user.setPhoneNumber(userEditRequest.getEditphoneNumber());
//        user.setImage(userEditRequest.getEditimage());
//        user.setUserRole(userEditRequest.getEdituserRole());
//        user.setLocation(userEditRequest.getEditlocation());
//
//        User updatedUser = userRepository.save(user);
//        return mapUserToDTO(updatedUser);
//    }


//     수정한 정보 업데이트
    private UserDTO mapUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        user.setNickname(user.getNickname());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setImage(user.getImage());
        user.setUserRole(user.getUserRole());

        return userDTO;
    }

}