package pl.kat.ue.whiskyup.utils;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.mapper.WhiskyBaseMapper;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.service.WhiskyService;


@Component
@RequiredArgsConstructor
@Slf4j
public class NewWhiskySqsListener {

    private final WhiskyBaseMapper whiskyBaseMapper;
    private final WhiskyService whiskyService;

    @SqsListener("${cloud.aws.sqs.queue.new-whisky}")
    public void onNewWhiskySqsMessage(final WhiskyDto whiskyDto) {
        log.info("New whisky '{}'", whiskyDto.getUrl());
        WhiskyBase whiskyBase = whiskyBaseMapper.mapDtoToModel(whiskyDto);
        whiskyService.addWhisky(whiskyBase);
    }

}