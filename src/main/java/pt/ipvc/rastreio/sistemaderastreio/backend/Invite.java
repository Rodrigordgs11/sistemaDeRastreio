package pt.ipvc.rastreio.sistemaderastreio.backend;

public class Invite {
    private int id;
    private int numInvite;
    private int idSender;
    private int idReceiver;
    private String description;
    private int idProject;
    private InviteState inviteState;

    public Invite(int sender, int receiver, String description, int idProject, InviteState inviteState) {
        this.id = ++numInvite;
        this.idSender = sender;
        this.idReceiver = receiver;
        this.description = description;
        this.idProject = idProject;
        this.inviteState = inviteState;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumInvite() {
        return numInvite;
    }

    public void setNumInvite(int numInvite) {
        this.numInvite = numInvite;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public InviteState getInviteState() {
        return inviteState;
    }

    public void setInviteState(InviteState inviteState) {
        this.inviteState = inviteState;
    }
}
