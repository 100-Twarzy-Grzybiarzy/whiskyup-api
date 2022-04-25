package pl.kat.ue.whiskyup.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.mapper.WhiskyMapper;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.service.WhiskyService;

@Component
@RequiredArgsConstructor
public class NewWhiskySqsListener {

    private final WhiskyMapper whiskyMapper;
    private final WhiskyService whiskyService;

    @SqsListener(value = "${cloud.aws.sqs.queue.new-whisky}")
    public void onNewWhiskySqsMessage(WhiskyDto whiskyDto) {
        Whisky whisky = whiskyMapper.mapDtoToModel(whiskyDto);
        whiskyService.addWhisky(whisky);
    }

}