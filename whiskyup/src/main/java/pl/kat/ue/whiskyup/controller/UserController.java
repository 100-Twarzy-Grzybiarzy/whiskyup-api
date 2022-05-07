package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.UserApi;
import pl.kat.ue.whiskyup.model.UserDto;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultDto;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {

        UserDto result = userService.addUser(userDto);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<WhiskiesFindResultDto> getUserWhiskies(@PathVariable String id,
                                                                 @RequestParam(required = false) String pageCursor) {

        WhiskiesFindResultDto result = userService.getUserWhiskies(id, pageCursor);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<WhiskyDto> addUserWhisky(@PathVariable String id,
                                                   @RequestBody WhiskyDto whiskyDto) {

        WhiskyDto result = userService.addWhisky(id, whiskyDto);
        return ResponseEntity.ok(result);
    }
}