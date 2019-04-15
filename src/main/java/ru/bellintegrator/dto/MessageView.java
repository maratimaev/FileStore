package ru.bellintegrator.dto;

/**
 * Dto сообщения
 */
public class MessageView {

    /**
     * id сообщения
     */
    private Long id;

    /**
     * Отправитель
     */
    private UserView from;

    /**
     * Адрессат
     */
    private UserView to;

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

    public MessageView() {
    }

    public MessageView(UserView from, UserView to, Integer code, String msg, String msgUuid) {
        this.from = from;
        this.to = to;
        this.code = code;
        this.msg = msg;
        this.msgUuid =  msgUuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserView getFrom() {
        return from;
    }

    public void setFrom(UserView from) {
        this.from = from;
    }

    public UserView getTo() {
        return to;
    }

    public void setTo(UserView to) {
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
        return "MessageView{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", msgUuid='" + msgUuid + '\'' +
                '}';
    }
}
