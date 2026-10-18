package functions.exceptions;

public class FunctionPointIndexOutOfBoundsException 
    extends IndexOutOfBoundsException {

        public FunctionPointIndexOutOfBoundsException(){
            super();
        }

        public FunctionPointIndexOutOfBoundsException(String message){
            super(message);
        }

}