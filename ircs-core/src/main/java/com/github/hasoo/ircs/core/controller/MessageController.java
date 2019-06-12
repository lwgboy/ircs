package com.github.hasoo.ircs.core.controller;

import com.github.hasoo.ircs.core.dto.MessageResponse;
import com.github.hasoo.ircs.core.dto.MultipleSms;
import com.github.hasoo.ircs.core.dto.ReportResponse;
import com.github.hasoo.ircs.core.dto.SmsRequest;
import com.github.hasoo.ircs.core.dto.UploadResponse;
import com.github.hasoo.ircs.core.queue.ReportQue;
import com.github.hasoo.ircs.core.service.ReceiverService;
import com.github.hasoo.ircs.core.service.ReportDeliverService;
import com.github.hasoo.ircs.core.util.NioFileSequence;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class MessageController {

  private final ReceiverService<SmsRequest> smsReceiverService;

  private final ReceiverService<MultipleSms> multipleSmsReceiverService;

  private final ReportDeliverService ReportDeliverService;

  private final NioFileSequence nioFileSequence;

  public MessageController(
      ReceiverService<SmsRequest> smsReceiverService,
      ReceiverService<MultipleSms> multipleSmsReceiverService,
      @Qualifier("reportDeliverService") ReportDeliverService ReportDeliverService,
      NioFileSequence nioFileSequence) {
    this.smsReceiverService = smsReceiverService;
    this.multipleSmsReceiverService = multipleSmsReceiverService;
    this.ReportDeliverService = ReportDeliverService;
    this.nioFileSequence = nioFileSequence;
  }

  @PostMapping(path = "/api/v1/sms", produces = "application/json")
  public ResponseEntity<?> receiveSms(@Valid @RequestBody SmsRequest smsRequest) {

    if (!smsReceiverService.receive(smsRequest)) {
      return new ResponseEntity<Object>(
          new MessageResponse(smsRequest.getKey(), "inner error", "9000"), HttpStatus.OK);
    }

    return new ResponseEntity<Object>(new MessageResponse(smsRequest.getKey(), "success", "1000"),
        HttpStatus.OK);
  }

  @PostMapping(path = "/api/v2/sms", produces = "application/json")
  public ResponseEntity<?> receiveMultipleSms(@Valid @RequestBody MultipleSms multipleSms) {

    log.info(multipleSms.toString());

    if (!multipleSmsReceiverService.receive(multipleSms)) {
      return new ResponseEntity<Object>(
          new MessageResponse(multipleSms.getGroupKey(), "inner error", "9000"), HttpStatus.OK);
    }

    return new ResponseEntity<Object>(
        new MessageResponse(multipleSms.getGroupKey(), "success", "1000"),
        HttpStatus.OK);
  }

  @GetMapping(path = "/api/v1/report", produces = "application/json")
  public ResponseEntity<?> writeReport() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    ReportResponse reportResponse = new ReportResponse();
    List<ReportQue> reports = null;
    try {
      reports = ReportDeliverService.receiveReport(username);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    if (null == reports) {
      reportResponse.setResult("NO");
    } else {
      reportResponse.setResult("OK");
      reportResponse.setReportQues(reports);
    }

    return new ResponseEntity<Object>(reportResponse, HttpStatus.OK);
  }

  @PostMapping(path = "/api/v1/upload")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    if (file.isEmpty()) {
      return new ResponseEntity<Object>(new UploadResponse("", "", "", "4000", "empty file"),
          HttpStatus.OK);
    }

    try {
      log.info("fileSequence:" + nioFileSequence.getSequence());
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new ResponseEntity<Object>(
        new UploadResponse(file.getOriginalFilename(), file.getContentType(), file.getName(),
            "1000", "success"),
        HttpStatus.OK);
  }
}
