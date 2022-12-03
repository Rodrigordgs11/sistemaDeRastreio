package pt.ipvc.rastreio.sistemaderastreio.utils.loginRegisterExceptions;

public class alreadyExistException extends Exception{
    public alreadyExistException(String message){
        super(message);
    }
}