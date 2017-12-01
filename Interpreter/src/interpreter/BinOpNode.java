package interpreter;

public class BinOpNode extends Node
{
    public TokenType op;
    public Node left;
    public Node right;    
    
    public BinOpNode() {}

    public BinOpNode(TokenType op, Node left, Node right)
    {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public int ToInt(Node node)
    {
        Object res = node.eval();
        return ((Integer) res).intValue();
    }
    
    public boolean ToBoolean(Node node)
    {                
        Object res = node.eval();
        return ((Boolean) res).booleanValue();
    }

    public Object ToObject(Node node)
    {
        return node.eval();
    }

    public Object eval()
    {                
        Object result = null;
        switch(op)
        {
            case ADD:
                try{
                    result = new Integer(ToInt(left) + ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(Add) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot add integer and string.");
                    }
                }
            break;
            case SUBTRACT:
                try{
                    result = new Integer(ToInt(left) - ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(Subtract) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot subtract integer and string.");
                    }                   
                }
            break;
            case MULTIPLY:
                try{
                    result = new Integer(ToInt(left) * ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(Multiply) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot multiply integer and string.");
                    }
                }
            break;
            case DIVIDE:
                if (ToInt(right) == 0)
                {
                    System.out.println("Error: Division by Zero!");
                    System.exit(0);
                }
                try{
                    result = new Integer(ToInt(left) / ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(Divide) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot divide integer and string.");
                    }
                }
            break;
            case LESS:
                try{
                    result = new Boolean(ToInt(left) < ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(less than) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot compare(less than) integer and string.");
                    }                    
                }
            break;
            case GREATER:
                try{
                    result = new Boolean(ToInt(left) > ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(greater than) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot compare(greater than) integer and string.");
                    } 
                }
            break;
            case EQUAL:
                result = new Boolean(ToObject(left).equals(ToObject(right)));
            break;
            case NOTEQUAL:
                result = new Boolean(!ToObject(left).equals(ToObject(right)));
            break;
            case LESSEQUAL:
                try{
                    result = new Boolean(ToInt(left) <= ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(lesser than equal to) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot compare(lesser than equal to) integer and string.");
                    }
                }
            break;
            case GREATEREQUAL:
                try{
                    result = new Boolean(ToInt(left) >= ToInt(right));
                }catch(Exception e){
                    Object value1 = left.eval();
                    Object value2 = right.eval();
                    if((value1 instanceof String) && (value2 instanceof String)){
                        System.out.println("Error: Invalid operation(greater than equal to) for type string.");
                    }
                    else if((value1 instanceof String) || (value2 instanceof String)){
                        System.out.println("Error: Cannot compare(greater than equal to) integer and string.");
                    } 
                }
            break;                                                                                     
        }
        return result;
    }   
}