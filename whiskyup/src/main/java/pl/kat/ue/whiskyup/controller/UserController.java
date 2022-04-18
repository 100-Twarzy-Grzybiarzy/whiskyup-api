package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.model.UserWhiskiesFindResultApi;
import pl.kat.ue.whiskyup.model.UserWhiskyApi;
import pl.kat.ue.whiskyup.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements pl.kat.ue.whiskyup.api.UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserWhiskiesFindResultApi> getUserWhiskies(@PathVariable String id,
                                                                     @RequestParam(required = false) String exclusiveStartKey) {

        UserWhiskiesFindResultApi result = userService.getUserWhiskies(id, exclusiveStartKey);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<UserWhiskyApi> addUserWhisky(@PathVariable String id,
                                                       @RequestBody UserWhiskyApi userWhiskyApi) {

        UserWhiskyApi result = userService.addWhisky(id, userWhiskyApi);
        return ResponseEntity.ok(result);
    }
}