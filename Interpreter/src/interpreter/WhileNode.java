package interpreter;

public class WhileNode extends Node
{
    public Node condition;
    public Node body;
    
    public WhileNode() {}
    
    public WhileNode(Node condition, Node body)
    {
        this.condition = condition;
        this.body = body;
    }
    
    public Object eval()
    {
        Object ret = null; 
        try{
            while ( ((Boolean) condition.eval()).booleanValue() )
            {            
                ret = body.eval();
            }                       
        }catch(Exception e){
            System.out.println("       Cannot execute while loop due to failure of condition evaluation.");
        }
        return ret;
    }    
}