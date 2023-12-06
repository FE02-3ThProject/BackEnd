package com.github.gather.service;

import com.github.gather.dto.UserDTO;
import com.github.gather.dto.request.UserLoginRequest;
import com.github.gather.dto.request.UserSignupRequest;
import com.github.gather.dto.response.UserLoginResponse;
import com.github.gather.exception.UserRuntimeException;
import com.github.gather.repositroy.ProfileRepository;
import com.github.gather.repositroy.UserRepository;
import com.github.gather.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
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
                .build();
    }

    public  UserLoginResponse login(UserLoginRequest user) {
        User loginUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new BadCredentialsException("이메일을 다시 확인해주세요.")
        );

        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            throw new BadCredentialsException("비밀번호를 다시 확인해주세요.");
        }


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


    public Boolean checkUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.isEmpty();
    }

    @Transactional
    public UserDTO getUserInfoByEmail(String email) {
        try {
            // UserRepository를 통해 이메일을 기준으로 사용자를 찾음
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserRuntimeException("사용자를 찾을 수 없습니다"));
            // 여기에서 예외가 발생했을 때 처리할 로직

            return mapUserToDTO(user);
        } catch (UserRuntimeException e) {
            // UserRuntimeException이 발생했을 때 처리할 로직
            // 여기에서 필요한 예외 처리를 수행하거나, 다시 던지거나 로깅 등을 수행
            e.printStackTrace();
            return null;
        }
    }

    private UserDTO mapUserToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNickname((userDTO.getNickname()));
        userDTO.setEmail(user.getEmail());
        // 다른 필요한 정보들을 매핑

        return userDTO;
    }

}
