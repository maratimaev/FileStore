package ru.bellintegrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity сообщения
 */
@Entity
public class Message {
    /**
     * id сообщения
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    /**
     * Отправитель
     */
    @OneToOne(fetch = FetchType.EAGER)
    private User from;

    /**
     * Адрессат
     */
    @OneToOne(fetch = FetchType.EAGER)
    private User to;

    /**
     * Тип сообщения
     */
    private Integer code;

    /**
     * Тело сообщения
     */
    private String msg;

    /**
     * UUID сообщения
     */
    private String msgUuid;

    public Long getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgUuid() {
        return msgUuid;
    }

    public void setMsgUuid(String msgUuid) {
        this.msgUuid = msgUuid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", msgUuid='" + msgUuid + '\'' +
                '}';
    }
}
