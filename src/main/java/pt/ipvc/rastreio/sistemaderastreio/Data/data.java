package pt.ipvc.rastreio.sistemaderastreio.Data;


import pt.ipvc.rastreio.sistemaderastreio.backend.user;
import pt.ipvc.rastreio.sistemaderastreio.backend.user.typeUser;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
                users.add(new user(name, userName, password, numberPhone, numOfWork, typeuser));
                user = br.readLine();
            }
        }catch (IOException e){
            System.out.println("Error reading file"+ e.getMessage());
        }
    }

    public static List<user> users = new ArrayList<>();
    /*private user.typeUser tipoUser;
    public void setTipoUser(typeUser tipoUser){
        this.tipoUser = tipoUser;
    }*/
}
