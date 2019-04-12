package com.github.hasoo.ircs.core.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.github.hasoo.ircs.core.entity.MsgLog;

@Repository
@Qualifier("customMsgLogRepository")
public class CustomMsgLogRepositoryImpl implements CustomMsgLogRepository {
  public static final String PREFIX_QUERY = "UPDATE MsgLog SET";
  public static final String WHERE_QUERY = " WHERE msg_key = :msgKey";
  public static final String FIRST_DELIMITER = " ";
  public static final String DELIMITER = ",";

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void insert(MsgLog msgLog) {
    entityManager.persist(msgLog);
  }

  @Override
  public int update(MsgLog msgLog) {
    Query query = entityManager.createQuery(getUpdateQuery(msgLog));
    if (null != msgLog.getUserKey()) {
      query.setParameter("userKey", msgLog.getUserKey());
    }
    if (null != msgLog.getGroupname()) {
      query.setParameter("groupname", msgLog.getGroupname());
    }
    if (null != msgLog.getUsername()) {
      query.setParameter("username", msgLog.getUsername());
    }
    if (null != msgLog.getResDate()) {
      query.setParameter("resDate", msgLog.getResDate());
    }
    if (null != msgLog.getRouteDate()) {
      query.setParameter("routeDate", msgLog.getRouteDate());
    }
    if (null != msgLog.getSentDate()) {
      query.setParameter("sentDate", msgLog.getSentDate());
    }
    if (null != msgLog.getDoneDate()) {
      query.setParameter("doneDate", msgLog.getDoneDate());
    }
    if (null != msgLog.getReportDate()) {
      query.setParameter("reportDate", msgLog.getReportDate());
    }
    if (null != msgLog.getStatus()) {
      query.setParameter("status", msgLog.getStatus());
    }
    if (null != msgLog.getMsgType()) {
      query.setParameter("msgType", msgLog.getMsgType());
    }
    if (null != msgLog.getContentType()) {
      query.setParameter("contentType", msgLog.getContentType());
    }
    if (null != msgLog.getPhone()) {
      query.setParameter("phone", msgLog.getPhone());
    }
    if (null != msgLog.getCallback()) {
      query.setParameter("callback", msgLog.getCallback());
    }
    if (null != msgLog.getMessage()) {
      query.setParameter("message", msgLog.getMessage());
    }
    if (null != msgLog.getResultCode()) {
      query.setParameter("result_code", msgLog.getResultCode());
    }
    if (null != msgLog.getResultDesc()) {
      query.setParameter("result_desc", msgLog.getResultDesc());
    }
    if (null != msgLog.getNet()) {
      query.setParameter("net", msgLog.getNet());
    }
    if (null != msgLog.getSender()) {
      query.setParameter("sender", msgLog.getSender());
    }
    return query.setParameter("msgKey", msgLog.getMsgKey()).executeUpdate();
  }

  private String getUpdateQuery(MsgLog msgLog) {
    List<String> columns = new ArrayList<>();
    if (null != msgLog.getUserKey()) {
      columns.add("user_key = :userKey");
    }
    if (null != msgLog.getGroupname()) {
      columns.add("groupname = :groupname");
    }
    if (null != msgLog.getUsername()) {
      columns.add("username = :username");
    }
    if (null != msgLog.getResDate()) {
      columns.add("res_date = :resDate");
    }
    if (null != msgLog.getRouteDate()) {
      columns.add("route_date = :routeDate");
    }
    if (null != msgLog.getSentDate()) {
      columns.add("sent_date = :sentDate");
    }
    if (null != msgLog.getDoneDate()) {
      columns.add("done_date = :doneDate");
    }
    if (null != msgLog.getReportDate()) {
      columns.add("report_date = :reportDate");
    }
    if (null != msgLog.getStatus()) {
      columns.add("status = status + :status");
    }
    if (null != msgLog.getMsgType()) {
      columns.add("msg_type = :msgType");
    }
    if (null != msgLog.getContentType()) {
      columns.add("content_type = :contentType");
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
