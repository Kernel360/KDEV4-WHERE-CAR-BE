package com.wherecar.collector.service;

import com.wherecar.collector.domain.OnOffLog;
import com.wherecar.collector.repository.OnOffLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OnOffLogSaveServiceImpl implements OnOffLogSaveService {

    private final OnOffLogRepository onOffLogRepository;

    @Override
    public void saveOnOffLog(OnOffLog onOffLog) {
        onOffLogRepository.save(onOffLog);
    }
}
