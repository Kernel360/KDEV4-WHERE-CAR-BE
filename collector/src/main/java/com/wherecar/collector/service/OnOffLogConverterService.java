package com.wherecar.collector.service;

import com.wherecar.collector.dto.OnOffLogRequest;

public interface OnOffLogConverterService {

    void receiveOnLog(OnOffLogRequest onLogRequest);

    void receiveOffLog(OnOffLogRequest offLogRequest);

}
