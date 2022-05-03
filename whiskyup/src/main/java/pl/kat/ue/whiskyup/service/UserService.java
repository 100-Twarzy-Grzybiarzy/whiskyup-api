package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.mapper.PaginationCursorMapper;
import pl.kat.ue.whiskyup.mapper.UserMapper;
import pl.kat.ue.whiskyup.mapper.WhiskyUserMapper;
import pl.kat.ue.whiskyup.model.*;
import pl.kat.ue.whiskyup.repository.UserRepository;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WhiskyUserMapper whiskyUserMapper;
    private final UserMapper userMapper;
    private final PaginationCursorMapper paginationCursorMapper;

    public UserDto addUser(UserDto userDto) {
        User newUser = userMapper.mapDtoToModel(userDto);
        User created = userRepository.addUser(newUser);
        return userMapper.mapModelToDto(created);
    }

    public UserWhiskiesFindResultDto getUserWhiskies(String userId, String paginationCursor) {
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(paginationCursor);
        Page<WhiskyUser> page = userRepository.getAllUserWhiskies(userId, exclusiveStartKey);

        List<UserWhiskyDto> results = page.items().stream()
                .map(whiskyUserMapper::mapModelToDto)
                .collect(Collectors.toList());

        String pageCursor = paginationCursorMapper.mapToCursor(page.lastEvaluatedKey());

        return new UserWhiskiesFindResultDto()
                .results(results)
                .pageCursor(pageCursor);
    }

    public UserWhiskyDto addWhisky(String userId, UserWhiskyDto userWhiskyDto) {
        WhiskyUser newWhiskyUser = whiskyUserMapper.mapDtoToModel(userId, userWhiskyDto);
        WhiskyUser created = userRepository.addUserWhisky(newWhiskyUser);
        return whiskyUserMapper.mapModelToDto(created);
    }
}