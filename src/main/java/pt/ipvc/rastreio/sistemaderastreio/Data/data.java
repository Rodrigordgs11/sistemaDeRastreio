package pt.ipvc.rastreio.sistemaderastreio.Data;


import pt.ipvc.rastreio.sistemaderastreio.backend.admin;
import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.backend.user.typeUser;
import pt.ipvc.rastreio.sistemaderastreio.backend.userManager;
import pt.ipvc.rastreio.sistemaderastreio.backend.userStd;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public static user userLogged() {
        for (user u : users) {
            if(getIdLog() == u.getId()){
                return u;
            }
        }
        return null;
    }
}
