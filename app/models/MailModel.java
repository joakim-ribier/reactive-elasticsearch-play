package models;

import java.io.Serializable;

import com.google.common.base.Objects;

public class MailModel implements Serializable {
    
    private static final long serialVersionUID = -87935052996107471L;
    
    private String id;
    
    private String to;
    private String subject;
    private String body;
    
    public MailModel() {}
    
    public MailModel(String id, String to, String subject, String body) {
        this.id = id;
        this.to = to;
        this.subject = subject;
        this.body = body;
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
        return Objects.hashCode(id, to, subject, body);
    }
    
    @Override
    public boolean equals(Object object){
        if (object instanceof MailModel) {
            MailModel that = (MailModel) object;
            return Objects.equal(this.id, that.id)
                && Objects.equal(this.to, that.to)
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
            .add("subject", subject)
            .add("body", body)
            .toString();
    }
    
}
