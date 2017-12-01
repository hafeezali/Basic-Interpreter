package interpreter;

public class PrintlnCommand extends Node
{
    public Parser parser;
    
    public PrintlnCommand() {}
    
    public PrintlnCommand(Parser parser)
    {
        this.parser = parser;
    }
           
    public Object eval()
    {
        Object writee = parser.getVariable("writee");
        if(writee != null){
            System.out.println(writee);
        }
        
        return writee;
    }        
}