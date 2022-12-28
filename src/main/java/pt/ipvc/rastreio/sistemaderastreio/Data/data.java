package pt.ipvc.rastreio.sistemaderastreio.Data;

import pt.ipvc.rastreio.sistemaderastreio.backend.*;
import pt.ipvc.rastreio.sistemaderastreio.backend.user.typeUser;
import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static pt.ipvc.rastreio.sistemaderastreio.backend.user.typeUser.*;
import static pt.ipvc.rastreio.sistemaderastreio.controller.UserController.getIdLog;
public class data {
    static String userDirectory = System.getProperty("user.dir");
    public static void saveUsers(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/users.csv"))){
            for (user u: users) {
                bw.write(u.getName() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getNumberPhone() + "," + u.getNumOfWork() + "," + u.getTipoUser().toString());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Error writting file"+e.getMessage());
        }
    }
    public static void saveTasks(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/tasks.csv"))){
            for (Task t: tasks) {
                    bw.write(t.getDescription() + "," + t.getState().toString() + "," + t.getStartTime() + "," + t.getEndTime() + "," + t.getidUser() + "," + t.getIdProject());
                    bw.newLine();
                }
        }catch (IOException e){
            System.out.println("Error writting file"+e.getMessage());
        }
    }
    public static void saveProjects(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/projects.csv"))){
            for (Project p: projects) {
                bw.write(p.getName() + ";" + p.getClientName() + ";" + p.getPricePerHour() + ";" + p.getIdProject()  + ";" + p.getOwner() + ";" + p.getSharedUsers());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Error writting file"+e.getMessage());
        }
    }
    public static void saveInvites(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/invites.csv"))){
            for (Invite i: invites) {
                bw.write(i.getSender() + "," + i.getReceiver() + "," + i.getDescription() + "," + i.getIdProject() + "," + i.getInviteState());
                bw.newLine();
            }
        }catch (IOException e){
            System.out.println("Error writting file"+e.getMessage());
        }
    }
    public static void loadInvites(){
        try (BufferedReader br = new BufferedReader(new FileReader(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/invites.csv"))){
            String invite = br.readLine();
            while (invite != null){
                String[] fields = invite.split(",");
                String sender = fields[0];
                String receiver = fields[1];
                String description = fields[2];
                int idProject = Integer.parseInt(fields[3]);
                InviteState inviteState = InviteState.valueOf(fields[4]);
                invites.add(new Invite(sender, receiver, description, idProject, inviteState));
                invite = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }
    }
    public static void loadProjects(){
        try (BufferedReader br = new BufferedReader(new FileReader(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/projects.csv"))){
            String project = br.readLine();
            while (project != null){
                String[] fields = project.split(";");
                String name = fields[0];
                String clientName = fields[1];
                float price = Float.parseFloat(fields[2]);
                String owner = fields[4];
                List<String> sharedUsers =  new ArrayList<String>(Arrays.asList(fields[5].split(", ")));
                projects.add(new Project(name,clientName,price, owner, sharedUsers));
                project = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("Error reading file" + e.getMessage());
        }
    }
    public static void loadTasks() throws ParseException{
        try (BufferedReader br = new BufferedReader(new FileReader(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/tasks.csv"))){
            String task = br.readLine();
            while (task != null){
                String[] fields = task.split(",");
                String description = fields[0];
                TaskState state = TaskState.valueOf(fields[1]);
                Date startTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(fields[2]);
                Date endTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(fields[3]);
                int idUser = Integer.parseInt(fields[4]);
                int idProject = Integer.parseInt(fields[5]);
                tasks.add(new Task(description, state, startTime, endTime, idUser, idProject));
                task = br.readLine();
            }
        }catch (IOException e){
            System.out.println("Error reading file"+ e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void loadUsers(){
        try (BufferedReader br = new BufferedReader(new FileReader(userDirectory + "/src/main/java/pt/ipvc/rastreio/sistemaderastreio/files/users.csv"))){
            String user = br.readLine();
            while (user != null){
                String[] fields = user.split(",");
                String name = fields[0];
                String userName = fields[1];
                String password = fields[2];
                int numberPhone = Integer.parseInt(fields[3]);
                int numOfWork = Integer.parseInt(fields[4]);
                typeUser typeuser = typeUser.valueOf(fields[5]);
                if(typeUser.valueOf(fields[5]).equals(userStd))
                    users.add(new userStd(name, userName, password, numberPhone, numOfWork, typeuser));
                if(typeUser.valueOf(fields[5]).equals(userManager))
                    users.add(new userManager(name, userName, password, numberPhone, numOfWork, typeuser));
                if(typeUser.valueOf(fields[5]).equals(admin))
                    users.add(new admin(name, userName, password, numberPhone, numOfWork, typeuser));
                user = br.readLine();
            }
        }catch (IOException e){
            System.out.println("Error reading file"+ e.getMessage());
        }
    }
    public static List<user> users = new ArrayList<>();
    public static List<Task> tasks = new ArrayList<>();
    public static List<Project> projects = new ArrayList<>();
    public static List<Invite> invites = new ArrayList<>();
    public static user userLogged() {
        for (user u : users) {
            if(getIdLog() == u.getId()){
                return u;
            }
        }
        return null;
    }
}