package interpreter;

public class IfNode extends Node
{
    public Node condition;
    public Node thenPart;
    public Node elsePart;
       
    public IfNode() {}
    
    public IfNode(Node condition, Node thenPart, Node elsePart)
    {
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }
       
    public Object eval()
    {
        Object ret = null;

        try{
            if ( (condition != null) && (thenPart != null))
                if ( ((Boolean) condition.eval()).booleanValue() )
                    ret = thenPart.eval(); 

            if ( (condition != null) && (elsePart != null))            
                if ( !((Boolean) condition.eval()).booleanValue() )
                    ret = elsePart.eval();

            return ret;
        }catch(Exception e){
            System.out.println("       Cannot execute 'if' since failure of condition statement");
            return ret;
        }
    }    
}