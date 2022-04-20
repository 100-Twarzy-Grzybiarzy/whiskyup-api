package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.mapper.PaginationCursorMapper;
import pl.kat.ue.whiskyup.mapper.UserMapper;
import pl.kat.ue.whiskyup.mapper.UserWhiskyMapper;
import pl.kat.ue.whiskyup.model.*;
import pl.kat.ue.whiskyup.repository.UserRepository;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserWhiskyMapper userWhiskyMapper;
    private final UserMapper userMapper;
    private final PaginationCursorMapper paginationCursorMapper;

    public UserDto addUser(UserDto userDto) {
        User newUser = userMapper.mapDtoToNewModel(userDto);
        User created = userRepository.addUser(newUser);
        return userMapper.mapModelToDto(created);
    }

    public UserWhiskiesFindResultDto getUserWhiskies(String userId, String paginationCursor) {
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(paginationCursor);
        Page<UserWhisky> page = userRepository.getAllUserWhiskies(userId, exclusiveStartKey);
        return new UserWhiskiesFindResultDto()
                .results(page.items().stream()
                        .map(userWhiskyMapper::mapModelToDto)
                        .collect(Collectors.toList()))
                .exclusiveStartKey(paginationCursorMapper.mapToCursor(page.lastEvaluatedKey()));
    }

    public UserWhiskyDto addWhisky(String userId, UserWhiskyDto userWhiskyDto) {
        UserWhisky newUserWhisky = userWhiskyMapper.mapDtoToNewModel(userId, userWhiskyDto);
        UserWhisky created = userRepository.addWhisky(newUserWhisky);
        return userWhiskyMapper.mapModelToDto(created);
    }
}