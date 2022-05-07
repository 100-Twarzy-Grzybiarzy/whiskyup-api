package pl.kat.ue.whiskyup.utils.listener;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.mapper.WhiskyMapper;
import pl.kat.ue.whiskyup.model.ActionType;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.service.WhiskyService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WhiskyQueueListener {

    private final WhiskyMapper whiskyMapper;
    private final WhiskyService whiskyService;

    @SqsListener("${cloud.aws.sqs.queue.whisky}")
    public void onSqsMessage(@Header("ActionType") String actionTypeDto,
                             @Payload WhiskyDto whiskyDto) {

        ActionType actionType = ActionType.valueOfLabel(actionTypeDto);

        if (ActionType.CREATE.equals(actionType)) {
            Whisky whisky = whiskyMapper.mapDtoToModel(whiskyDto);
            whiskyService.addWhisky(whisky);
            log.info("New whisky '{}'", whisky.getUrl());

        } else if (ActionType.DELETE.equals(actionType)) {
            String url = whiskyDto.getUrl();
            boolean isDeleted = whiskyService.deleteWhisky(url);
            log.info(isDeleted ? "Deleted whisky '{}'" : "Could not delete. No such whisky '{}'", url);

        } else {
            log.info("Could not process message. Action type neither 'create' nor 'delete'");
        }
    }
}