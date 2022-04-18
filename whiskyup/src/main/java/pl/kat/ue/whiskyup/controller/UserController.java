package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.UserApi;
import pl.kat.ue.whiskyup.model.UserWhiskiesFindResultDto;
import pl.kat.ue.whiskyup.model.UserWhiskyDto;
import pl.kat.ue.whiskyup.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserWhiskiesFindResultDto> getUserWhiskies(@PathVariable String id,
                                                                     @RequestParam(required = false) String exclusiveStartKey) {

        UserWhiskiesFindResultDto result = userService.getUserWhiskies(id, exclusiveStartKey);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<UserWhiskyDto> addUserWhisky(@PathVariable String id,
                                                       @RequestBody UserWhiskyDto userWhiskyDto) {

        UserWhiskyDto result = userService.addWhisky(id, userWhiskyDto);
        return ResponseEntity.ok(result);
    }
}