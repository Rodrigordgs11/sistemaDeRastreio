package pt.ipvc.rastreio.sistemaderastreio.backend;

public class Invite {
    private user sender;
    private user receiver;
    private String description;

    public Invite(user sender, user receiver, String description) {
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
    }

    public user getSender() {
        return sender;
    }

    public void setSender(user sender) {
        this.sender = sender;
    }

    public user getReceiver() {
        return receiver;
    }

    public void setReceiver(user receiver) {
        this.receiver = receiver;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
