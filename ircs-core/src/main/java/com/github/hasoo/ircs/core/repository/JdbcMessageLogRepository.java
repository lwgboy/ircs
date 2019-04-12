package com.github.hasoo.ircs.core.repository;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import com.github.hasoo.ircs.core.entity.MsgLog;

@Repository
public class JdbcMessageLogRepository implements MessageLogRepository {
  public static final String PREFIX_QUERY = "update msg_log set";
  public static final String WHERE_QUERY = " where msg_key = :msgKey";
  public static final String FIRST_DELIMITER = " ";
  public static final String DELIMITER = ",";

  private SimpleJdbcInsert simpleJdbcInsert;

  // @Autowired
  // private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate JdbcTemplate;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("msg_log");
  }

  @Override
  public void insert(MsgLog msgLog) {
    SqlParameterSource params = new BeanPropertySqlParameterSource(msgLog);
    simpleJdbcInsert.execute(params);
  }

  @Override
  public int update(MsgLog msgLog) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    if (null != msgLog.getUserKey()) {
      params.addValue("user_key", msgLog.getUserKey());
    }
    if (null != msgLog.getGroupname()) {
      params.addValue("groupname", msgLog.getGroupname());
    }
    if (null != msgLog.getUsername()) {
      params.addValue("username", msgLog.getUsername());
    }
    if (null != msgLog.getResDate()) {
      params.addValue("res_date", msgLog.getResDate());
    }
    if (null != msgLog.getRouteDate()) {
      params.addValue("route_date", msgLog.getRouteDate());
    }
    if (null != msgLog.getSentDate()) {
      params.addValue("sent_date", msgLog.getSentDate());
    }
    if (null != msgLog.getDoneDate()) {
      params.addValue("done_date", msgLog.getDoneDate());
    }
    if (null != msgLog.getReportDate()) {
      params.addValue("report_date", msgLog.getReportDate());
    }
    if (null != msgLog.getStatus()) {
      params.addValue("status", msgLog.getStatus());
    }
    if (null != msgLog.getMsgType()) {
      params.addValue("msg_type", msgLog.getMsgType());
    }
    if (null != msgLog.getContentType()) {
      params.addValue("content_type", msgLog.getContentType());
    }
    if (null != msgLog.getPhone()) {
      params.addValue("phone", msgLog.getPhone());
    }
    if (null != msgLog.getCallback()) {
      params.addValue("callback", msgLog.getCallback());
    }
    if (null != msgLog.getMessage()) {
      params.addValue("message", msgLog.getMessage());
    }
    if (null != msgLog.getResultCode()) {
      params.addValue("result_code", msgLog.getResultCode());
    }
    if (null != msgLog.getResultDesc()) {
      params.addValue("result_desc", msgLog.getResultDesc());
    }
    if (null != msgLog.getNet()) {
      params.addValue("net", msgLog.getNet());
    }
    if (null != msgLog.getSender()) {
      params.addValue("sender", msgLog.getSender());
    }
    params.addValue("msgKey", msgLog.getMsgKey());
    return JdbcTemplate.update(getUpdateQuery(msgLog), params);
  }

  private String getUpdateQuery(MsgLog msgLog) {
    List<String> columns = new ArrayList<>();
    if (null != msgLog.getUserKey()) {
      columns.add("user_key = :user_key");
    }
    if (null != msgLog.getGroupname()) {
      columns.add("groupname = :groupname");
    }
    if (null != msgLog.getUsername()) {
      columns.add("username = :username");
    }
    if (null != msgLog.getResDate()) {
      columns.add("res_date = :res_date");
    }
    if (null != msgLog.getRouteDate()) {
      columns.add("route_date = :route_date");
    }
    if (null != msgLog.getSentDate()) {
      columns.add("sent_date = :sent_date");
    }
    if (null != msgLog.getDoneDate()) {
      columns.add("done_date = :done_date");
    }
    if (null != msgLog.getReportDate()) {
      columns.add("report_date = :report_date");
    }
    if (null != msgLog.getStatus()) {
      columns.add("status = status + :status");
    }
    if (null != msgLog.getMsgType()) {
      columns.add("msg_type = :msg_type");
    }
    if (null != msgLog.getContentType()) {
      columns.add("content_type = :content_type");
    }
    if (null != msgLog.getPhone()) {
      columns.add("phone = :phone");
    }
    if (null != msgLog.getCallback()) {
      columns.add("callback = :callback");
    }
    if (null != msgLog.getMessage()) {
      columns.add("message = :message");
    }
    if (null != msgLog.getResultCode()) {
      columns.add("result_code = :result_code");
    }
    if (null != msgLog.getResultDesc()) {
      columns.add("result_desc = :result_desc");
    }
    if (null != msgLog.getNet()) {
      columns.add("net = :net");
    }
    if (null != msgLog.getSender()) {
      columns.add("sender = :sender");
    }

    String query = PREFIX_QUERY;
    int count = columns.size();
    for (int i = 0; i < count; i++) {
      if (0 == i) {
        query = query + FIRST_DELIMITER;
      } else {
        query = query + DELIMITER;
      }
      query = query + columns.get(i);
    }
    query = query + WHERE_QUERY;

    return query;
  }

}
