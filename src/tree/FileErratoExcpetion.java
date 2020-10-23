package tree;

public class FileErratoExcpetion extends Exception{//Gestione inseirmento errato del file(Diverso da FileNotFoundException)
        FileErratoExcpetion(){

        }
        FileErratoExcpetion(String e){
            super(e);
        }
}
