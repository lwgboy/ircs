package com.github.hasoo.ircs.core.dto;

import com.github.hasoo.ircs.core.queue.ReportQue;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
  private String result;
  List<ReportQue> reportQues;
}
