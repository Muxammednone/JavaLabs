package functions;

public class InappropriateFunctionPointException 
    extends Exception{

        public InappropriateFunctionPointException(){
            super();
        }

        public InappropriateFunctionPointException(String message){
            super("inappropiate function point exception: " + message);
        }
        
}
