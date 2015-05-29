package models;

import java.io.Serializable;

import com.google.common.base.Objects;

public class MailModel implements Serializable {
    
    private static final long serialVersionUID = -87935052996107471L;
    
    private String id;
    
    private String to;
    private String bcc;
    private boolean bccEnable;
    private String subject;
    private String body;
    
    public MailModel() {}
    
    public MailModel(String id, String to, String bcc, String subject, String body) {
        this.id = id;
        this.to = to;
        this.bcc = bcc;
        this.subject = subject;
        this.body = body;
        this.bccEnable = false;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public String getBcc() {
        return bcc;
    }
    
    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
    
    public boolean isBccEnable() {
        return bccEnable;
    }
    
    public void setBccEnable(boolean bccEnable) {
        this.bccEnable = bccEnable;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    @Override
    public int hashCode(){
        return Objects.hashCode(id, to, bcc, bccEnable, subject, body);
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof MailModel) {
            MailModel that = (MailModel) object;
            return Objects.equal(this.id, that.id)
                && Objects.equal(this.to, that.to)
                && Objects.equal(this.bcc, that.bcc)
                && Objects.equal(this.bccEnable, that.bccEnable)
                && Objects.equal(this.subject, that.subject)
                && Objects.equal(this.body, that.body);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("id", id)
            .add("to", to)
            .add("bcc", bcc)
            .add("bccEnable", bccEnable)
            .add("subject", subject)
            .add("body", body)
            .toString();
    }
    
}
